package com.yilami.pulsar.configuration;

import cn.hutool.core.util.RandomUtil;
import com.yilami.pulsar.client.MultiPulsarClient;
import com.yilami.pulsar.ececption.PulsarAutoConfigException;
import com.yilami.pulsar.listener.BaseMessageListener;
import com.yilami.pulsar.listener.PulsarListener;
import com.yilami.pulsar.listener.ThreadPool;
import com.yilami.pulsar.properties.MultiPulsarProperties;
import com.yilami.pulsar.utils.TopicUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.shade.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 消费者自动初始化
 * @author yilami
 **/
@Slf4j
public class PulsarConsumerAutoConfigure implements CommandLineRunner {

    /**
     * 自定义消费者监听器列表，可为空
     */
    @Autowired(required = false)
    private List<BaseMessageListener> listeners;
    /**
     * 多数据源Pulsar客户端
     */
    private final MultiPulsarClient multiPulsarClient;
    /**
     * 多数据源Pulsar自定义配置
     */
    private final MultiPulsarProperties multiPulsarProperties;

    public PulsarConsumerAutoConfigure(MultiPulsarClient multiPulsarClient, MultiPulsarProperties multiPulsarProperties){
        this.multiPulsarClient = multiPulsarClient;
        this.multiPulsarProperties = multiPulsarProperties;
    }

    /**
     * 注入消费者到IOC容器
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        if (!CollectionUtils.isEmpty(this.listeners)){
            for (BaseMessageListener baseMessageListener : this.listeners) {
                // 获取当前监听器的@PulsarListener信息
                PulsarListener pulsarListener = AnnotationUtils.findAnnotation(baseMessageListener.getClass(), PulsarListener.class);
                if (Objects.nonNull(pulsarListener)){
                    try {
                        String sourceName = pulsarListener.sourceName();
                        PulsarClient client = this.multiPulsarClient.getOrDefault(sourceName, null);
                        if (Objects.isNull(client)){
                            log.error("[Pulsar] 数据源对应PulsarClient不存在，sourceName is {}",sourceName);
                            continue;
                        }
                        ConsumerBuilder<String> consumerBuilder = client.newConsumer(Schema.STRING).receiverQueueSize(pulsarListener.receiverQueueSize());
                        if (pulsarListener.topics().length > 0){
                            /**
                             * 初始化线程池
                             */
                            if (Boolean.TRUE.equals(baseMessageListener.enableAsync())){
                                log.info("[Pulsar] 消费者开启异步消费，开始初始化消费线程池....");
                                ThreadPool threadPool = pulsarListener.threadPool();
                                baseMessageListener.initThreadPool(threadPool.coreThreads(), threadPool.maxCoreThreads(), threadPool.keepAliveTime(), threadPool.maxQueueLength(), threadPool.threadPoolName());
                            }
                            List<String> topics = new ArrayList<>(pulsarListener.topics().length);
                            String tenant = StringUtils.isBlank(pulsarListener.tenant()) ? this.multiPulsarProperties.getTenantBySourceName(sourceName) : pulsarListener.tenant();
                            String namespace = StringUtils.isBlank(pulsarListener.namespace()) ? this.multiPulsarProperties.getNamespaceBySourceName(sourceName) : pulsarListener.namespace();
                            if (StringUtils.isBlank(tenant) || StringUtils.isBlank(namespace)){
                                log.error("[Pulsar] 消费者初始化失败，subscriptionName is {},sourceName is {},tenant is {},namespace is {}",pulsarListener.subscriptionName(),sourceName,tenant,namespace);
                                continue;
                            }
                            Boolean persistent = pulsarListener.persistent();
                            /**
                             * 处理topics
                             */
                            for (String topic : pulsarListener.topics()) {
                                topics.add(TopicUtil.generateTopic(persistent, tenant, namespace, topic));
                            }
                            consumerBuilder.topics(topics);
                            /**
                             * 处理订阅名称
                             */
                            String subscriptionName = StringUtils.isBlank(pulsarListener.subscriptionName())?"subscription_"+ RandomUtil.randomString(3):pulsarListener.subscriptionName();
                            consumerBuilder.subscriptionName(subscriptionName);
                            consumerBuilder.ackTimeout(Long.parseLong(pulsarListener.ackTimeout()), TimeUnit.MILLISECONDS);
                            consumerBuilder.subscriptionType(pulsarListener.subscriptionType());
                            /**
                             * 处理死信策略
                             */
                            if (Boolean.TRUE.equals(pulsarListener.enableRetry())){
                                DeadLetterPolicy deadLetterPolicy = DeadLetterPolicy.builder()
                                        .maxRedeliverCount(pulsarListener.maxRedeliverCount())
                                        .build();
                                if (StringUtils.isNotBlank(pulsarListener.retryLetterTopic())){
                                    deadLetterPolicy.setRetryLetterTopic(pulsarListener.retryLetterTopic());
                                }
                                if (StringUtils.isNotBlank(pulsarListener.deadLetterTopic())){
                                    deadLetterPolicy.setDeadLetterTopic(pulsarListener.deadLetterTopic());
                                }
                                consumerBuilder.enableRetry(pulsarListener.enableRetry()).deadLetterPolicy(deadLetterPolicy);
                            }else {
                                if (StringUtils.isNotBlank(pulsarListener.deadLetterTopic())){
                                    if (SubscriptionType.Exclusive.equals(pulsarListener.subscriptionType())){
                                        throw new PulsarAutoConfigException("[Pulsar] 消费端仅支持在Shared/Key_Shared模式下单独使用死信队列");
                                    }
                                    DeadLetterPolicy deadLetterPolicy = DeadLetterPolicy.builder()
                                            .maxRedeliverCount(pulsarListener.maxRedeliverCount())
                                            .deadLetterTopic(pulsarListener.deadLetterTopic())
                                            .build();
                                    consumerBuilder.deadLetterPolicy(deadLetterPolicy);
                                }
                            }
                            consumerBuilder.messageListener(baseMessageListener);
                            Consumer<String> consumer = consumerBuilder.subscribe();
                            log.info("[Pulsar] Consumer初始化完毕, sourceName is {}, topic is {},",sourceName,consumer.getTopic());
                        }
                    } catch (PulsarClientException e) {
                        throw new PulsarAutoConfigException("[Pulsar] consumer初始化异常",e);
                    }
                }
            }
        }else {
            log.warn("[Pulsar] 未发现有Consumer");
        }
    }
}
