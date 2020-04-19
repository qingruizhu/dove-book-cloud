package com.dove.common.base.config;

import com.dove.common.base.intercepter.CommonResultIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /*@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //  MappingJackson2HttpMessageConverter 提到 StringHttpMessageConverter 之前，解决全局响应时遇到 Controller 中返回值为 String 类型的方法时的转换异常
        converters.add(0,new MappingJackson2HttpMessageConverter());
    }*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截统一处理 @ResponseResult
        registry.addInterceptor(new CommonResultIntercepter()).addPathPatterns("/**");
    }
}