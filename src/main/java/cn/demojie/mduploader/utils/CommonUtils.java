package cn.demojie.mduploader.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

public class CommonUtils {

  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static final HttpClient HTTP_CLIENT = HttpClients.createDefault();

  public static ObjectMapper getObjectMapper() {
    return OBJECT_MAPPER;
  }

  public static HttpClient getHttpClient() {
    return HTTP_CLIENT;
  }

  public static String getMimeType(String filePath) {
    FileNameMap fileNameMap = URLConnection.getFileNameMap();
    return fileNameMap.getContentTypeFor(filePath);
  }

  public static Header newAuthHeader(String auth) {
    return new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + auth);
  }

  public static Header newAuthHeader(String username, String password) {
    String format = String.format("%s:%s", username, password);
    String auth = Base64.getEncoder().encodeToString(format.getBytes(StandardCharsets.UTF_8));
    return newAuthHeader(auth);
  }

  public static void checkFileExists(File file) {
    boolean exists = Files.exists(file.toPath());
    if (!exists) {
      throw new RuntimeException("文件不存在" + file.getAbsolutePath());
    }
    if (file.isDirectory()) {
      throw new RuntimeException("不支持目录" + file.getAbsolutePath());
    }
  }

  public static void checkFileExists(String filePath) {
    checkFileExists(new File(filePath));
  }

  public static void printContent(List<String> contents) {
    System.out.println("替换后的内容====================================>");
    for (String content : contents) {
      System.out.println(content);
    }
    System.out.println("<====================================替换后的内容");
  }

  public static String getBaseDir(File file) throws IOException {
    if (file.isAbsolute()) {
      return file.getParent();
    } else {
      return file.getCanonicalFile().getParent();
    }
  }

  public static String getHomePath() {
    return System.getProperty("user.home");
  }

}
