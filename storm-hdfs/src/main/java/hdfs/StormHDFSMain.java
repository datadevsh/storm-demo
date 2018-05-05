package hdfs;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.hdfs.bolt.HdfsBolt;
import org.apache.storm.hdfs.bolt.format.DefaultFileNameFormat;
import org.apache.storm.hdfs.bolt.format.DelimitedRecordFormat;
import org.apache.storm.hdfs.bolt.format.FileNameFormat;
import org.apache.storm.hdfs.bolt.format.RecordFormat;
import org.apache.storm.hdfs.bolt.rotation.FileRotationPolicy;
import org.apache.storm.hdfs.bolt.rotation.FileSizeRotationPolicy;
import org.apache.storm.hdfs.bolt.sync.CountSyncPolicy;
import org.apache.storm.hdfs.bolt.sync.SyncPolicy;
import org.apache.storm.topology.TopologyBuilder;

/**
 * 一定要给hdfs上的文件夹更改权限
 *  hadoop fs -chmod 777  /storm-hdfs
 */
public class StormHDFSMain {

    public static void main(String[] args) throws Exception{

        RecordFormat format = new DelimitedRecordFormat().withFieldDelimiter("\001");
        // 当数据达到100条，写入hdfs
        SyncPolicy syncPolicy = new CountSyncPolicy(100);
        // 当文件大小达到1 KB，就写入HDFS
        FileRotationPolicy rotationPolicy = new FileSizeRotationPolicy(1.0f, FileSizeRotationPolicy.Units.KB);
        FileNameFormat fileNameFormat = new DefaultFileNameFormat().withPath("/storm-hdfs/");
        HdfsBolt hdfsBolt = new HdfsBolt()
                .withFsUrl("hdfs://node11:9000")
                .withFileNameFormat(fileNameFormat)
                .withRecordFormat(format)
                .withRotationPolicy(rotationPolicy)
                .withSyncPolicy(syncPolicy);

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("orderSpout",new OrderSpout());
        builder.setBolt("countMoneyBolt",new CountMoneyBolt()).localOrShuffleGrouping("orderSpout");
        builder.setBolt("hdfsBolt",hdfsBolt).localOrShuffleGrouping("countMoneyBolt");

        Config config = new Config();
        StormTopology topology = builder.createTopology();

        if(args.length >0 ){
            StormSubmitter.submitTopology(args[0],config,topology);
        }else{
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("storm-hdfs",config,topology);
        }
    }
}
