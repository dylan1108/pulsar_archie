package com.yilami.pulsar.properties;

/**
 * Pulsar配置类
 * @author yilami
 * @deprecated 支持多数据源注入
 **/
@Deprecated
public class PulsarProperties {

    /**
     * pulsar服务地址
     */
    private String serviceUrl;
    /**
     * 租户
     */
    private String tenant;
    /**
     * 命名空间
     */
    private String namespace;
    /**
     * 是否开启TCP不延迟
     */
    private Boolean enableTcpNoDelay=true;
    /**
     * 操作超时，单位秒
     */
    private Integer operationTimeout=30;
    /**
     * 消费者监听线程数
     */
    private Integer listenerThreads=1;
    /**
     * IO线程数
     */
    private Integer ioThreads=1;
}
