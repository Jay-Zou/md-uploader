package cn.demojie.mduploader.config;

import lombok.Data;

@Data
public class CmdConfig {

  /**
   * 原始文件
   */
  private String mdFile;

  /**
   * 配置文件
   */
  private String configFile;

  /**
   * 是否替换原始文件
   */
  private boolean replaceSrc;
}
