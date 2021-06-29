package com.mail;

import com.yoa.entity.TEmployee;
import com.yoa.uitl.RabbitConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class MailReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    MailProperties mailProperties;
    @Autowired
    TemplateEngine templateEngine;

    //监听mail.welcome消息
    @RabbitListener(queues = RabbitConstant.MAIL_QUEUE_NAME)
    public void handle(TEmployee tEmployee){
        //创建邮件会话
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            //设置邮件内容
            //发件人，在配置文件获取
            helper.setFrom(mailProperties.getUsername());
            //收件人
            helper.setTo(tEmployee.getEmail());
            //邮件主题
            helper.setSubject("测试邮件");
            //发件日期
            helper.setSentDate(new Date());
            //将员工信息设置到template模板
            Context context = new Context();
            context.setVariable("name",tEmployee.getName());
            context.setVariable("jobNumber",tEmployee.getJobNumber());
            context.setVariable("position",tEmployee.getPosition());
            context.setVariable("department",tEmployee.getDepartment());
            String mail = templateEngine.process("mail", context);
            //设置邮件内容
            helper.setText(mail,true);
            //发送
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("邮件发送失败==========>",e.getMessage());
        }
    }

}
