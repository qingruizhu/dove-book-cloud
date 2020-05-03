package com.dove.book.sso.api.config;

import com.dove.book.sso.api.UserShiroService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis配置类
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.dove.book.bgd.mapper", "com.dove.book.sso.api.dao"})
public class MyBatisConfig {
    @Bean
    public UserShiroService userShiroService() {
        return new UserShiroService();
    }

}
