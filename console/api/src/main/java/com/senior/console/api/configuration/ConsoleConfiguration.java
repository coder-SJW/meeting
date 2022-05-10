/*
 * @(#) ConsoleConfiguration.java 2020-11-15
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author senior
 */
@Configuration
@ComponentScan({"com.senior"})
@MapperScan("com.senior.dao")
@EnableAspectJAutoProxy
public class ConsoleConfiguration {

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
                .addProperty("hibernate.validator.fail_fast", "false").buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
