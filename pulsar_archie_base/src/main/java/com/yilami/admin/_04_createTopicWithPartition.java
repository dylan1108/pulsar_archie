package com.yilami.admin;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.common.policies.data.RetentionPolicies;

import java.util.List;

public class _04_createTopicWithPartition {
    public static void main(String[] args) throws Exception {

        //1. 创建Pulsar的Admin管理对象
        String serviceHttpUrl = "http://192.168.0.10:18080,192.168.0.10:28080,192.168.0.10:38080";
        PulsarAdmin pulsarAdmin = PulsarAdmin.builder().serviceHttpUrl(serviceHttpUrl).build();
        //2. 执行相关的操作
        //2.1: 创建 Topic相关的操作: 有分区和没有分区, 以及持久化和非持久化
        //pulsarAdmin.topics().createNonPartitionedTopic("persistent://itcast_pulsar_t/itcast_pulsar_n/t_topic5");
//        pulsarAdmin.topics().createPartitionedTopic("persistent://t1/ns1/t_topicWithPartition",10);
        RetentionPolicies retention1 = pulsarAdmin.topics().getRetention("persistent://t1/ns1/test");
        RetentionPolicies retention = pulsarAdmin.topics().getRetention("persistent://t1/ns1/t_topicWithPartition");
        //pulsarAdmin.topics().createNonPartitionedTopic("non-persistent://itcast_pulsar_t/itcast_pulsar_n/t_topic2");

        // pulsarAdmin.topics().createPartitionedTopic("persistent://itcast_pulsar_t/itcast_pulsar_n/t_topic3",5);
        //pulsarAdmin.topics().createPartitionedTopic("non-persistent://itcast_pulsar_t/itcast_pulsar_n/t_topic5",5);

        //2.2: 查询当前有那些topic:
        /*List<String> topicList = pulsarAdmin.topics().getList("itcast_pulsar_t/itcast_pulsar_n");
        for (String topic : topicList) {
            System.out.println(topic);
        }*/
        List<String> topicList = pulsarAdmin.topics().getPartitionedTopicList("t1/ns1");

        for (String topic : topicList) {
            System.out.println(topic);
        }

        //2.3 修改Topic 分片的数量
        //pulsarAdmin.topics().updatePartitionedTopic("persistent://itcast_pulsar_t/itcast_pulsar_n/t_topic3",7);

        //2.4 一共有多少个分片呢
        //int partitions = pulsarAdmin.topics().getPartitionedTopicMetadata("persistent://itcast_pulsar_t/itcast_pulsar_n/t_topic3").partitions;
        //System.out.println(partitions);

        //2.5: 删除Topic
        //pulsarAdmin.topics().deletePartitionedTopic("persistent://itcast_pulsar_t/itcast_pulsar_n/t_topic3");

        //3. 关闭admin对象
        pulsarAdmin.close();
    }
}
