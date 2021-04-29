# Markdown 图片上传器

> 将 Markdown 文档中的图片通过指定 API 上传，然后替换链接。之后将整个 markdown 上传或者生成文档用于复制。

使用方式：
```
JDK版本：>= 1.8
默认配置文件：$HOME/.mduploader/config.json

1、将 zip 文件解压，然后将 .mduploader 目录整个复制到 $HOME 目录下：
- 比如 C:\Users\JayZou\.mduploader

2、修改配置文件 config.json：
{
  "username": "demojie",
  "password": "11111111",
  "uploadRrl": "http://192.168.198.155:1080/wp-json/wp/v2/media"
}
- wordpress 的 Basic 和 Token 认证两个插件同时开启无效，所以这里先使用 Basic 认证

3、配置环境变量：
- 将 .mduploader 路径加入环境变量，方便全局运行命令

4、使用：
a、 直接指定 md 文件，会在 md 文件的同级目录下生成 xx.md.o.md 文件
mdu test.md

b、 -r 参数，直接替换原始文件
mdu -r test.md

c、 -c 参数，指定配置文件
mdu -c myconfig.json test.md
```

TODO：
```
1、login username passwd，生成 token
2、支持更新文档，要记录上一次上传的文件。要么只上传更新的，要么全部删除后上传。
3、生成配置文件
4、不要处理代码块中的数据
5、-r 来回写到原始文件，可以放到配置文件中（Done）
6、打成zip包，然后包含 .mduploader 目录，以及包含 bin 启动脚本（Done）
7、命令行支持，看看其他人怎么写（Done）
```

异常场景：
```
配置文件格式不对。
必选项没填。
未认证。
登录失败。
请求URL失败
```