package com.qiang.common.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.common.config
 * @Description: 错误页面定制
 * @Date: 2019/9/8 0008 14:56
 **/
@Configuration
public class ErrorConfig {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
        return factory -> {
            ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND,
                    "/404");
            ErrorPage errorPage403 = new ErrorPage(HttpStatus.FORBIDDEN,
                    "/403");
            ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,
                    "/500");
            factory.addErrorPages(errorPage403, errorPage404,
                    errorPage500);
        };
    }
}
