package cn.demojie.mduploader.utils;

import java.io.File;

public class ConfigUtils {

  public static final String DEFAULT_CONFIG_PATH = ".mduploader/config.json";

  public static File getDefaultConfigFile() {
    return new File(CommonUtils.getHomePath(), DEFAULT_CONFIG_PATH);
  }

}
