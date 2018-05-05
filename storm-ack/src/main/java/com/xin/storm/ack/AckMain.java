package com.xin.storm.ack;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;

public class AckMain {

    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("randomAckSpout",new RandomAckSpout());
        builder.setBolt("randomAckBolt1",new RandomAckBolt1()).localOrShuffleGrouping("randomAckSpout");
        builder.setBolt("randomAckBolt2",new RandomAckBolt2()).localOrShuffleGrouping("randomAckBolt1");


        Config config = new Config();

        /*

        //设置我们ack的线程的数量，默认是一个，如果数据量比较大，需要将ack的线程数调大，用于调优
        config.setNumAckers(3);
        //设置我们的内存池里面有多少个状态没有清掉的时候就不要再继续发送数据了
        config.setMaxSpoutPending(50);

         */

        if(args !=null && args.length > 0){
            StormSubmitter.submitTopology(args[0],config,builder.createTopology());
        }else{
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("ack",config,builder.createTopology());
        }

    }

}
