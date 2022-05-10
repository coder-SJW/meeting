package com.senior.console.api.test.controller;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.senior.console.api.ConsoleApplication;
import com.senior.service.AccountService;
import com.senior.service.RoleService;

/**
 * @author senior
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsoleApplication.class)
public class SmokeTest {
    @Resource
    private AccountService accountService;
    @Resource
    private RoleService roleService;

    @Test
    public void testAll() {

    }
}
