# storm-demo
storm demo，包括java并发问题测试。    


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