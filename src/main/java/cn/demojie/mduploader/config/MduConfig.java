package cn.demojie.mduploader.config;

import lombok.Data;


@Data
public class MduConfig {

  /**
   * 账号密码
   */
  private String username;
  private String password;

  /**
   * MdFile
   */
  private String mdFile;

  /**
   * 密码账号的 base64 编码（优先使用这个，当为 null 时采用密码账号）
   */
  private String token;

  /**
   * 上传 URL
   */
  private String uploadRrl;


  /**
   * 删除 URL，用于回滚，或者删除文章
   */
  private String deleteRrl;

}
