# Nacos数据库适配插件

## 一、插件概述

**Nacos 3.1.1 (2025年11月26日) 最新版**

本次发布重点增强了Nacos的MCP（模型配置协议）和A2A（智能体到智能体）能力，提升了系统稳定性，并完善了安全与配置管理。主要新增内容包括支持批量注册智能体端点、在导入MCP服务器时合并版本，以及通过`overrideExisting`选项增强了更新MCP服务器的灵活性。此版本还引入了对入站和出站通信的默认安全配置支持，为安全的AI智能体交互奠定了基础。

本版本的具体变更如下：
**功能**

*   [#13867] 支持在更新MCP服务器时覆盖现有配置。
*   [#13922] 新增线程池配置以允许核心线程超时。
*   [#13946] 重构AgentEndpoint模型并新增字段以支持高级协议和查询。
*   [#13953] 增加对默认安全配置的支持，包括为入站和出站安全方案提供选择和设置。
*   [#13957] 支持批量注册智能体端点。
*   [#13951] 为控制台模块添加可配置的CORS过滤器。
**增强/重构**

*   [#13822] 分离客户端执行器与登录调度执行器的职责，防止线程池耗尽。
*   [#13863] 优化`shutdown.sh`脚本，将多个PID合并为单行，使进程终止更简洁。
*   [#13911] 增强`importToolsFromMcp` API以支持流式协议服务器。
*   [#13914] 升级checkStyle插件版本，以改进代码规范检查与可维护性。
*   [#13945] 支持在导入MCP服务器规范时合并版本。
*   [#13963] 重构List配置API以支持返回`configTags`和描述信息。
**问题修复**

*   [#13140] 修复当客户端命名空间为空时，`ConfigChangeNotifyRequest`的一致性命名空间处理问题。
*   [#13860] 修复`ConfigMigrateService`中潜在的空指针异常风险。
*   [#13866] 修复在控制台中无法编辑已导入的MCP服务器的问题。
*   [#13869] 修复因缺少依赖注入导致创建'consoleHealthController' bean失败的错误。
*   [#13877] 修复`McpRemoteHandler`中的`namespaceId`问题，该问题导致上下文传播不正确。
*   [#13882] 修复`TpsControlManager`的同步锁问题，并添加`volatile`关键字以确保线程安全。
*   [#13884] 修复导入过程中MCP服务器验证API的JSON解析错误。
*   [#13902] 通过更新日志记录器初始化逻辑，修复已弃用的Log4j2包扫描警告。
*   [#13948] 修复通过控制台界面更新智能体卡片失败的问题。
*   [#13959] 修复在独立部署控制台/服务器时密码更新失败的问题。
*   [#13964] 修复当`nacos.extension.ai.enabled`设置为false时，Nacos启动失败的问题。


### 1.1、简介

从Nacos2.2版本开始，Nacos提供了数据源扩展插件，以便让需要进行其他数据库适配的用户自己编写插件来保存数据。当前项目插件目前已简单适配Postgresql。


当前插件项目地址：
[https://github.com/lilinhai/nacos-datasource-plugin-ext](https://github.com/lilinhai/nacos-datasource-plugin-ext)

欢迎大家访问我的博客：[Nacos-3.1.1适配PostgreSQL数据库 https://linhai.blog.csdn.net/article/details/147592681](https://linhai.blog.csdn.net/article/details/147592681)

我已将pg数据源插件打成了jar包，传到maven中央仓库，大家只需下载该jar，然后放入`nacos`的`home`目录下的`plugins`插件目录，再配置`pg`数据源信息，即可使用！

**PG适配Nacos3.1.1插件下载**

**1. 直接下载该jar**：[https://repo1.maven.org/maven2/com/sinhy/nacos-postgresql-datasource-plugin-ext/3.1.1/nacos-postgresql-datasource-plugin-ext-3.1.1.jar](https://repo1.maven.org/maven2/com/sinhy/nacos-postgresql-datasource-plugin-ext/3.1.1/nacos-postgresql-datasource-plugin-ext-3.1.1.jar)

**2. 从以下连接下载**
[https://github.com/lilinhai/nacos-datasource-plugin-ext/releases/download/3.1.1/nacos-postgresql-datasource-plugin-ext-3.1.1.jar](https://github.com/lilinhai/nacos-datasource-plugin-ext/releases/download/3.1.1/nacos-postgresql-datasource-plugin-ext-3.1.1.jar)


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
    <version>3.1.1</version>
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