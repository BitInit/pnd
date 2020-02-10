![logo](doc/image/logo_600_400.png)
## 介绍
`pnd`全名为`Personal Network Disk`，为了实现一个部署在家庭、寝室等私人生活中的网络存储空间，是一个具有从远程服务器后台下载到私人网盘、家庭文件共享、在线影视观看等功能的方便、快捷的生活助手。

前端页面：[https://github.com/BitInit/pnd-web](https://github.com/BitInit/pnd-web)

演示地址(服务器配置为1核 2G内存 1M带宽，所以该地址只为预览项目)：[http://47.106.219.179:8989](http://47.106.219.179:8989)

## 功能
- [x] 文件及文件夹：增/删/重命名/移动/复制
- [x] 大文件的分块上传，支持文件上传的暂停/恢复
- [x] 文件下载，部分视频的浏览器简单播放
- [ ] 文件 md5 校验，实现文件快速上传
- [ ] 视频在线转码播放
- [ ] 互联网资源服务器后台下载
- [ ] 互联网视频在线播放(整合各个视频网站，不用在去找各种视频种子)
- [ ] ......

## 下载源码或安装包
### 容器部署(推荐)

``` sh
docker run -d -p 8989:8989 -v [YourOwnPath]:/pnd/data bitinit/pnd

# 浏览器访问：http://<ip>:8989
```

### 直接下载二进制包
[选择最新的二进制包](https://github.com/BitInit/pnd/releases)

### 源码构建

``` sh
git clone git@github.com:BitInit/pnd.git 

cd pnd
mvn clean package

# pnd/distribution/target" 下可看到构建好的 *.tar.gz 和 *.zip 包
# 运行
bin/startup.sh

# 浏览器访问：http://localhost:8989
```
