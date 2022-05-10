/*
 * @(#) ConsoleApplication.java 2020-11-14
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author senior
 */
@SpringBootApplication
@EnableScheduling
public class ConsoleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class, args);
    }
}
