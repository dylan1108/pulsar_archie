package com.yilami.pulsar.utils;

import com.yilami.pulsar.constant.PulsarConstant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

/**
 * 主题工具类
 * @author yilami
 **/
public final class TopicUtil {

    private TopicUtil(){}

    /**
     * 拼接topic
     * @return 完整topic路径
     */
    public static String generateTopic(@NotNull Boolean persistent, @NotBlank String tenant, @NotBlank String namespace, @NotBlank String topic){

        StringJoiner stringJoiner = new StringJoiner(PulsarConstant.PATH_SPLIT);
        stringJoiner.add(tenant).add(namespace).add(topic);
        if (Boolean.TRUE.equals(persistent)){
            return PulsarConstant.PERSISTENT + stringJoiner.toString();
        }else {
            return PulsarConstant.NON_PERSISTENT + stringJoiner.toString();
        }
    }
}
