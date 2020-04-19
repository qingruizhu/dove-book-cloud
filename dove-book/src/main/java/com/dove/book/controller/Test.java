package com.dove.book.controller;

import com.dove.common.base.vo.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description: Test
 * @Auther: qingruizhu
 * @Date: 2020/4/8 11:10
 */
@RestController
@RequestMapping("/test")
public class Test {

    @RequestMapping("/hello")
    public CommonResult hello() {
        CommonResult<People> pp = CommonResult.success(new People("祝清瑞", 22));
        return pp;
    }
}
