package cn.demojie.mduploader.uploader.impl;

import cn.demojie.mduploader.config.ContextConfig;
import cn.demojie.mduploader.config.UserConfig;
import cn.demojie.mduploader.exception.UploaderException;
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
  public String upload(ContextConfig contextConfig, File file) {
    System.out.println("上传文件：" + file.getAbsolutePath());
    HttpPost httpPost = buildRequest(contextConfig, file);
    JsonNode jsonNode;
    try {
      HttpResponse httpResponse = CommonUtils.getHttpClient().execute(httpPost);
      HttpEntity httpEntity = httpResponse.getEntity();
      if (httpEntity == null) {
        return null;
      }
      String resp = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
      jsonNode = CommonUtils.getObjectMapper().readTree(resp);
    } catch (Exception e) {
      throw new UploaderException("上传文件失败：" + e.getMessage());
    }
    if (jsonNode == null) {
      throw new UploaderException("调用上传接口出错");
    }
    if (!jsonNode.has("source_url")) {
      throw new UploaderException("调用上传接口出错：" + jsonNode);
    }
    return jsonNode.get("source_url").asText();
  }

  private HttpPost buildRequest(ContextConfig contextConfig, File file) {
    HttpPost httpPost = new HttpPost(contextConfig.getUserConfig().getUploadRrl());
    httpPost.setHeaders(buildHeaders(contextConfig.getUserConfig(), file.getName()));
    httpPost.setEntity(
        new FileEntity(file, ContentType.parse(CommonUtils.getMimeType(file.getName()))));
    return httpPost;
  }

  private Header[] buildHeaders(UserConfig userConfig, String name) {
    List<Header> headers = new ArrayList<>(2);
    // 使用已经 BASE64 处理的用户名和密码
    if (userConfig.getToken() != null) {
      headers.add(CommonUtils.newBearerAuthHeader(userConfig.getToken()));
    } else {
      // 使用 username/password
      headers
          .add(CommonUtils.newBasicAuthHeader(userConfig.getUsername(), userConfig.getPassword()));
    }
    // Content-Disposition: form-data
    headers.add(new BasicHeader("Content-Disposition", "form-data; filename=\"" + name + "\""));
    return headers.toArray(new Header[0]);
  }
}
