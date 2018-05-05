package com.xin.storm.wordcount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

public class StormDemo {

    public static void main(String[] args) throws Exception {

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("randomSpout", new RandomSpout());
        builder.setBolt("splitBolt", new SplitBolt()).localOrShuffleGrouping("randomSpout");
        builder.setBolt("countBolt", new CountBolt()).localOrShuffleGrouping("splitBolt");

        Config conf = new Config();
        conf.setDebug(false);

        StormTopology topology = builder.createTopology();

        if (args.length > 0) {
            StormSubmitter.submitTopology("wordcount", conf, topology);
        } else {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("wordcount", conf, topology);
        }
    }
}
