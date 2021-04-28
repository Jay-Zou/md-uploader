package cn.demojie.mduploader.utils;

import cn.demojie.mduploader.config.CmdConfig;
import cn.demojie.mduploader.config.ContextConfig;
import cn.demojie.mduploader.config.UserConfig;
import cn.demojie.mduploader.exception.UploaderException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CmdParser {

  private static final String COMMAND = "mdu";
  private static final String CMD_LINE_SYNTAX = COMMAND + " [OPTION]... [FILE]";
  private static final String DEFAULT_OUT_FILE_SUFFIX = ".o.md";
  private static final HelpFormatter HELP_FORMATTER = new HelpFormatter();

  interface CmdOption {

    String CONFIG_FILE = "c";
    String REPLACE_SRC = "r";
  }

  public static CmdConfig parse(String[] args) throws IllegalArgumentException {
    CommandLineParser parser = new DefaultParser();
    Options options = buildOptions();
    CmdConfig cmdConfig = new CmdConfig();
    try {
      CommandLine line = parser.parse(options, args);
      List<String> argList = line.getArgList();
      if (argList.size() != 1) {
        printHelpAndExit(options);
      }
      cmdConfig.setMdFile(argList.get(0));
      if (line.hasOption(CmdOption.CONFIG_FILE)) {
        cmdConfig.setConfigFile(line.getOptionValue(CmdOption.CONFIG_FILE));
      }
      if (line.hasOption(CmdOption.REPLACE_SRC)) {
        cmdConfig.setReplaceSrc(true);
      }
      return cmdConfig;
    } catch (ParseException exp) {
      System.err.println(exp.getMessage());
      printHelpAndExit(options);
    }
    throw new IllegalArgumentException("非法参数！");
  }

  private static Options buildOptions() {
    Options options = new Options();
    options.addOption(Option.builder("c").longOpt("config")
        .desc("配置文件。默认使用 $HOME/.mduploader/config.json")
        .hasArg()
        .argName("configFile")
        .build());
    options.addOption("r", "replace", false, "替换原始文件。否则生成 {filename}.md.o.md");
    return options;
  }

  public static void printHelpAndExit(Options options) {
    HELP_FORMATTER.printHelp(CMD_LINE_SYNTAX, options);
    System.exit(0);
  }

  public static ContextConfig getConfig(CmdConfig cmdConfig) {
    ContextConfig contextConfig = new ContextConfig();

    String mdFile = cmdConfig.getMdFile();
    String configFilePath = cmdConfig.getConfigFile();
    boolean replaceSrc = cmdConfig.isReplaceSrc();

    if (configFilePath == null) {
      // 使用默认配置文件
      configFilePath = ConfigUtils.getDefaultConfigFilePath();
    }
    // 解析配置文件
    UserConfig userConfig = parserConfigFile(configFilePath);
    contextConfig.setUserConfig(userConfig);
    contextConfig.setMdFile(mdFile);
    // 是否替换原始文件
    if (replaceSrc) {
      contextConfig.setOutputMdFile(mdFile);
    } else {
      contextConfig.setOutputMdFile(mdFile + DEFAULT_OUT_FILE_SUFFIX);
    }
    return contextConfig;
  }

  private static UserConfig parserConfigFile(String configFilePath) {
    return parserConfigFile(new File(configFilePath));
  }

  private static UserConfig parserConfigFile(File configFile) {
    System.out.println("读取配置文件：" + configFile.getAbsolutePath());
    if (!configFile.exists()) {
      System.err.println("配置文件不存在：" + configFile.getAbsolutePath());
      System.exit(0);
    }
    try {
      return CommonUtils.getObjectMapper().readValue(configFile, UserConfig.class);
    } catch (IOException e) {
      throw new UploaderException("读取配置文件失败！" + e.getMessage());
    }
  }

}
