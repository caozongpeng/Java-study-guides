### RocketMq Cluster搭建
基于windows版本来进行搭建

### 一、下载RocketMQ
目前最新版本为4.7.1 下载地址：https://mirrors.bfsu.edu.cn/apache/rocketmq/4.7.1/rocketmq-all-4.7.1-bin-release.zip

### 二、配置环境变量
```
ROCKETMQ_HOME D:\kf\RocketMQ4.7
```

### 三、添加namesrv配置文件
在 `D:\kf\RocketMQ4.7\conf` 下创建两个文件,启动两个namesrv服务
```
namesrv-a.properties
listenPort=9876

namesrv-b.properties
listenPort=9875
```
### 四、修改runserver.cmd和runbroker.cmd
```
runserver.cmd
set "JAVA_OPT=%JAVA_OPT% -server -Xms2g -Xmx2g -Xmn1g -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"
修改为
set "JAVA_OPT=%JAVA_OPT% -server -Xms512m -Xmx512m -Xmn256m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m"

runbroker.cmd
set "JAVA_OPT=%JAVA_OPT% -server -Xms2g -Xmx2g -Xmn1g"
修改为
set "JAVA_OPT=%JAVA_OPT% -server -Xms512m -Xmx512m -Xmn256m"
```
### 五、修改broker配置文件
在 `D:\kf\RocketMQ4.7\conf\2m-2s-async`目录下默认有四个properties文件

分别为 broker-a.properties、broker-a-s.properties、broker-b.properties、broker-b-s.properties代表双主双从

