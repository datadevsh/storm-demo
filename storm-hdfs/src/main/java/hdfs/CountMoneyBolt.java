package hdfs;

import com.alibaba.fastjson.JSONObject;
import domain.PaymentInfo;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.concurrent.ConcurrentHashMap;

public class CountMoneyBolt extends BaseBasicBolt {


    private static ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {

        String payment = input.getStringByField("order-line");
        PaymentInfo paymentInfo = JSONObject.parseObject(payment, PaymentInfo.class);
        long payPrice = paymentInfo.getPayPrice();

        if (map.containsKey("amount")) {
            map.put("amount", map.get("amount") + payPrice);
        } else {
            map.put("amount", payPrice);
        }

        collector.emit(new Values(payment));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("count-money"));
    }
}
