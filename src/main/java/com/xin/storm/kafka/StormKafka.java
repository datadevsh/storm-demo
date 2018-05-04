package com.xin.storm.kafka;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;

public class StormKafka {

    public static void main(String[] args) throws Exception{

        KafkaSpoutConfig.Builder<String, String> builder = KafkaSpoutConfig.builder("node11:9092,node12:9092,node13:9092","test");
        builder.setGroupId("kafka-storm-group");
        builder.setFirstPollOffsetStrategy(KafkaSpoutConfig.FirstPollOffsetStrategy.UNCOMMITTED_LATEST);
        KafkaSpoutConfig<String, String> kafkaSpoutConfig = builder.build();

        KafkaSpout<String, String> kafkaSpout = new KafkaSpout<>(kafkaSpoutConfig);
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("kafkaSpout",kafkaSpout);
        topologyBuilder.setBolt("KafkaBolt",new KafkaBolt()).localOrShuffleGrouping("kafkaSpout");
        StormTopology topology = topologyBuilder.createTopology();

        Config conf = new Config();

        if (args.length > 0) {
            StormSubmitter.submitTopology("kafka-storm", conf, topology);
        } else {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("kafka-storm", conf, topology);
        }

    }
}
