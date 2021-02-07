# EdgeComputing  

#### 介绍
边缘计算程序，采集数据并上传到服务端。  


#### 软件架构
spring boot、mybatis、shiro、h2  


#### 工具  
http://localhost:7001/swagger-ui.html  
http://localhost:7001/h2-console/  
http://localhost:7001/druid/index.html  


#### 上线部署注意事项  
1、修改环境为 prod  
1、盒子上 /data/ec/scripts/ 目录下部署 sshconnect.sh脚本，盒子上安装expect  
1、修改 application.yml remote->dsm_carid  
2、修改log4j2配置，关闭日志输出   
3、启动 nohup java -jar ec.jar &   
4、debug启动 nohup java -jar -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 ec.jar &  
5、启动不输出日志 nohup java -jar ec.jar >/dev/null 2>&1 &  


测试环境远程debug  
nohup java -Xms768m -Xmx768m -Xmn512m -XX:MetaspaceSize=100m -XX:MaxMetaspaceSize=256m -XX:SurvivorRatio=10 -XX:+UseParallelGC -XX:+UseParallelOldGC -XX:-UseAdaptiveSizePolicy -XX:+DisableExplicitGC -jar -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 ec.jar >/dev/null 2>&1 &



