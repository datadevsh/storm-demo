package com.xin.storm.mysql;

import org.apache.storm.Config;
import org.apache.storm.Constants;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Delayed;

public class TickTimeBolt extends BaseBasicBolt {

    SimpleDateFormat format;

    /**
     * 定时器设置
     *
     * @return
     */
    @Override
    public Map<String, Object> getComponentConfiguration() {
        Config config = new Config();
        config.put(config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 5);
        return config;
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context) {

        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public void execute(Tuple input, BasicOutputCollector collector) {

        //这个Tuple是否是定时器
        if (input.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID)
                && input.getSourceStreamId().equals(Constants.SYSTEM_TICK_STREAM_ID)) {

            System.out.println(format.format(new Date()));
        } else {

            String str = input.getStringByField("randomStr");
            System.out.println("send data is :" + str);

            //这里发出的字段数据类型要和数据库表一致。
            if (str != null && str != "") {

                String[] split = str.split(" ");

                //注意，这里发送出来的数据的个数要跟数据库保持一致，数据的类型要跟数据库保持一致
                collector.emit(new Values(null, split[0], Integer.parseInt(split[1])));
            }
        }

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        //这里的字段，必须和数据库字段名字一致
        declarer.declare(new Fields("userId", "name", "age"));
    }
}
