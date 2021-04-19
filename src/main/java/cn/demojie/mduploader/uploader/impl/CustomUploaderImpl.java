package cn.demojie.mduploader.uploader.impl;

import cn.demojie.mduploader.config.MduConfig;
import cn.demojie.mduploader.uploader.AbstractUploader;
import cn.demojie.mduploader.utils.CommonUtils;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;


public class CustomUploaderImpl extends AbstractUploader {

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
    // 删除前后的双引号，并将 '\/' 变成 '/'
    return resp.substring(1, resp.length() - 1).replaceAll("\\\\/", "/");
  }


  private HttpPost buildRequest(MduConfig mduConfig, File file) throws Exception {
    HttpPost httpPost = new HttpPost(mduConfig.getUploadRrl());

    Header[] headers = buildHeaders(mduConfig);
    httpPost.setHeaders(headers);

    String body = buildBody(file);
    httpPost.setEntity(new StringEntity(body));
    return httpPost;
  }

  private Header[] buildHeaders(MduConfig mduConfig) {
    List<Header> headers = new ArrayList<>(2);
    // 使用已经 BASE64 处理的用户名和密码
    if (mduConfig.getAuthBasic() != null) {
      headers.add(CommonUtils.newAuthHeader(mduConfig.getAuthBasic()));
    } else {
      // 使用 username/password
      headers.add(CommonUtils.newAuthHeader(mduConfig.getUsername(), mduConfig.getPassword()));
    }
    headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString()));
    return headers.toArray(new Header[0]);
  }

  private String buildBody(File file) throws Exception {
    byte[] bytes = Files.readAllBytes(file.toPath());
    HashMap<String, Object> map = new HashMap<>();
    map.put("filename", file.getName());
    map.put("mime", CommonUtils.getMimeType(file.getName()));
    map.put("content", bytes);
    return CommonUtils.getObjectMapper().writeValueAsString(map);
  }
}