#### broker-a.properties
```properties
#所属集群名字
brokerClusterName=rocketmq-cluster
#broker名字，名字可重复,为了管理,每个master起一个名字,他的slave同他,eg:Amaster叫broker-a,他的slave也叫broker-a
brokerName=broker-a
#0 表示 Master，>0 表示 Slave
brokerId=0
#nameServer地址，分号分割
namesrvAddr=127.0.0.1:9876;127.0.0.1:9875
#在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
defaultTopicQueueNums=4
#是否允许 Broker 自动创建Topic，建议线下开启，线上关闭
autoCreateTopicEnable=true
#是否允许 Broker 自动创建订阅组，建议线下开启，线上关闭
autoCreateSubscriptionGroup=true
#Broker 对外服务的监听端口,
listenPort=10911
#删除文件时间点，默认凌晨 4点
deleteWhen=04
#文件保留时间，默认 48 小时
fileReservedTime=120
#commitLog每个文件的大小默认1G
mapedFileSizeCommitLog=1073741824
#ConsumeQueue每个文件默认存30W条，根据业务情况调整
mapedFileSizeConsumeQueue=300000
#destroyMapedFileIntervalForcibly=120000
#redeleteHangedFileInterval=120000
#检测物理文件磁盘空间
diskMaxUsedSpaceRatio=88
#存储路径
storePathRootDir=D:\kf\RocketMQ4.7\store\broker-a
#commitLog 存储路径
storePathCommitLog=D:\kf\RocketMQ4.7\store\broker-a\commitlog
#消费队列存储路径存储路径
storePathConsumeQueue=D:\kf\RocketMQ4.7\store\broker-a\consumequeue
#消息索引存储路径
storePathIndex=D:\kf\RocketMQ4.7\store\broker-a\index
#checkpoint 文件存储路径
storeCheckpoint=D:\kf\RocketMQ4.7\store\broker-a\checkpoint
#abort 文件存储路径
abortFile=D:\kf\RocketMQ4.7\store\broker-a\abort
#限制的消息大小
maxMessageSize=65536
#flushCommitLogLeastPages=4
#flushConsumeQueueLeastPages=2
#flushCommitLogThoroughInterval=10000
#flushConsumeQueueThoroughInterval=60000
#Broker 的角色
#- ASYNC_MASTER 异步复制Master
#- SYNC_MASTER 同步双写Master
#- SLAVE
brokerRole=ASYNC_MASTER
#刷盘方式
#- ASYNC_FLUSH 异步刷盘
#- SYNC_FLUSH 同步刷盘
flushDiskType=ASYNC_FLUSH
#checkTransactionMessageEnable=false
#发消息线程池数量
#sendMessageThreadPoolNums=128
#拉消息线程池数量
#pullMessageThreadPoolNums=128
```
#### broker-a-s.properties
```properties
#所属集群名字
brokerClusterName=rocketmq-cluster
#broker名字，名字可重复,为了管理,每个master起一个名字,他的slave同他,eg:Amaster叫broker-a,他的slave也叫broker-a
brokerName=broker-a
#0 表示 Master，>0 表示 Slave
brokerId=1
#nameServer地址，分号分割
namesrvAddr=127.0.0.1:9876;127.0.0.1:9875
#在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
defaultTopicQueueNums=4
#是否允许 Broker 自动创建Topic，建议线下开启，线上关闭
autoCreateTopicEnable=true
#是否允许 Broker 自动创建订阅组，建议线下开启，线上关闭
autoCreateSubscriptionGroup=true
#Broker 对外服务的监听端口,
listenPort=10931
#删除文件时间点，默认凌晨 4点
deleteWhen=04
#文件保留时间，默认 48 小时
fileReservedTime=120
#commitLog每个文件的大小默认1G
mapedFileSizeCommitLog=1073741824
#ConsumeQueue每个文件默认存30W条，根据业务情况调整
mapedFileSizeConsumeQueue=300000
#destroyMapedFileIntervalForcibly=120000
#redeleteHangedFileInterval=120000
#检测物理文件磁盘空间
diskMaxUsedSpaceRatio=88
#存储路径
storePathRootDir=D:\kf\RocketMQ4.7\store\broker-a-s
#commitLog 存储路径
storePathCommitLog=D:\kf\RocketMQ4.7\store\broker-a-s\commitlog
#消费队列存储路径存储路径
storePathConsumeQueue=D:\kf\RocketMQ4.7\store\broker-a-s\consumequeue
#消息索引存储路径
storePathIndex=D:\kf\RocketMQ4.7\store\broker-a-s\index
#checkpoint 文件存储路径
storeCheckpoint=D:\kf\RocketMQ4.7\store\broker-a-s\checkpoint
#abort 文件存储路径
abortFile=D:\kf\RocketMQ4.7\store\broker-a-s\abort
#限制的消息大小
maxMessageSize=65536
#flushCommitLogLeastPages=4
#flushConsumeQueueLeastPages=2
#flushCommitLogThoroughInterval=10000
#flushConsumeQueueThoroughInterval=60000
#Broker 的角色
#- ASYNC_MASTER 异步复制Master
#- SYNC_MASTER 同步双写Master
#- SLAVE
brokerRole=SLAVE
#刷盘方式
#- ASYNC_FLUSH 异步刷盘
#- SYNC_FLUSH 同步刷盘
flushDiskType=ASYNC_FLUSH
#checkTransactionMessageEnable=false
#发消息线程池数量
#sendMessageThreadPoolNums=128
#拉消息线程池数量
#pullMessageThreadPoolNums=128
```

