package com.yilami.sparkAdaptor;

import org.apache.pulsar.client.impl.auth.AuthenticationDisabled;
import org.apache.pulsar.client.impl.conf.ConsumerConfigurationData;
import org.apache.pulsar.spark.SparkStreamingPulsarReceiver;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.util.HashSet;
import java.util.Set;

public class SparkStreamingAdaptor {

    public static void main(String[] args) throws InterruptedException {
        String serviceUrl = "pulsar://node1:6650,node2:6650,node3:6650";
        String topic = "persistent://public/default/test_src";
        String subs = "test_sub";
        //1. 创建Java Spark Streaming 对象
        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("Pulsar Spark Adaptor");
        JavaStreamingContext streamingContext = new JavaStreamingContext(sparkConf, Durations.seconds(10));

        //2. 设置数据源: 从Pulsar中读取数据
        ConsumerConfigurationData<byte[]> pulsarConf = new ConsumerConfigurationData<>();
        Set<String> set = new HashSet<>();
        set.add(topic);
        pulsarConf.setTopicNames(set);
        pulsarConf.setSubscriptionName(subs);
        SparkStreamingPulsarReceiver pulsarReceiver = new SparkStreamingPulsarReceiver(serviceUrl, pulsarConf, new AuthenticationDisabled());

        JavaReceiverInputDStream<byte[]> lineStream = streamingContext.receiverStream(pulsarReceiver);

        //3. 对接收到数据进行处理
        JavaDStream<String> stream = lineStream.map(new Function<byte[], String>() {
            @Override
            public String call(byte[] v1) throws Exception {

                return new String(v1);
            }
        });
        //4. 输出操作
        stream.print();

        //5. 启动
        streamingContext.start();
        streamingContext.awaitTermination();

    }
}
