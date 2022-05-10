/*
 * @(#) SecurityConfiguration.java 2020-11-13
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.configuration;

import java.util.Collections;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.senior.console.api.security.AccountAuthenticationFilter;
import com.senior.console.api.security.AccountAuthenticationProvider;
import com.senior.console.api.security.SecurityConstants;
import com.senior.console.api.security.SecurityProblemSupport;

/**
 * @author senior
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AuthenticationSuccessHandler authenticationSuccessHandler = new ForwardAuthenticationSuccessHandler(
            SecurityConstants.ACCOUNT_CURRENT);

    @Resource
    private SecurityProblemSupport securityProblemSupport;
    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;
    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Resource
    private AccountAuthenticationProvider accountAuthenticationProvider;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources",
                        "/configuration/security", "/swagger-ui.html", "/webjars/**")
                .antMatchers("/", "/*.js", "/index.html", "/favicon.ico",
                        "/js/**", "/img/**", "/css/**", "/front-statics/**")
                .antMatchers("/api/account/register");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().headers()
                .frameOptions().sameOrigin()

                .and().exceptionHandling()
                .authenticationEntryPoint(securityProblemSupport)
                .accessDeniedHandler(securityProblemSupport)

                .and()
                .addFilterBefore(accountAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                .logout()
                .logoutUrl(SecurityConstants.LOGOUT_URL)
                .logoutSuccessHandler(logoutSuccessHandler)
                .invalidateHttpSession(true)

                .and().anonymous().disable()
                .rememberMe().disable()
                .authorizeRequests()
                .anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccountAuthenticationFilter accountAuthenticationFilter() {
        AccountAuthenticationFilter filter = new AccountAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setAuthenticationManager(
                new ProviderManager(Collections.singletonList(accountAuthenticationProvider)));
        return filter;
    }
}
