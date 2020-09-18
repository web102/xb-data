package com.bobandata.iot.basedb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author: 李志鹏
 * @Date: 2019/11/28 13:37
 * @Desc:
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("**","/login**","/phone/food/**", "static/index.html","favicon.ico","/static/**");
    }
}
