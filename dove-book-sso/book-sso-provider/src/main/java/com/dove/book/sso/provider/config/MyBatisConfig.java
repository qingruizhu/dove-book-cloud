package com.dove.book.sso.provider.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 */
@Configuration
//@EnableTransactionManagement
@MapperScan({/*"com.dove.book.bgd.mapper", */"com.dove.book.sso.provider.dao"})
public class MyBatisConfig {

}
