package cn.demojie.mduploader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:application.yml"})
@ConfigurationProperties(prefix = "mdu-config")
@Data
public class MduConfig {

  /**
   * 账号密码
   */
  private String username;
  private String password;

  /**
   * 密码账号的 base64 编码（优先使用这个，当为 null 时采用密码账号）
   */
  private String authBasic = "ZGVtb2ppZToxMTExMTExMQ==";

  /**
   * 上传 URL
   */
  private String uploadRrl = "http://192.168.198.155:1080/wp-json/brain1981/v1/stream";


  /**
   * 删除 URL，用于回滚，或者删除文章
   */
  private String deleteRrl;

}
