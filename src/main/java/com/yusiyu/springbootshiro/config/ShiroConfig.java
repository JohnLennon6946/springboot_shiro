package com.yusiyu.springbootshiro.config;

import com.yusiyu.springbootshiro.service.UserService;
import com.yusiyu.springbootshiro.shiro.cache.RedisCacheManager;
import com.yusiyu.springbootshiro.shiro.realms.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration     //用来整合shiro框架的配置类
public class ShiroConfig {
    //创建shiroFilter,负责拦截所有请求
    @Bean
    public ShiroFilterFactoryBean getshiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //配置系统受限资源
        //配置系统公共资源
        Map<String,String> map=new HashMap<String, String>();
        map.put("/user/login","anon");   //anon表示把login设置为公共资源
        map.put("/user/register","anon");    //放行register请求
        map.put("/register.jsp","anon");    //放行register.jsp页面
        map.put("/user/role","anon");
        map.put("/**","authc");    //请求除了login外所有资源需要认证和授权

        shiroFilterFactoryBean.setLoginUrl("/login.jsp");   //设置默认认证界面路径,不设置默认是login.jsp
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
    //创建shiroFilter所需要的安全管理器
    @Bean
    public DefaultWebSecurityManager getdefaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
        //给安全管理器设置realm
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }
    //创建自定义realm,自定义realm
    @Bean
    public Realm getRealm(){
        CustomerRealm customerRealm = new CustomerRealm();
        //设置hashed凭证匹配器，md5加密，1024次哈希散列
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置md5加密
        credentialsMatcher.setHashAlgorithmName("md5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(credentialsMatcher);
        //开启缓存管理器
        customerRealm.setCachingEnabled(true);
        customerRealm.setAuthorizationCachingEnabled(true);
        customerRealm.setAuthorizationCachingEnabled(true);
        customerRealm.setCacheManager(new RedisCacheManager());
        return customerRealm;
    }
}
