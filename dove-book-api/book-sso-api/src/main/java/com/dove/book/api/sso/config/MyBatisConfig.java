package com.dove.book.api.sso.config;

import com.dove.book.api.sso.service.UserShiroService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 */
@Configuration
//@EnableTransactionManagement
@MapperScan({"com.dove.book.bgd.mapper", "com.dove.book.api.sso.dao"})
@ComponentScan("com.dove.book.api.sso.hystrix")
public class MyBatisConfig {
    @Bean
    public UserShiroService userShiroService() {
        return new UserShiroService();
    }

}


