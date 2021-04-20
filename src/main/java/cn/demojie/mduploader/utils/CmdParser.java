package cn.demojie.mduploader.utils;

import cn.demojie.mduploader.config.MduConfig;
import java.io.File;
import java.io.IOException;

public class CmdParser {

  public static MduConfig getConfig(String... args) {
    File configFile;
    String mdFile;
    if (args.length == 1) {
      configFile = ConfigUtils.getDefaultConfigFile();
      mdFile = args[0];
    } else if (args.length == 3) {
      if (!"-c".equals(args[0])) {
        System.err.println("参数错误！");
      }
      configFile = new File(args[1]);
      mdFile = args[2];
    } else {
      System.err.println("参数错误！");
      return null;
    }
    MduConfig mduConfig = parserConfigFile(configFile);
    mduConfig.setMdFile(mdFile);
    return mduConfig;
  }

  private static MduConfig parserConfigFile(File configFile) {
    System.out.println("读取配置文件：" + configFile.getAbsolutePath());
    if (!configFile.exists()) {
      System.err.println("配置文件不存在：" + configFile.getAbsolutePath());
      System.exit(0);
    }
    try {
      return CommonUtils.getObjectMapper().readValue(configFile, MduConfig.class);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("读取配置文件失败！");
    }
  }

}
