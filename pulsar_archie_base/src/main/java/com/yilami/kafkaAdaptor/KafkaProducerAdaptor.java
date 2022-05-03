package com.yilami.kafkaAdaptor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

// 构建kafka的生产者API
public class KafkaProducerAdaptor {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "pulsar://node1:6650,node2:6650,node3:6650");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                    "persistent://itcast_pulsar_t/itcast_pulsar_n/t_topic8",
                    Integer.toString(i),
                    Integer.toString(i)
            );
            producer.send(producerRecord).get();
        }


        producer.close();
    }
}