#### broker-b.properties
```properties
#所属集群名字
brokerClusterName=rocketmq-cluster
#broker名字，名字可重复,为了管理,每个master起一个名字,他的slave同他,eg:Amaster叫broker-a,他的slave也叫broker-a
brokerName=broker-b
#0 表示 Master，>0 表示 Slave
brokerId=0
#nameServer地址，分号分割
namesrvAddr=127.0.0.1:9876;127.0.0.1:9875
#在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
defaultTopicQueueNums=4
#是否允许 Broker 自动创建Topic，建议线下开启，线上关闭
autoCreateTopicEnable=true
#是否允许 Broker 自动创建订阅组，建议线下开启，线上关闭
autoCreateSubscriptionGroup=true
#Broker 对外服务的监听端口,
listenPort=10951
#删除文件时间点，默认凌晨 4点
deleteWhen=04
#文件保留时间，默认 48 小时
fileReservedTime=120
#commitLog每个文件的大小默认1G
mapedFileSizeCommitLog=1073741824
#ConsumeQueue每个文件默认存30W条，根据业务情况调整
mapedFileSizeConsumeQueue=300000
#destroyMapedFileIntervalForcibly=120000
#redeleteHangedFileInterval=120000
#检测物理文件磁盘空间
diskMaxUsedSpaceRatio=88
#存储路径
storePathRootDir=D:\kf\RocketMQ4.7\store\broker-b
#commitLog 存储路径
storePathCommitLog=D:\kf\RocketMQ4.7\store\broker-b\commitlog
#消费队列存储路径存储路径
storePathConsumeQueue=D:\kf\RocketMQ4.7\store\broker-b\consumequeue
#消息索引存储路径
storePathIndex=D:\kf\RocketMQ4.7\store\broker-b\index
#checkpoint 文件存储路径
storeCheckpoint=D:\kf\RocketMQ4.7\store\broker-b\checkpoint
#abort 文件存储路径
abortFile=D:\kf\RocketMQ4.7\store\broker-b\abort
#限制的消息大小
maxMessageSize=65536
#flushCommitLogLeastPages=4
#flushConsumeQueueLeastPages=2
#flushCommitLogThoroughInterval=10000
#flushConsumeQueueThoroughInterval=60000
#Broker 的角色
#- ASYNC_MASTER 异步复制Master
#- SYNC_MASTER 同步双写Master
#- SLAVE
brokerRole=ASYNC_MASTER
#刷盘方式
#- ASYNC_FLUSH 异步刷盘
#- SYNC_FLUSH 同步刷盘
flushDiskType=ASYNC_FLUSH
#checkTransactionMessageEnable=false
#发消息线程池数量
#sendMessageThreadPoolNums=128
#拉消息线程池数量
#pullMessageThreadPoolNums=128
```

#### broker-b-s.properties
```properties
#所属集群名字
brokerClusterName=rocketmq-cluster
#broker名字，名字可重复,为了管理,每个master起一个名字,他的slave同他,eg:Amaster叫broker-a,他的slave也叫broker-a
brokerName=broker-b
#0 表示 Master，>0 表示 Slave
brokerId=1
#nameServer地址，分号分割
namesrvAddr=127.0.0.1:9876;127.0.0.1:9875
#在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
defaultTopicQueueNums=4
#是否允许 Broker 自动创建Topic，建议线下开启，线上关闭
autoCreateTopicEnable=true
#是否允许 Broker 自动创建订阅组，建议线下开启，线上关闭
autoCreateSubscriptionGroup=true
#Broker 对外服务的监听端口,
listenPort=10971
#删除文件时间点，默认凌晨 4点
deleteWhen=04
#文件保留时间，默认 48 小时
fileReservedTime=120
#commitLog每个文件的大小默认1G
mapedFileSizeCommitLog=1073741824
#ConsumeQueue每个文件默认存30W条，根据业务情况调整
mapedFileSizeConsumeQueue=300000
#destroyMapedFileIntervalForcibly=120000
#redeleteHangedFileInterval=120000
#检测物理文件磁盘空间
diskMaxUsedSpaceRatio=88
#存储路径
storePathRootDir=D:\kf\RocketMQ4.7\store\broker-b-s
#commitLog 存储路径
storePathCommitLog=D:\kf\RocketMQ4.7\store\broker-b-s\commitlog
#消费队列存储路径存储路径
storePathConsumeQueue=D:\kf\RocketMQ4.7\store\broker-b-s\consumequeue
#消息索引存储路径
storePathIndex=D:\kf\RocketMQ4.7\store\broker-b-s\index
#checkpoint 文件存储路径
storeCheckpoint=D:\kf\RocketMQ4.7\store\broker-b-s\checkpoint
#abort 文件存储路径
abortFile=D:\kf\RocketMQ4.7\store\broker-b-s\abort
#限制的消息大小
maxMessageSize=65536
#flushCommitLogLeastPages=4
#flushConsumeQueueLeastPages=2
#flushCommitLogThoroughInterval=10000
#flushConsumeQueueThoroughInterval=60000
#Broker 的角色
#- ASYNC_MASTER 异步复制Master
#- SYNC_MASTER 同步双写Master
#- SLAVE
brokerRole=SLAVE
#刷盘方式
#- ASYNC_FLUSH 异步刷盘
#- SYNC_FLUSH 同步刷盘
flushDiskType=ASYNC_FLUSH
#checkTransactionMessageEnable=false
#发消息线程池数量
#sendMessageThreadPoolNums=128
#拉消息线程池数量
#pullMessageThreadPoolNums=128
```
### 六、rocketMQ Web管理界面
地址：https://github.com/apache/rocketmq-externals

