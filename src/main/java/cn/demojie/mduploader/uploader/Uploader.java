package cn.demojie.mduploader.uploader;

import cn.demojie.mduploader.config.ContextConfig;
import java.io.File;
import java.util.List;

public interface Uploader {

  String upload(ContextConfig config, File file);

  List<String> upload(ContextConfig config, List<File> files);
}
