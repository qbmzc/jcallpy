# jcallpy

java调用python文件

## 启动依赖的服务

```bash
Java8运行环境
MySql/MariaDB 
maven # 开发需要
```

## 启动

1. 创建数据库
2. 修改配置文件`application.yml`中的数据库连接
3. 运行`./data/*.sql`数据库脚本，生成表
4. 


## 部署

1. mavne 打包成jar

```bash
mvn clean & mvn package -DskipTests=true
```

```bash
nohup java -server -Xms256m -Xmx512m -XX:+UseG1GC -jar -*.jar &
```