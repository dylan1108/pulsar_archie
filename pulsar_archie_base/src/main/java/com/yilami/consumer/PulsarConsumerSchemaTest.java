package com.yilami.consumer;

import com.yilami.pojo.User;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.impl.schema.AvroSchema;

// 演示 Pulsar的消费者的使用  基于schema形式
public class PulsarConsumerSchemaTest {

    public static void main(String[] args) throws Exception {
        //1. 创建Pulsar的客户端对象
        PulsarClient pulsarClient = PulsarClient.builder().serviceUrl("pulsar://node1:6650,node2:6650,node3:6650").build();

        //2. 基于客户端对象构建消费者对象

        Consumer<User> consumer = pulsarClient.newConsumer(AvroSchema.of(User.class))
                .topic("persistent://itcast_pulsar_t/itcast_pulsar_n/my_tt04")
                .subscriptionName("sub_05")
                .subscribe();
        //3. 循环读取数据操作

        while(true){
            //3.1: 接收消息
            Message<User> message = consumer.receive();

            //3.2: 获取消息数据
            User msg = message.getValue();

            System.out.println(msg);

        }


    }
}
