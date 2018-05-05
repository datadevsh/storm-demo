package com.xin.storm.mysql;

import com.google.common.collect.Lists;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.jdbc.bolt.JdbcInsertBolt;
import org.apache.storm.jdbc.common.Column;
import org.apache.storm.jdbc.common.ConnectionProvider;
import org.apache.storm.jdbc.common.HikariCPConnectionProvider;
import org.apache.storm.jdbc.mapper.JdbcMapper;
import org.apache.storm.jdbc.mapper.SimpleJdbcMapper;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrmMysqlMain {

    public static void main(String[] args) throws Exception {

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("randomStrSpout", new RandomSpout());
        builder.setBolt("tickTimeBolt", new TickTimeBolt()).localOrShuffleGrouping("randomStrSpout");

        Map hikariConfigMap = new HashMap();
        hikariConfigMap.put("dataSourceClassName", "com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikariConfigMap.put("dataSource.url", "jdbc:mysql://node11/log_monitor");
        hikariConfigMap.put("dataSource.user", "root");
        hikariConfigMap.put("dataSource.password", "root");
        ConnectionProvider connectionProvider = new HikariCPConnectionProvider(hikariConfigMap);

        String tableName = "user";
        List<Column> columnSchema = Lists.newArrayList(
                new Column("userId", java.sql.Types.INTEGER),
                new Column("name", java.sql.Types.VARCHAR),
                new Column("age", java.sql.Types.INTEGER));
        JdbcMapper simpleJdbcMapper = new SimpleJdbcMapper(columnSchema);

		/*
		// 也可以这么写
		JdbcInsertBolt userPersistanceBolt = new JdbcInsertBolt(connectionProvider, simpleJdbcMapper)
		                                    .withTableName("welc")
		                                    .withQueryTimeoutSecs(30);
		                                    Or
		                                    */

        JdbcInsertBolt userPersistanceBolt = new JdbcInsertBolt(connectionProvider, simpleJdbcMapper)
                .withInsertQuery("insert into user values (?,?,?)")
                .withQueryTimeoutSecs(30);
        builder.setBolt("jdbcBolt", userPersistanceBolt).fieldsGrouping("tickTimeBolt", new Fields("name", "age"));

        Config conf = new Config();
        conf.setDebug(false);

        if (args != null && args.length > 0) {
            StormSubmitter submitter = new StormSubmitter();
            submitter.submitTopology(args[0], conf, builder.createTopology());
        } else {
            StormTopology wc = builder.createTopology();
            //1.本地模式提交
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("mywordcount", conf, wc);
        }
    }
}
