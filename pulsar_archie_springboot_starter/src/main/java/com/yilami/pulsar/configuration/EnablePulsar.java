package com.yilami.pulsar.configuration;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 开启pulsar自动配置，包括Producer和Consumer
 * @author yilami
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Component
@Import({com.yilami.pulsar.configuration.PulsarAutoConfiguration.class})
public @interface EnablePulsar {
}
