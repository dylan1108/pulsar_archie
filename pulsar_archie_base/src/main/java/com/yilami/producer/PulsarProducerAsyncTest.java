package com.yilami.producer;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;

// 演示Pulsar 生产者 异步发送
public class PulsarProducerAsyncTest {
    public static void main(String[] args) throws Exception {

        //1. 创建Pulsar的客户端对象
        PulsarClient pulsarClient = PulsarClient.builder().serviceUrl("pulsar://node1:6650,node2:6650,node3:6650").build();

        //2. 通过客户端构建生产者的对象
        Producer<String> producer = pulsarClient.newProducer(Schema.STRING)
                .topic("persistent://itcast_pulsar_t/itcast_pulsar_n/t_topic1")
                .create();
        //3. 进行数据发送操作
        // 发现数据并没有生产成功, 主要原因是
        //          因为采用异步的发送方案, 这种发送方案会先将数据写入到客户端缓存中, 当缓存中数据达到一批后 才会进行发送操作
        producer.sendAsync("hello async pulsar...2222");
        System.out.println("数据生产成功....");

        // 可以发送完成后, 让程序等待一下, 让其将缓冲区中数据刷新到pulsar上 然后在结束
        Thread.sleep(1000);
        //4. 释放资源
        producer.close();
        pulsarClient.close();
    }
}
