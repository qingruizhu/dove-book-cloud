package com.dove.book.provider.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
//扫描->@FeignClient
@EnableFeignClients(basePackages = {"com.dove.book.api.sso"})
public class BookProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookProviderApplication.class, args);
    }

}
