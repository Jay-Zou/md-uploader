package cn.demojie.mduploader.uploader.impl;

import cn.demojie.mduploader.config.MduConfig;
import cn.demojie.mduploader.uploader.AbstractUploader;
import cn.demojie.mduploader.utils.CommonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Component
public class JwtUploaderImpl extends AbstractUploader {

  @Override
  public String upload(MduConfig mduConfig, File file) throws Exception {
    System.out.println("上传文件：" + file.getAbsolutePath());
    HttpPost httpPost = buildRequest(mduConfig, file);

    HttpResponse httpResponse = CommonUtils.getHttpClient().execute(httpPost);
    HttpEntity httpEntity = httpResponse.getEntity();
    if (httpEntity == null) {
      return null;
    }
    String resp = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
    JsonNode jsonNode = CommonUtils.getObjectMapper().readTree(resp);
    if (jsonNode == null) {
      throw new RuntimeException("调用上传接口出错");
    }
    if (!jsonNode.has("source_url")) {
      throw new RuntimeException("调用上传接口出错：" + jsonNode);
    }
    return jsonNode.get("source_url").asText();
  }

  private HttpPost buildRequest(MduConfig mduConfig, File file) {
    HttpPost httpPost = new HttpPost(mduConfig.getUploadRrl());
    httpPost.setHeaders(buildHeaders(mduConfig, file.getName()));
    httpPost.setEntity(
        new FileEntity(file, ContentType.parse(CommonUtils.getMimeType(file.getName()))));
    return httpPost;
  }

  private Header[] buildHeaders(MduConfig mduConfig, String name) {
    List<Header> headers = new ArrayList<>(2);
    // 使用已经 BASE64 处理的用户名和密码
    if (mduConfig.getAuthBasic() != null) {
      headers.add(CommonUtils.newAuthHeader(mduConfig.getAuthBasic()));
    } else {
      // 使用 username/password
      headers.add(CommonUtils.newAuthHeader(mduConfig.getUsername(), mduConfig.getPassword()));
    }
    // Content-Disposition: form-data
    headers.add(new BasicHeader("Content-Disposition", "form-data; filename=\"" + name + "\""));
    return headers.toArray(new Header[0]);
  }
}
