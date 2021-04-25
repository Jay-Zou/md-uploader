package cn.demojie.mduploader.utils;

import cn.demojie.mduploader.entity.MatchEntity;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MatcherUtilsTest {

  @Test
  void findTwiceMd() {
    String src1 = "待购买.assets/image-20210418160308920.png";
    String src2 = "待购买.assets/image-asb.png";
    String source = "![image-20210418160308920](待购买.assets/image-20210418160308920.png) ![image-20210418160308920](待购买.assets/image-asb.png) ";
    int src1Start = source.indexOf(src1);
    int src2Start = source.indexOf(src2);
    List<MatchEntity> matchEntities = MatcherUtils.find(source);
    List<MatchEntity> expectedEntities =
        Lists.list(new MatchEntity(src1Start, src1Start + src1.length(), src1),
            new MatchEntity(src2Start, src2Start + src2.length(), src2));
    Assertions.assertArrayEquals(expectedEntities.toArray(), matchEntities.toArray());
  }

  @Test
  void findTwiceImg() {
    String src1 = "待购买.assets/image-20210418160308920.png";
    String src2 = "待购买.assets/image-asb.png";
    String source = "<img alt=\"image-20210418160308920\" src=\"待购买.assets/image-20210418160308920.png\" style=\"zoom:50%;\" /> <img src=\"待购买.assets/image-asb.png\" alt=\"image-20210418160308920\" style=\"zoom:50%;\" />";
    int src1Start = source.indexOf(src1);
    int src2Start = source.indexOf(src2);
    List<MatchEntity> matchEntities = MatcherUtils.find(source);
    List<MatchEntity> expectedEntities = Lists
        .list(new MatchEntity(src1Start, src1Start + src1.length(), src1),
            new MatchEntity(src2Start, src2Start + src2.length(), src2));
    Assertions.assertArrayEquals(expectedEntities.toArray(), matchEntities.toArray());
  }


  @Test
  void findMdAndImg() {
    String src1 = "待购买.assets/image-abc.png";
    String src2 = "待购买.assets/image-20210418160308920.png";
    String source = "![image-20210418160308920](待购买.assets/image-abc.png) <img alt=\"image-20210418160308920\" src=\"待购买.assets/image-20210418160308920.png\" style=\"zoom:50%;\" />";
    int src1Start = source.indexOf(src1);
    int src2Start = source.indexOf(src2);
    List<MatchEntity> matchEntities = MatcherUtils.find(source);
    List<MatchEntity> expectedEntities = Lists
        .list(new MatchEntity(src1Start, src1Start + src1.length(), src1),
            new MatchEntity(src2Start, src2Start + src2.length(), src2));
    Assertions.assertArrayEquals(expectedEntities.toArray(), matchEntities.toArray());
  }

  @Test
  void findMdAndImgReOrder() {
    String src1 = "待购买.assets/image-20210418160308920.png";
    String src2 = "待购买.assets/image-abc.png";
    String source = "<img alt=\"image-20210418160308920\" src=\"待购买.assets/image-20210418160308920.png\" style=\"zoom:50%;\" />![image-20210418160308920](待购买.assets/image-abc.png) ";
    int src1Start = source.indexOf(src1);
    int src2Start = source.indexOf(src2);
    List<MatchEntity> matchEntities = MatcherUtils.find(source);
    List<MatchEntity> expectedEntities = Lists
        .list(new MatchEntity(src1Start, src1Start + src1.length(), src1),
            new MatchEntity(src2Start, src2Start + src2.length(), src2));
    Assertions.assertArrayEquals(expectedEntities.toArray(), matchEntities.toArray());
  }


  @Test
  void findAndReplace() {
    String src1 = "待购买.assets/image-20210418160308920.png";
    String src2 = "待购买.assets/image-asb.png";
    String source = String
        .format("![image-20210418160308920](%s) ![image-20210418160308920](%s) ", src1, src2);
    int src1Start = source.indexOf(src1);
    int src2Start = source.indexOf(src2);
    List<MatchEntity> matchEntities = MatcherUtils.find(source);
    List<MatchEntity> expectedEntities = Lists
        .list(new MatchEntity(src1Start, src1Start + src1.length(), src1),
            new MatchEntity(src2Start, src2Start + src2.length(), src2));
    Assertions.assertArrayEquals(expectedEntities.toArray(), matchEntities.toArray());

    String target1 = "http://xxx/a.png";
    String target2 = "http://xxx/b.png";

    String target = String
        .format("![image-20210418160308920](%s) ![image-20210418160308920](%s) ", target1, target2);

    String replace = MatcherUtils.replace(source, matchEntities, Lists.list(target1, target2));
    Assertions.assertEquals(target, replace);
  }
}