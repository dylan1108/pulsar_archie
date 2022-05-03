package com.yilami.consumer;

import org.apache.pulsar.client.api.*;

import java.util.concurrent.TimeUnit;

// 演示 Pulsar的消费者的使用  批量消费
public class PulsarConsumerBatchTest {

    public static void main(String[] args) throws Exception{

        //1. 构建Pulsar的客户端对象
        PulsarClient pulsarClient = PulsarClient.builder().serviceUrl("pulsar://node1:6650,node2:6650,node3:6650").build();

        //2. 通过客户端构建消费者对象

        Consumer<String> consumer = pulsarClient.newConsumer(Schema.STRING)
                .topic("persistent://itcast_pulsar_t/itcast_pulsar_n/t_topic1")
                .subscriptionName("sub_04")
                // 设置支持批量读取参数配置
                .batchReceivePolicy(
                        BatchReceivePolicy.builder()
                                .maxNumBytes(1024 * 1024)
                                .maxNumMessages(100)
                                .timeout(2000, TimeUnit.MILLISECONDS)
                                .build()
                )
                .subscribe();

        //3. 循环读取数据
        while (true) {

            //3.1 读取消息(批量)
            Messages<String> messages = consumer.batchReceive();

            //3.2: 获取消息数据
            for (Message<String> message : messages) {
                String msg = message.getValue();

                System.out.println("消息数据为:"+msg);

                //3.3 ack确认
                consumer.acknowledge(message);
            }

        }

    }
}
