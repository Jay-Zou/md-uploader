package cn.demojie.mduploader;

import cn.demojie.mduploader.config.CmdConfig;
import cn.demojie.mduploader.config.ContextConfig;
import cn.demojie.mduploader.exception.UploaderException;
import cn.demojie.mduploader.service.MdUploaderHandler;
import cn.demojie.mduploader.utils.CmdParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author JayZou
 */
@SpringBootApplication
public class MdUploaderApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(MdUploaderApplication.class, args);
  }

  @Autowired
  MdUploaderHandler mdUploaderHandler;

  @Override
  public void run(String... args) {
    /**
     * ![image-20210418160308920](待购买.assets/image-20210418160308920.png)
     * <img src="待购买.assets/image-20210418160308920.png" alt="image-20210418160308920" style="zoom:50%;" />
     * <img src="http://192.168.198.155:1080/wp-content/uploads/2021/04/prefix_vertical-logo-monochromatic-4.png" style="zoom:50%;" />
     */
    try {
      CmdConfig cmdConfig = CmdParser.parse(args);
      ContextConfig contextConfig = CmdParser.getConfig(cmdConfig);
      mdUploaderHandler.handle(contextConfig);
    } catch (UploaderException e) {
      System.err.println(e.getMessage());
    }
  }

}
