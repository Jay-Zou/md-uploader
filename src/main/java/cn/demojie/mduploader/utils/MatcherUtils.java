package cn.demojie.mduploader.utils;

import cn.demojie.mduploader.entity.MatchEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherUtils {

  private static final String FILE_PATTERN = "!\\[.*?]\\((.*?)\\)";
  // TODO img 样式的，如果两种样式在同一行

  /**
   * 一行有多个？不太可能 ![](待购买.assets/image-20210418160308920.png) ![](待购买.assets/image-20210418160308920.png)
   * <p>
   * ![image-20210418160308920](待购买.assets/image-20210418160308920.png) <img
   * src="待购买.assets/image-20210418160308920.png" alt="image-20210418160308920" style="zoom:50%;" />
   * <img src="http://192.168.198.155:1080/wp-content/uploads/2021/04/prefix_vertical-logo-monochromatic-4.png"
   * style="zoom:50%;" />
   *
   * @return
   */
  public static List<MatchEntity> find(String source) {
    Pattern pattern = Pattern.compile(FILE_PATTERN);
    List<MatchEntity> matchEntityList = new LinkedList<>();
    Matcher matcher = pattern.matcher(source);
    while (matcher.find()) {
      MatchEntity matchEntity = new MatchEntity();
      matchEntity.setStart(matcher.start());
      matchEntity.setEnd(matcher.end());
      matchEntity.setMatchContent(matcher.group(1));
      matchEntityList.add(matchEntity);
    }
    return matchEntityList;
  }

  public static String replace(String origin, List<MatchEntity> matchResults,
      List<String> contents) {
    StringBuilder finalContent = new StringBuilder(origin);
    // 从后面往前替换
    for (int i = matchResults.size() - 1; i >= 0; i--) {
      MatchEntity matchEntity = matchResults.get(i);
      String matchContent = matchEntity.getMatchContent();
      int end = matchEntity.getEnd();
      finalContent.replace(end - 1 - matchContent.length(), end - 1, contents.get(i));
    }
    return finalContent.toString();
  }

}
