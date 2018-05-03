package com.xin.storm.wordcount;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

public class CountBolt extends BaseBasicBolt {

    private ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {

        String word = tuple.getStringByField("word");
        Integer num = tuple.getIntegerByField("num");
        if (map.contains(word)) {
            map.put(word, map.get(word) + num);
        } else {
            map.put(word, num);
        }
        System.out.println(map);

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
