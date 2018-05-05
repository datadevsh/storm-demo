package com.xin.storm.ack;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.Random;

public class RandomAckSpout  extends BaseRichSpout{

    private SpoutOutputCollector collector;
    private String[] arrays;
    private Random random;


    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        arrays = new String[]{"hello tom","hello hadoop","impala hue","sqoop oozie"};
        random = new Random();
    }

    @Override
    public void nextTuple() {
        String str = arrays[random.nextInt(arrays.length)];
        collector.emit(new Values(str),str);

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("str"));
    }


    @Override
    public void ack(Object msgId) {
        System.out.println("我是成功调用的方法，成功的数据为"+msgId.toString()+"哈哈哈哈哈");
    }


    @Override
    public void fail(Object msgId) {
        System.out.println("我是失败调用的方法，失败的数据为"+msgId.toString()+"呜呜呜呜呜");
    }
}
