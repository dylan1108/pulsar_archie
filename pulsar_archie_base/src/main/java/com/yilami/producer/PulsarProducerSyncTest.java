package com.yilami.producer;


import org.apache.pulsar.client.api.*;

import java.util.Random;

// 演示Pulsar 生产者 同步发送
public class PulsarProducerSyncTest {
    public static void main(String[] args) throws Exception {

        //1. 创建Pulsar的客户端对象
        PulsarClient pulsarClient = PulsarClient.builder().serviceUrl("pulsar://192.168.0.10:16650,192.168.0.10:26650,192.168.0.10:36650").build();

        //2. 通过客户端创建生产者的对象

        Producer<String> producer = pulsarClient.newProducer(Schema.STRING)
                .topic("persistent://t1/ns1/t_topicWithPartition")
                .accessMode(ProducerAccessMode.WaitForExclusive)
                .create();
        Random random = new Random(10000);

        sendMsg(producer,random,10000);
        //3. 使用生产者发送数据

        System.out.println("数据生产完成....");
        //4. 释放资源
        producer.close();
        pulsarClient.close();


    }

    private static void sendMsg(Producer<String> producer,Random random,int loopCNT) throws PulsarClientException {
        long sTime=System.currentTimeMillis();
        for(int i=0;i<loopCNT;i++){
            int code=random.nextInt();
            producer.send(String.format("Pulsar Msg_IDX[%d]_RD[%d]",i,code));
        }
        System.out.println(String.format("Finish SendMsg[%d],and cost:[%d]",loopCNT, (System.currentTimeMillis()-sTime)));
    }
}
