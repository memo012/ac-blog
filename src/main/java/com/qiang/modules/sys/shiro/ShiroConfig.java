package com.qiang.modules.sys.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Properties;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.shiro
 * @Description: shiro配置文件
 * @Date: 2019/6/20 0020 13:24
 **/
@Configuration
public class ShiroConfig {

    @Value("${qiang.shiro-redis.host}")
    private String shiroRedisHost;

    @Value("${qiang.shiro-redis.port}")
    private Integer shiroRedisPort;


    /**
     * 密码校验规则HashedCredentialsMatcher
     * 这个类是为了对密码进行编码的 ,
     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
     * 这个类也负责对form里输入的密码进行编码
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        credentialsMatcher.setHashIterations(1024);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }


    /**
     * 自定义realm
     * @return
     */
    @Bean("authRealm")
    public AuthRealm authRealm() {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return authRealm;
    }

    /**
     * 自定义sessionManager
     * @return
     */
    @Bean("sessionManager")
    public SessionManager sessionManager(){
        CustomSessionManager customSessionManager = new CustomSessionManager();
        // 超时时间 默认30分钟，会话超时，方法里面的是单位是毫秒
        customSessionManager.setGlobalSessionTimeout(60 * 1000 * 60 * 24);
        // 配置session持久化
        customSessionManager.setSessionDAO(redisSessionDAO());
        return customSessionManager;
    }


    @Bean("securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 如果前后端不分离，则注释掉即可
        manager.setSessionManager(sessionManager());
        // 设置自定义redisManager
        manager.setCacheManager(redisCacheManager());
        // 推荐放到最后
        manager.setRealm(authRealm());
        return manager;
    }

    /**
     * 设置redisManager
     * @return
     */
    public RedisManager getRedisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(shiroRedisHost);
        redisManager.setPort(shiroRedisPort);
        return redisManager;
    }

    /**
     * 配置具体cache实现类
     * @return
     */
    @Bean("redisCacheManager")
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(getRedisManager());
        // 设置过期时间，单位是秒
        redisCacheManager.setExpire(60 * 60 * 24);
        return redisCacheManager;
    }

    /**
     * 自定义session持久化
     * @return
     */
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(getRedisManager());
        // 设置sessionId生成器
        redisSessionDAO.setSessionIdGenerator(new CustomSessionIdGenerator());
        return redisSessionDAO;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        bean.setLoginUrl("login");
        bean.setUnauthorizedUrl("/");
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/", "anon");    // authc --    认证(登录)才能使用
        filterChainDefinitionMap.put("/user", "authc");
        filterChainDefinitionMap.put("/editor", "roles[admin]");
        filterChainDefinitionMap.put("/SuperAdmin", "roles[admin]");
        filterChainDefinitionMap.put("/druid/**", "anon");  // anon -- 匿名访问
        filterChainDefinitionMap.put("/**", "anon");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    /**
     * Shiro控制ThymeLeaf界面按钮级权限控制
     *
     * @return
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


    /**
     * 配置shiro跟spring的关联
     *
     * @param
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }


    /**
     * Spring的一个bean , 由Advisor决定对哪些类的方法进行AOP代理 .
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

}
