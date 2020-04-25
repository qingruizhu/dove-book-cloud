package com.dove.common.shiro.configuration;

import com.dove.common.shiro.credential.JWTCredentialsMatcher;
import com.dove.common.shiro.filter.JwtAuthenFilter;
import com.dove.common.shiro.filter.PermissionAuthorFilter;
import com.dove.common.shiro.filter.RolesAuthorFilter;
import com.dove.common.shiro.realm.DbShiroRealm;
import com.dove.common.shiro.realm.JwtShiroRealm;
import com.dove.common.shiro.util.JwtTokenUtil;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.Arrays;
import java.util.Map;


/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }

    /**
     * 注册过滤器
     */
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() throws Exception {
        FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<Filter>();
        filterRegistration.setFilter((Filter) shiroFilter().getObject());
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setAsyncSupported(true);
        filterRegistration.setEnabled(true);
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
        return filterRegistration;
    }

    /**
     * 设置过滤器
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //添加filter
        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("authcToken", new JwtAuthenFilter(jwtTokenUtil()));
        filterMap.put("anyRole", new RolesAuthorFilter());
        filterMap.put("anyPermission", new PermissionAuthorFilter());
        factoryBean.setFilters(filterMap);
        //设置 SecurityManager
        factoryBean.setSecurityManager(securityManager());
        //添加认证规则
        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
        return factoryBean;
    }

    /**
     * 设置 SecurityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//        manager.setAuthenticator(authenticator());
        manager.setRealms(Arrays.asList(dbShiroRealm(), jwtShiroRealm()));
        manager.setAuthorizer(authorizer());
        manager.setSubjectDAO(subjectDAO());
        return manager;
    }

    @Bean
    public DbShiroRealm dbShiroRealm() {
        DbShiroRealm dbShiroRealm = new DbShiroRealm();
        return dbShiroRealm;
    }

    @Bean
    public JwtShiroRealm jwtShiroRealm() {
        JwtShiroRealm jwtShiroRealm = new JwtShiroRealm(
                new JWTCredentialsMatcher(jwtTokenUtil()));
        return jwtShiroRealm;
    }

    @Bean
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/login", "noSessionCreation,anon");//noSessionCreation的作用是用户在操作session时会抛异常
        chainDefinition.addPathDefinition("/logout", "noSessionCreation,authcToken[permissive]"); //做用户认证，permissive参数的作用是当token无效时也允许请求访问，不会返回鉴权未通过的错误
        chainDefinition.addPathDefinition("/image/**", "anon");
        chainDefinition.addPathDefinition("/admin/**", "noSessionCreation,authcToken,anyRole[admin,manager]"); //只允许admin或manager角色的用户访问
        chainDefinition.addPathDefinition("/article/list", "noSessionCreation,authcToken,anyRole,anyPermission");
//        chainDefinition.addPathDefinition("/article/*", "noSessionCreation,authcToken[permissive]");
        chainDefinition.addPathDefinition("/**", "noSessionCreation,authcToken");
        return chainDefinition;
    }

    //
//    @Bean
//    public Authenticator authenticator() {
//        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
//        authenticator.setRealms(Arrays.asList(new DbShiroRealm(userService), new JWTShiroRealm(userService, new JWTCredentialsMatcher(jwtTokenUtil()))));
////        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
//        return authenticator;
//    }
    @Bean
    public Authorizer authorizer() {
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setRealms(Arrays.asList(jwtShiroRealm()));
        return authorizer;
    }

    /**
     * 禁用 session
     */
    @Bean
    protected DefaultSubjectDAO subjectDAO() {
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        return subjectDAO;
    }


}
