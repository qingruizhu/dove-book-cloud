package com.dove.book.provider.book.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        //添加请求头参数设置
//        ParameterBuilder auth = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<Parameter>();
//        auth.name(JwtAuthenFilter.AUTHORIZATION_HEADER).description("用户token")
//                .modelRef(new ModelRef("string")).parameterType("header")
//                .required(false).build(); //header中的ticket参数非必填，传空也可以
//        pars.add(auth.build());    //根据每个方法名也知道当前方法在设置什么参数
//
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.dove.book.provider.book"))
                .paths(PathSelectors.any())
                .build()
//                .globalOperationParameters(pars)
                ;
    }

    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                //页面标题
//                .title("Spring Boot 测试使用 Swagger2 构建RESTful API")
                .title("API【Book】")
                //创建人
//                .contact(new Contact("qingruizhu", "http://www.baidu.com", "zqr99655@163.com"))
//                .contact(new Contact("qingruizhu", "https://mp.csdn.net/postedit/97941666", "zqr99655@163.com"))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }

}