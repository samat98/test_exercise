package com.test.testExe.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConf {

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
