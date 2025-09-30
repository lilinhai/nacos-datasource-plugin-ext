# Nacos数据库适配插件

## 一、插件概述

### 1.1、简介

从Nacos2.2版本开始，Nacos提供了数据源扩展插件，以便让需要进行其他数据库适配的用户自己编写插件来保存数据。当前项目插件目前已简单适配Postgresql。


当前插件项目地址：
[https://github.com/lilinhai/nacos-datasource-plugin-ext](https://github.com/lilinhai/nacos-datasource-plugin-ext)

欢迎大家访问我的博客：[Nacos-3.1.0适配PostgreSQL数据库 https://linhai.blog.csdn.net/article/details/147592681](https://linhai.blog.csdn.net/article/details/147592681)

当前项目是基于`Nacos3.0.3`版本的扩展插件口进行开发，同时也是基于以下项目的一个分支进行的而开：
[https://github.com/nacos-group/nacos-plugin/tree/develop/nacos-datasource-plugin-ext](https://github.com/nacos-group/nacos-plugin/tree/develop/nacos-datasource-plugin-ext)

很感谢阿里`nacos`团队的贡献，你们一如既往的迭代`nacos`版本，给你们说声辛苦了，但是却忘记了`nacos`的其他数据源适配，比如`postgresql`。

### 2.2、插件工程结构说明

nacos-datasource-plugin-ext-base工程为数据库插件操作的适配抽象。
nacos-postgresql-datasource-plugin-ext工程可打包适配Postgresql的数据库插件

## 二、下载和使用

### 2.1、插件引入

方式一：将nacos的postgresql插件下载下来，将依赖手动拷贝到nacos的home目录下的plugins目录下（nacos-3.0.3/plugins）即可。
- 以下nacos的postgresql插件版本仅支持nacos3.0.3，采用jdk17编译级：

```xml
<dependency>
    <groupId>com.sinhy</groupId>
    <artifactId>nacos-postgresql-datasource-plugin-ext</artifactId>
    <version>3.1.0</version>
</dependency>
```

方式二：下载当前插件项目源码，打包为jar包，将该文件的路径配置到startup.sh文件中，使用Nacos的loader.path机制指定该插件的路径，可修改startup.sh中的loader.path参数的位置进行指定。

### 2.2、修改数据库配置文件

在application.properties文件中声明postgresql的配置信息：

```properties
spring.sql.init.platform=postgresql

#spring.datasource.platform=postgresql

### Count of DB:
db.num=1

### Connect URL of DB:
db.url.0=jdbc:postgresql://127.0.0.1:5432/nacos?currentSchema=public&useUnicode=true&tcpKeepAlive=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&reWriteBatchedInserts=true&ApplicationName=nacos_java
db.user.0=nacos
db.password.0=nacos
db.pool.config.connectionTimeout=30000
db.pool.config.validationTimeout=10000
db.pool.config.maximumPoolSize=20
db.pool.config.minimumIdle=2
db.pool.config.driverClassName=org.postgresql.Driver

#如果是 oracle 则需要改为 SELECT * FROM dual 
#db.pool.config.connectionTestQuery=SELECT 1
```

### 2.3、导入Postgresql的数据库脚本文件

导入nacos-postgresql的脚本文件，脚本文件在nacos-postgresql-datasource-plugin-ext/src/main/resources/schema文件夹下面.

上面操作完成后，启动Nacos即可。

## 三、其他数据库插件开发

可参考nacos-postgresql-datasource-plugin-ext工程，新创建Maven项目，实现AbstractDatabaseDialect类，重写相关的分页操作逻辑与方法，并创建相应的mapper实现。