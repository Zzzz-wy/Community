package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTest {


    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailClient mailClient;

    @Test
    public void testTextMail() {
        mailClient.sendEmail("664845716@qq.com", "test", "welcome!!!!!");
    }

    @Test
    public void testHtmlMail() {
        Context context = new Context();
        //将要传给模版的变量存到这个对象就行
        context.setVariable("username", "sunday");

        //模版路径、数据
        String content = templateEngine.process("mail/demo", context);

        System.out.println(content);

        mailClient.sendEmail("664845716@qq.com", "Html", content);

    }

}
