package com.senior.console.api.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author sjw
 * @Description
 * @Date 11:15 2022/4/29
 **/
@Slf4j
@Service
public class MailUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送纯文本邮件
     * @param to            邮件接收方
     * @param subject       邮件主题
     * @param text          邮件内容
     */
    public void sendTextMail(String to,String subject,String text){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        try{
            javaMailSender.send(simpleMailMessage);
            logger.info("邮件已发送。");
        }catch (Exception e){
            logger.error("邮件发送失败。" + e.getMessage());
        }
    }
}