直接clone 到本地
```
git clone git@github.com:apache/rocketmq-externals.git

进入 rocketmq-console\src\main\resources 修改 application.properties

修改启动端口
server.port=8081
你自己的namesrv服务地地址
rocketmq.config.namesrvAddr=127.0.0.1:9876;127.0.0.1:9875

进入 rocketmq-console 目录下执行maven打包命令

mvn clean package -Dmaven.test.skip=true

打包成功可以到 rocketmq-console\target 目录下找到
rocketmq-console-ng-1.0.1.jar

java -jar rocketmq-console-ng-1.0.1.jar 即可运行

运行成功直接访问 localhost:8081 即可。
```

### 七、新建启动文件
在 `D:\kf\RocketMQ4.7`新建rocketMq.cmd文件
```
@echo off
 
:: 检查启动环境
if not exist "%ROCKETMQ_HOME%\bin\mqnamesrv.cmd" echo Please set the ROCKETMQ_HOME variable in your environment! & EXIT /B 1
 
if not exist "%ROCKETMQ_HOME%\bin\mqbroker.cmd" echo Please check your mqbroker.cmd! & EXIT /B 1
 
:: 获取本机IP地址，ip信息可以写到配置文件中，下面这段根据自己情况可以省略。
setlocal ENABLEEXTENSIONS & set "i=0.0.0.0" & set "j="
 
for /f "tokens=4" %%a in ('route print^|findstr 0.0.0.0.*0.0.0.0') do (
  if not defined j for %%b in (%%a) do set "i=%%b" & set "j=1")
endlocal & set "ip=%i%"
set rocketpath = %ROCKETMQ_HOME%\bin
cd %rocketpath %
start mqnamesrv.cmd -c %ROCKETMQ_HOME%\conf\namesrv-a.properties
start mqnamesrv.cmd -c %ROCKETMQ_HOME%\conf\namesrv-b.properties
:: 启动完namesrv后再启动broker
pause
start mqbroker.cmd  -c %ROCKETMQ_HOME%\conf\2m-2s-async\broker-a.properties
start mqbroker.cmd  -c %ROCKETMQ_HOME%\conf\2m-2s-async\broker-a-s.properties
start mqbroker.cmd  -c %ROCKETMQ_HOME%\conf\2m-2s-async\broker-b.properties
start mqbroker.cmd  -c %ROCKETMQ_HOME%\conf\2m-2s-async\broker-b-s.properties
:: 如果有控制台程序，放在文件夹下启动
:: java -jar rocketmq-console-ng-1.0.0.jar --server.port=8085 --rocketmq.config.namesrvAddr=%ip%:9876;%ip%:9875
cd ..
java -jar rocketmq-console-ng-1.0.1.jar
```

### 八、遇到的坑
broker-*.properties 配置文件注意
```
所属集群名称
brokerClusterName=rocketmq-cluster

brokerName相同，brokerId=0为主，>0表示slave从

主：brokerName=broker-a  brokerId=0
从：brokerName=broker-a  brokerId=1

主：brokerName=broker-b  brokerId=0
从：brokerName=broker-b  brokerId=1

主：brokerRole=SYNC_MASTER
从：brokerRole=SLAVE
```