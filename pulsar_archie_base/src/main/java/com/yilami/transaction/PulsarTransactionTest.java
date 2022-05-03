package com.yilami.transaction;

import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.api.transaction.Transaction;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class PulsarTransactionTest {

    // Pulsar 事务的测试操作
    public static void main(String[] args) throws PulsarClientException, ExecutionException, InterruptedException {

        //1. 创建一个支持事务的Pulsar的客户端
        PulsarClient pulsarClient = PulsarClient.builder().serviceUrl("pulsar://node1:6650,node2:6650,node3:6650")
                .enableTransaction(true)
                .build();

        //2. 开启事务支持
        Transaction txn = pulsarClient.newTransaction().withTransactionTimeout(5, TimeUnit.MINUTES).build().get();

        try {
            //3. 执行相关的操作

            //3.1: 接收消息数据
            Consumer<String> consumer = pulsarClient.newConsumer(Schema.STRING)
                    .topic("txn_t3")
                    .subscriptionName("sub_txn")
                    .subscribe();

            Message<String> message = consumer.receive();

            //3.2: 处理数据操作
            System.out.println("消息数据为: "+message.getValue());

            //3.3:  将处理后的数据,发送到另一个Topic中
            Producer<String> producer = pulsarClient.newProducer(Schema.STRING)
                    .topic("txn_t4")
                    .sendTimeout(0,TimeUnit.MILLISECONDS)
                    .create();

            producer.newMessage(txn).value(message.getValue()).send();

            // 额外添加一个异常
            //double a = 1/0;

            //4. 确认消息
            consumer.acknowledge(message);

            //5.提交事务
            txn.commit();
        }catch (Exception e){
            // 如果有异常, 直接回滚
            txn.abort();
            e.printStackTrace();

        }

    }


}
