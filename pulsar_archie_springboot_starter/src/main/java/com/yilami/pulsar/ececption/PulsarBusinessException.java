package com.yilami.pulsar.ececption;

/**
 * 自定义Pulsar异常
 * @author yilami
 **/
public class PulsarBusinessException extends RuntimeException{

    public PulsarBusinessException(String msg){
        super(msg);
    }

    public PulsarBusinessException(String msg,Throwable e){
        super(msg,e);
    }
}
