package cn.demojie.mduploader.entity;

import java.util.List;
import lombok.Data;

@Data
public class UploaderContext {

  /**
   * 原始文件内容更
   */
  private List<String> originContent;

  /**
   * 解析出来的内容
   */
  private List<MathInfoInLine> mathInfoInLineList;

  /**
   * 文件所在的路径
   */
  private String fileBaseDir;

}
