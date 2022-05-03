package com.yilami.producer;


import com.yilami.pojo.User;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.impl.schema.AvroSchema;

// 演示Pulsar 生产者 schema的方式方案
public class PulsarProducerSchemaTest {
    public static void main(String[] args) throws Exception {

        //1. 创建pulsar的客户端对象

        PulsarClient pulsarClient = PulsarClient.builder().serviceUrl("pulsar://node1:6650,node2:6650,node3:6650").build();

        //2. 基于客户端对象进行构建生产者对象
        Producer<User> producer = pulsarClient.newProducer(AvroSchema.of(User.class))
                .topic("persistent://itcast_pulsar_t/itcast_pulsar_n/my_tt03")
                .create();
        //3. 进行数据生产
        User user = new User();
        user.setName("张三");
        user.setAge("20");
        user.setAddress("北京");

        producer.send(user);
        //4. 释放资源
        producer.close();
        pulsarClient.close();
    }
}
