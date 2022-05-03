package com.yilami.consumer;


import org.apache.pulsar.client.api.*;

// 演示 Pulsar的消费者的使用
public class PulsarConsumerTest {

    public static void main(String[] args)  throws Exception{

        //1. 创建pulsar的客户端的对象
        PulsarClient pulsarClient = PulsarClient.builder().serviceUrl("pulsar://192.168.0.10:16650,192.168.0.10:26650,192.168.0.10:36650").build();

        //2. 基于客户端构建消费者对象

        Consumer<String> consumer = pulsarClient.newConsumer(Schema.STRING)
                .topic("persistent://t1/ns1/t_topicWithPartition")
                .subscriptionName("test")
                .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
                .subscriptionType(SubscriptionType.Exclusive)
                .subscriptionMode(SubscriptionMode.Durable)
                .subscribe();

        //3. 循环从消费者读取数据
        System.out.println("========================================================");
        while(true) {
            //3.1: 接收消息
            Message<String> message = consumer.receive();

            //3.2: 获取消息
            String msg = message.getValue();

            //3.3: 处理数据--- 业务操作
            System.out.println("消息数据为:"+msg);

            //3.4: ack确认操作
            consumer.acknowledge(message);

            // 如果消费失败了, 可以采用try catch方式进行捕获异常, 捕获后, 可以进行告知没有消费
            //consumer.negativeAcknowledge(message);

        }



    }
}
