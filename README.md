# storm-demo
storm demo，包括java并发问题测试。    


## 1.wordcount
com.xin.storm.wordcount.StormDemo   


## 2.并行度问题引出的java并发编程问题
com.xin.java.concurrent     
  
设置多个线程执行， builder.setBolt("countBolt", new CountBolt(),`3`)。   

数据源源不断的产生，用map存数据，要保证每次取map，拿到的都是最新的。 

## 3.storm与kafka整合
com.xin.storm.kafka.StormKafka  
