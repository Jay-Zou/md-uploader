package cn.demojie.mduploader.utils;

import cn.demojie.mduploader.entity.MatchEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherUtils {

  private static final String MD_PIC_PATTERN = "!\\[.*?]\\((.*?)\\)";
  private static final String IMG_TAG_PATTERN = "<[img|IMG].*?src=\"(.*?)\"";
  // TODO img 样式的，如果两种样式在同一行

  /**
   * 一行有多个？不太可能 ![](待购买.assets/image-20210418160308920.png) ![](待购买.assets/image-20210418160308920.png)
   * <p>
   * ![image-20210418160308920](待购买.assets/image-20210418160308920.png) <img
   * src="待购买.assets/image-20210418160308920.png" alt="image-20210418160308920" style="zoom:50%;" />
   * <img src="http://192.168.198.155:1080/wp-content/uploads/2021/04/prefix_vertical-logo-monochromatic-4.png"
   * style="zoom:50%;" /> TODO 处理套娃：<img src="![](xxx)"/> ![](<img src=""/>) 更深的嵌套
   *
   * @return
   */
  public static List<MatchEntity> find(String source) {
    Pattern pattern = Pattern.compile(String.format("%s|%s", MD_PIC_PATTERN, IMG_TAG_PATTERN));
    List<MatchEntity> matchEntityList = new LinkedList<>();
    Matcher matcher = pattern.matcher(source);
    while (matcher.find()) {
      int groupNum = 1;
      if (matcher.group(1) == null) {
        groupNum = 2;
      }
      String matchContent = matcher.group(groupNum);
      if (isIgnore(matchContent)) {
        continue;
      }
      MatchEntity matchEntity = new MatchEntity();
      matchEntity.setStart(matcher.start(groupNum));
      matchEntity.setEnd(matcher.end(groupNum));
      matchEntity.setMatchContent(matchContent);
      matchEntityList.add(matchEntity);
    }
    return matchEntityList;
  }

  private static boolean isIgnore(String matchContent) {
    return matchContent.startsWith("http://")
        || matchContent.startsWith("HTTP://")
        || matchContent.startsWith("https://")
        || matchContent.startsWith("HTTPS://");
  }

  public static String replace(String origin, List<MatchEntity> matchResults,
      List<String> contents) {
    StringBuilder finalContent = new StringBuilder(origin);
    // 从后面往前替换
    for (int i = matchResults.size() - 1; i >= 0; i--) {
      MatchEntity matchEntity = matchResults.get(i);
      finalContent.replace(matchEntity.getStart(), matchEntity.getEnd(), contents.get(i));
    }
    return finalContent.toString();
  }

}
