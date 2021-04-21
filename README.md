# Markdown 上传器

> 将 Markdown 文档中的图片和文件通过指定 API 上传，然后替换链接。之后将整个 markdown 上传或者生成文档用于复制。

```
默认配置文件：$HOME/.mduploader/config.json

java -jar xxx.jar xxx.md
java -jar xxx.jar -c config.json xxx.md
```

TODO：
```
1、login username passwd
2、支持更新文档，要记录上一次上传的文件。要么只上传更新的，要么全部删除后上传。
3、生成配置文件
4、不要处理代码块中的数据
```

异常场景：
```
配置文件格式不对。
必选项没填。
未认证。
登录失败。
请求URL失败
```