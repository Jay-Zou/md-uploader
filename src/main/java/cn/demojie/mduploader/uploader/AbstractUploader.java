package cn.demojie.mduploader.uploader;

import cn.demojie.mduploader.config.MduConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractUploader implements Uploader {

  @Override
  public List<String> upload(MduConfig mduConfig, List<File> fileList) throws Exception {
    List<String> returnLinks = new ArrayList<>(fileList.size());
    for (File file : fileList) {
      returnLinks.add(upload(mduConfig, file));
    }
    // TODO 如果中途失败，则删除已上传的文件
    return returnLinks;
  }
}
