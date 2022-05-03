package com.yilami.aop;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class RabbitMQTest {

    public static void main(String[] args) throws Exception {

        //1. 创建连接, 构建传输管道
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setVirtualHost("vhost1");
        connectionFactory.setHost("node1");
        connectionFactory.setPort(5672);

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();


        //2. 设置交换器
        String exchange = "ex";
        String queue = "qu";

        channel.exchangeDeclare(exchange, BuiltinExchangeType.FANOUT,true,false,false,null);

        //3. 设置 queue
        channel.queueDeclare(queue,true,false,false,null);

        //4. 绑定 queue 和 交换器
        channel.queueBind(queue,exchange,"");

        //5. 向交换器来生产数据即可

        for (int i = 0; i<10;i++){
            channel.basicPublish(exchange,"",null,("hello aop"+i).getBytes());
        }

        //6. 从队列中获取数据
        CountDownLatch countDownLatch = new CountDownLatch(10);

        channel.basicConsume(queue,true,new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消息数据为:"+new String(body));
                countDownLatch.countDown();
            }
        });

        countDownLatch.await();

        //7. 释放资源
        channel.close();
        connection.close();



    }
}
