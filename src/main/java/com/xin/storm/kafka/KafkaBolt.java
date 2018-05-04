package com.xin.storm.kafka;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

public class KafkaBolt extends BaseBasicBolt {
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        Object value = input.getValue(4);
        System.out.println(value);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
