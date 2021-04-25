# Markdown 上传器

> 将 Markdown 文档中的图片和文件通过指定 API 上传，然后替换链接。之后将整个 markdown 上传或者生成文档用于复制。

```
JDK版本：1.8
默认配置文件：$HOME/.mduploader/config.json

java -jar xxx.jar xxx.md
java -jar xxx.jar -c config.json xxx.md
```
config.json 模板：
- 使用用户名和密码：
```json
{
  "username": "demojie",
  "password": "11111111",
  "uploadRrl": "http://192.168.198.155:1080/wp-json/wp/v2/media"
}
```
- 使用 token：
```json
{
  "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xOTIuMTY4LjE5OC4xNTU6MTA4MCIsImlhdCI6MTYxODg0MzU2OSwibmJmIjoxNjE4ODQzNTY5LCJleHAiOjE2MTk0NDgzNjksImRhdGEiOnsidXNlciI6eyJpZCI6IjEifX19.itYMAErBmJkb0w5lfUHo8t8uYbYDoNyM9n787owAGL4",
  "uploadRrl": "http://192.168.198.155:1080/wp-json/wp/v2/media"
}
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