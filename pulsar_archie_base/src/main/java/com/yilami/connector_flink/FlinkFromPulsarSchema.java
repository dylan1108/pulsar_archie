package com.yilami.connector_flink;

import com.yilami.pojo.User;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.pulsar.FlinkPulsarSink;
import org.apache.flink.streaming.connectors.pulsar.FlinkPulsarSource;
import org.apache.flink.streaming.connectors.pulsar.config.RecordSchemaType;
import org.apache.flink.streaming.connectors.pulsar.internal.AvroDeser;
import org.apache.flink.streaming.connectors.pulsar.internal.AvroSer;
import org.apache.flink.streaming.util.serialization.PulsarSerializationSchemaWrapper;

import java.util.Optional;
import java.util.Properties;

// 需求: 基于Flink实现读取一个POJO类型的数据, 将将数据写入到Pulsar中
public class FlinkFromPulsarSchema {

    public static void main(String[] args) throws Exception {

        //1. 创建Flink流式处理的核心环境类对象
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //2. 添加source组件: 从Pulsar中读取数据
        Properties props = new Properties();
        props.setProperty("topic","persistent://itcast_pulsar_t/itcast_pulsar_n/my_tt03");
        FlinkPulsarSource<User> pulsarSource = new FlinkPulsarSource<User>(
                "pulsar://node1:6650,node2:6650,node3:6650",
                "http://node1:8080,node2:8080,node3:8080",
                AvroDeser.of(User.class),
                props
        );
        DataStreamSource<User> streamSource = env.addSource(pulsarSource);
        //3. 添加转换处理操作
        //4.添加sink的组件: 将处理后的数据写出到Pulsar中
        streamSource.print();
        PulsarSerializationSchemaWrapper<User> pulsarSerialization = new PulsarSerializationSchemaWrapper.Builder<User>(AvroSer.of(User.class))
                .usePojoMode(User.class, RecordSchemaType.AVRO)
                .build();

        FlinkPulsarSink<User> pulsarSink = new FlinkPulsarSink<User>(

                "pulsar://node1:6650,node2:6650,node3:6650",
                "http://node1:8080,node2:8080,node3:8080",
                Optional.of("persistent://itcast_pulsar_t/itcast_pulsar_n/my_tt04"),
                new Properties(),
                pulsarSerialization
        );

        streamSource.addSink(pulsarSink);

        //5. 启动flink程序
        env.execute("Pulsar connector Flink");

    }

}
