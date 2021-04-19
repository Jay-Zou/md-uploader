package cn.demojie.mduploader.service;

import cn.demojie.mduploader.entity.MatchEntity;
import cn.demojie.mduploader.entity.MathInfoInLine;
import cn.demojie.mduploader.entity.UploaderContext;
import cn.demojie.mduploader.utils.CommonUtils;
import cn.demojie.mduploader.utils.MatcherUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MdLinkParser {

  public UploaderContext parse(String mdFile) {
    File file = new File(mdFile);
    System.out.println("解析文件：" + file.getAbsolutePath());
    // TODO 测试一下相对路径和绝对路径
    UploaderContext uploaderContext = new UploaderContext();
    List<String> lines;
    try {
      String baseDir = CommonUtils.getBaseDir(file);
      uploaderContext.setFileBaseDir(baseDir);
      lines = Files.readAllLines(file.toPath());
    } catch (IOException e) {
      throw new RuntimeException("读取文件失败！");
    }
    uploaderContext.setOriginContent(lines);

    List<MathInfoInLine> mathInfoInLineList = new LinkedList<>();
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      // 对一行进行匹配
      List<MatchEntity> matchEntityList = MatcherUtils.find(line);
      if (!matchEntityList.isEmpty()) {
        MathInfoInLine mathInfoInLine = new MathInfoInLine();
        mathInfoInLine.setLineNum(i);
        mathInfoInLine.setContent(line);
        mathInfoInLine.setMatchEntityList(matchEntityList);
        mathInfoInLineList.add(mathInfoInLine);
      }
    }
    uploaderContext.setMathInfoInLineList(mathInfoInLineList);
    return uploaderContext;
  }


}
