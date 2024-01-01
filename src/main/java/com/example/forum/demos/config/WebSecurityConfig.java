package com.example.forum.demos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

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
                .csrf().disable() // 如果你正在构建REST API，通常你会禁用CSRF保护
                .authorizeRequests()
                .antMatchers("/api/users/login", "/api/users/register").permitAll() // 允许所有用户访问登录和注册
                .anyRequest().authenticated() // 其他所有请求都需要认证
                .and()
                .formLogin().disable() // 禁用表单登录
                .httpBasic().disable(); // 禁用HTTP Basic认证
    }
}
