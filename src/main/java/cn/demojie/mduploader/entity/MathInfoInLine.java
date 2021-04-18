package cn.demojie.mduploader.entity;

import java.util.List;
import lombok.Data;

@Data
public class MathInfoInLine {

  /**
   * 行号，从 0 开始
   */
  private Integer lineNum;

  /**
   * 内容
   */
  private String content;

  /**
   * 解析的匹配对象
   */
  private List<MatchEntity> matchEntityList;
}
