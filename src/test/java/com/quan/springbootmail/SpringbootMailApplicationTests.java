package com.quan.springbootmail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@SpringBootTest
class SpringbootMailApplicationTests {

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    TemplateEngine templateEngine;
    @Test
    void contextLoads() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("主题呀");
        simpleMailMessage.setText("邮件内容");
        simpleMailMessage.setFrom("879386150@qq.com");
        simpleMailMessage.setSentDate(new Date());
        simpleMailMessage.setTo("13232457767@163.com");
        javaMailSender.send(simpleMailMessage);
    }

    // 带附件的邮件
    @Test
    void test1() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // 配置邮件
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setSubject("主题呀");
        mimeMessageHelper.setText("邮件内容");
        mimeMessageHelper.setFrom("879386150@qq.com");
        mimeMessageHelper.setSentDate(new Date());
        mimeMessageHelper.setTo("13232457767@163.com");
        mimeMessageHelper.addAttachment("sll.mp4",new File("D:\\developer\\2020-02-10 14\\0542dbd9-69c9-4336-bcfa-5d96608deec6.mp4"));
        // 发送邮件
        javaMailSender.send(mimeMessage);
    }

    // 带图片的邮件
    @Test
    void test2() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // 配置邮件
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setSubject("主题呀");
        mimeMessageHelper.setText("邮件内容 <img src='cid:p01'/> ",true);
        mimeMessageHelper.setFrom("879386150@qq.com");
        mimeMessageHelper.setSentDate(new Date());
        mimeMessageHelper.setTo("13232457767@163.com");
        mimeMessageHelper.addInline("p01",new FileSystemResource(new File("E:\\wallpaper\\5a8dc1c0b5dcf.jpg")));
        // 发送邮件
        javaMailSender.send(mimeMessage);
    }
    //使用 tmymeleaf 做邮件模板
    @Test
    void test3() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // 配置邮件
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setSubject("主题呀");
        Context context = new Context();
        context.setVariable("username","王祖贤");
        context.setVariable("position","演员");
        context.setVariable("dep","开发部");
        context.setVariable("salary",3000);
        context.setVariable("joblevel","高级工程师");
        String process = templateEngine.process("mail.html",context);
        mimeMessageHelper.setText(process,true);
        mimeMessageHelper.setFrom("879386150@qq.com");
        mimeMessageHelper.setSentDate(new Date());
        mimeMessageHelper.setTo("13232457767@163.com");
        // 发送邮件
        javaMailSender.send(mimeMessage);
    }
}
