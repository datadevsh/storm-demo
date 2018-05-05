package com.xin.storm.ack;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

public class RandomAckBolt2 extends BaseRichBolt {

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {

        String bolt1 = input.getStringByField("bolt1");
        collector.ack(input);

        /*
        //打用这段代码，就可看到ack失败的效果

             try {
                String bolt1 = input.getStringByField("bolt1");
                System.out.println("我是bolt2收到的数据为"+bolt1);
                Thread.sleep(40000);
                collector.ack(input);
            } catch (Exception e) {
                 e.printStackTrace();
            }

         */

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
