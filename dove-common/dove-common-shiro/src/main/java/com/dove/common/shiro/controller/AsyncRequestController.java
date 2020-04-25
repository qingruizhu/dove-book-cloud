package com.dove.common.shiro.controller;

import com.dove.common.shiro.dto.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncRequestController {

    @GetMapping("/async")
    @Async
    public User doAsync() {
        String name = Thread.currentThread().getName();
        System.out.println(name);
//            Thread.sleep(5000);
        return (User) SecurityUtils.getSubject().getPrincipal();

    }
}
