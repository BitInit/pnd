## 介绍
`pnd`全名为`Personal Network Disk`，为了实现一个部署在家庭、寝室等私人生活中的网络存储空间，是一个具有从远程服务器后台下载到私人网盘、家庭文件共享、在线影视观看等功能的方便、快捷的生活助手。

前端页面：[https://github.com/BitInit/pnd-web](https://github.com/BitInit/pnd-web)

## 功能
- [x] 文件及文件夹：增/删/改/移动/复制
- [ ] 文件的上传与下载 开发中...
- [ ] 影视在线观看
- [ ] 远程资源后台下载
- [ ] ......

## 下载源码或安装包
### 源码构建

``` sh
git clone https://github.com/BitInit/pnd.git

cd pnd
mvn clean package

# pnd/distribution/target" 下可看到构建好的 *.tar.gz 和 *.zip 包
# 运行
bin/startup.sh

# 浏览器访问：http://localhost:8989
```

### 直接下载安装包
待续...
