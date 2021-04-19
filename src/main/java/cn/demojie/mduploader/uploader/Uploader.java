package cn.demojie.mduploader.uploader;

import cn.demojie.mduploader.config.MduConfig;
import java.io.File;
import java.util.List;

public interface Uploader {

  String upload(MduConfig config, File file) throws Exception;

  List<String> upload(MduConfig config, List<File> files) throws Exception;
}
