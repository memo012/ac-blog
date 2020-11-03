package com.qiang.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qiang
 * @Description: durid数据源配置
 * @Date: 2019/6/27 0027 17:24
 */

@Configuration
public class DruidConfig {

    @Value("${qiang.druid.user}")
    private String user;

    @Value("${qiang.druid.password}")
    private String password;

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }


    // 配置druid的监控
    // 1. 配置一个管理后台的servlet
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();
        // 添加控制台管理用户
        initParams.put("loginUsername", user);
        initParams.put("loginPassword", password);
        // 添加IP白名单, 默认就是允许所有访问, 127.0.0.1
        initParams.put("allow", "");
        // 添加IP黑名单，当白名单和黑名单重复时，黑名单优先级更高
        initParams.put("deny", "192.168.203.8");
        // 是否能够重置数据
        initParams.put("resetEnable", "false");
        bean.setInitParameters(initParams);
        return bean;
    }


    // 2. 配置一个web监控filter
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*css,/druid/*");
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }

}
