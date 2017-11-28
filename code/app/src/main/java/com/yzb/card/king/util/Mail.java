package com.yzb.card.king.util;

import android.content.Context;

import com.yzb.card.king.sys.AppConstant;

import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {

    public static boolean sendMail(Context context, String sendTo, String title, MimeMultipart content) {

        // 配置发送邮件的环境属性
        final Properties props = new Properties();
        /*
         * 可用的属性：
         * mail.store.protocol
         * mail.transport.protocol
         * mail.host
         * mail.user
         * mail.from
         */
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", AppConstant.mail_smtp_host);
        // 发件人的账号
        props.put("mail.user", AppConstant.mail_user);
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", AppConstant.mail_password);

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        try {
            // 设置发件人
            InternetAddress form = new InternetAddress(
                    AppConstant.mail_user);

            message.setFrom(form);

            // 设置收件人
            InternetAddress to = new InternetAddress(sendTo);
            message.setRecipient(Message.RecipientType.TO, to);

            // 设置抄送
            //InternetAddress cc = new InternetAddress("");
            //message.setRecipient(RecipientType.CC, cc);

            // 设置密送，其他的收件人不能看到密送的邮件地址
            //InternetAddress bcc = new InternetAddress("aaaaa@163.com");
            //message.setRecipient(RecipientType.CC, bcc);

            // 设置邮件标题
            message.setSubject(title);

            // 设置邮件的内容体
            message.setContent(content, "text/html;charset=utf-8");

            MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
            mc.addMailcap("text/html;charset=utf-8; x-java-content-handler=com.sun.mail.handlers.text_html");
            mc.addMailcap("text/xml;charset=utf-8; x-java-content-handler=com.sun.mail.handlers.text_xml");
            mc.addMailcap("text/plain;charset=utf-8; x-java-content-handler=com.sun.mail.handlers.text_plain");
            mc.addMailcap("multipart/*;charset=utf-8; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            mc.addMailcap("message/rfc822;charset=utf-8; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
            CommandMap.setDefaultCommandMap(mc);

            // 发送邮件
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

    }

}
