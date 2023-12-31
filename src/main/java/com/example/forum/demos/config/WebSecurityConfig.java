package com.example.forum.demos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author zhouhaoran
 * @date 2023/12/31
 * @project forum
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 如果您使用的是REST API，通常需要禁用CSRF保护
                .authorizeRequests()
                .antMatchers("/api/users/login", "/api/users/register").permitAll() // 允许所有用户访问登录和注册
                .anyRequest().authenticated() // 其他所有请求都需要认证
                .and()
                .formLogin() // 启用表单登录
                .and()
                .httpBasic(); // 启用HTTP Basic认证
    }
}
