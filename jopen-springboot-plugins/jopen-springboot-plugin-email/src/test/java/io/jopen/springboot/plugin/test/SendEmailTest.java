package io.jopen.springboot.plugin.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author maxuefeng
 * @since 2020/2/4
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@EnableAutoConfiguration
public class SendEmailTest {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void testSend(){
        send();
    }

    private void send() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("kaiyuanshishen@163.com");
        mailMessage.setTo("2446011668@qq.com");
        mailMessage.setSubject("测试邮件发送");
        mailMessage.setText("测试text");

        javaMailSender.send(mailMessage);
    }
}
