# storm-demo
storm demo，包括storm与其他组件的组合，以及java并发问题测试。    


## 1.wordcount
storm-wordcount  


## 2.并行度问题引出的java并发编程问题
java-concurrent   
  
设置多个线程执行， builder.setBolt("countBolt", new CountBolt(),`3`)。   

数据源源不断的产生，用map存数据，要保证每次取map，拿到的都是最新的。 

## 3.storm与kafka整合
storm-kafka

## 4.storm与HDFS整合
storm-hdfs

一定要给hdfs上的文件夹更改权限，否则hdfs上不会产生数据   
```
hadoop fs -chmod 777  /storm-hdfs
```

## 5.storm ack机制测试
storm-ack

开启测试，需要用com.xin.storm.ack.RandomAckBolt2.execute()注释里面的代码。

## 6.storm的定时器以及与mysql的整合
storm-mysql

打包插件：maven-assembly-plugin

