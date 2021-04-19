package cn.demojie.mduploader;

import cn.demojie.mduploader.service.MdUploaderHandler;
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
  public void run(String... args) throws Exception {
    /**
     * ![image-20210418160308920](待购买.assets/image-20210418160308920.png)
     * <img src="待购买.assets/image-20210418160308920.png" alt="image-20210418160308920" style="zoom:50%;" />
     * <img src="http://192.168.198.155:1080/wp-content/uploads/2021/04/prefix_vertical-logo-monochromatic-4.png" style="zoom:50%;" />
     */
    if (args == null || args.length <= 0) {
      System.err.println("请指定文件！");
      return;
    }
    String mdFile = args[0];
    mdUploaderHandler.handle(mdFile);
  }

}
