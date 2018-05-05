package com.xin.storm.mysql;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.Random;

public class RandomSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private String[] strArr;
    private Random random;

    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        strArr = new String[]{"张三 24","李四 25","按住啦baby 28","王五 30"};
        random = new Random();
    }

    public void nextTuple() {

        try {
            Thread.sleep(1000);
            String string = strArr[random.nextInt(strArr.length)];
            collector.emit(new Values(string));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("randomStr"));
    }
}
