package com.dove.book.provider.book.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis配置类
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.dove.book.bgd.mapper", "com.dove.book.provider.book.dao"})
public class MyBatisConfig {

}
