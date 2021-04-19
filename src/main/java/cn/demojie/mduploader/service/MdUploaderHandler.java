package cn.demojie.mduploader.service;

import cn.demojie.mduploader.config.MduConfig;
import cn.demojie.mduploader.entity.MatchEntity;
import cn.demojie.mduploader.entity.MathInfoInLine;
import cn.demojie.mduploader.entity.UploaderContext;
import cn.demojie.mduploader.uploader.Uploader;
import cn.demojie.mduploader.utils.CommonUtils;
import cn.demojie.mduploader.utils.MatcherUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MdUploaderHandler {

  @Autowired
  Uploader uploader;

  @Autowired
  MduConfig mduConfig;

  @Autowired
  MdLinkParser mdLinkParser;

  @SneakyThrows
  public void handle(String mdFile) {
    // TODO 当修改文件后，需要将原文件删除。所以上传文件后要保存一下链接
    CommonUtils.checkFileExists(mdFile);

    UploaderContext uploaderContext = mdLinkParser.parse(mdFile);
    if (uploaderContext.getMathInfoInLineList().isEmpty()) {
      System.err.println("没找到需要上传的文件，不需要转换！");
      System.exit(0);
    }
    // TODO 对于重复文件，不需要重复上传
    uploadFiles(uploaderContext);
    System.out.println("全部上传完毕！");

    List<String> originContent = uploaderContext.getOriginContent();
    List<MathInfoInLine> mathInfoInLineList = uploaderContext.getMathInfoInLineList();
    rePutToContent(mathInfoInLineList, originContent);

    // CommonUtils.printContent(originContent);
    writeToFile(mdFile, originContent);
    System.out.println("完成！");
  }

  private void rePutToContent(List<MathInfoInLine> mathInfoInLineList, List<String> originContent) {
    for (MathInfoInLine mathInfoInLine : mathInfoInLineList) {
      Integer lineNum = mathInfoInLine.getLineNum();
      String content = mathInfoInLine.getContent();
      originContent.set(lineNum, content);
    }
  }

  private void writeToFile(String mdFile, List<String> originContent) throws IOException {
    File file = new File(mdFile + ".o.md");
    System.out.println("写入文件：" + file.getAbsolutePath());
    try {
      Files.write(file.toPath(), originContent, StandardOpenOption.CREATE_NEW);
    } catch (FileAlreadyExistsException e) {
      System.err.println("文件已存在，写入失败！");
      System.exit(0);
    }
  }

  private void uploadFiles(UploaderContext uploaderContext) throws Exception {
    List<MathInfoInLine> mathInfoInLineList = uploaderContext.getMathInfoInLineList();
    for (MathInfoInLine mathInfoInLine : mathInfoInLineList) {
      String content = mathInfoInLine.getContent();
      List<MatchEntity> matchEntityList = mathInfoInLine.getMatchEntityList();
      List<File> preUploadFiles = matchEntityList.stream().map(matchEntity -> {
        String filePath = matchEntity.getMatchContent();
        File file = new File(filePath);
        if (!file.isAbsolute()) {
          file = new File(uploaderContext.getFileBaseDir(), filePath);
        }
        CommonUtils.checkFileExists(file);
        return file;
      }).collect(Collectors.toList());
      // 一行内的上传文件
      List<String> returnLinks = uploader.upload(mduConfig, preUploadFiles);
      System.out.println("返回的链接：" + returnLinks);
      String finalContent = MatcherUtils.replace(content, matchEntityList, returnLinks);
      // 将替换后的内容放回去
      mathInfoInLine.setContent(finalContent);
    }
  }
}
