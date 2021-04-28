package cn.demojie.mduploader.config;

import lombok.Data;


@Data
public class ContextConfig {

  /**
   * 配置文件中的配置
   */
  private UserConfig userConfig;

  /**
   * 原始文件
   */
  private String mdFile;

  /**
   * 输出文件
   */
  private String outputMdFile;
}
