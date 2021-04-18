package cn.demojie.mduploader.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;

public class CommonUtils {

  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static ObjectMapper getObjectMapper() {
    return OBJECT_MAPPER;
  }

  public static String getMimeType(String filePath) {
    FileNameMap fileNameMap = URLConnection.getFileNameMap();
    return fileNameMap.getContentTypeFor(filePath);
  }

  public static Header newAuthHeader(String auth) {
    return new BasicHeader(HttpHeaders.AUTHORIZATION, "Basic " + auth);
  }

  public static Header newAuthHeader(String username, String password) {
    String format = String.format("%s:%s", username, password);
    String auth = Base64.getEncoder().encodeToString(format.getBytes(StandardCharsets.UTF_8));
    return newAuthHeader(auth);
  }

  public static void checkFilesExists(List<String> filePaths) {
    for (String filePath : filePaths) {
      File file = new File(filePath);
      boolean exists = Files.exists(file.toPath());
      if (!exists) {
        throw new RuntimeException("文件不存在" + file.getAbsolutePath());
      }
    }
  }

  public static void checkFileExists(String filePath) {
    File file = new File(filePath);
    boolean exists = Files.exists(file.toPath());
    if (!exists) {
      throw new RuntimeException("文件不存在" + file.getAbsolutePath());
    }
  }

  public static void printContent(List<String> contents) {
    System.out.println("替换后的内容====================================>");
    for (String content : contents) {
      System.out.println(content);
    }
    System.out.println("<====================================替换后的内容");
  }


}
