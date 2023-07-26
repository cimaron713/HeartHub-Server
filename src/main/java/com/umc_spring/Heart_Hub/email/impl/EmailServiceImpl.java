package com.umc_spring.Heart_Hub.email.impl;


import com.umc_spring.Heart_Hub.email.EmailService;
import com.umc_spring.Heart_Hub.user.service.impl.UserServiceImpl;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    JavaMailSender emailSender;
    public static final String code = createKey();

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 4; i++) {
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }

    public MimeMessage createVerificationMessage(String to) throws Exception{
        LOGGER.info("[createMessage] 보내는 대상 {}, 인증 번호 {}", to, code);
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("이메일 인증 코드 입니다.");
        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 이메일 진증 코드 입니다.. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 복사해 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>테스트.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>이메일 인증 코드 입니다</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= code+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("jinuktest208@gmail.com","test"));
        return message;
    }

    public MimeMessage createUsernameMessage(String to, String username)throws Exception{
        LOGGER.info("[createMessage] 보내는 대상 {}, 아이디 {}", to, username);
        MimeMessage  message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("아이디 입니다.");
        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 아이디 입니다.. </h1>";
        msgg+= "<br>";
        msgg+= "<br>";
        msgg+= "<p>테스트.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>아이디 입니다</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= username+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("jinuktest208@gmail.com","test"));
        return message;
    }

    public MimeMessage createTemporaryPasswdMessage(String to) throws Exception{
        LOGGER.info("[createMessage] 보내는 대상 {}, 인증 번호 {}", to, code);
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("임시 비밀번호 입니다.");
        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 임시 비밀번호 입니다.. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 복사해 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>테스트.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>임시 비밀번호 입니다</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= code+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("jinuktest208@gmail.com","test"));
        return message;
    }

    public MimeMessage createWarningMessage(String to) throws Exception {
        LOGGER.info("[createMessage] 보내는 대상 {}", to);
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("[경고 알림] HeartHub 서비스 이용이 제한될 수 있습니다.");
        String msgg="";
        msgg+= "<h1> 비매너의 사유로 HeartHub 서비스 이용이 제한될 수 있습니다. 게시글 작성 시 감정을 상하게 하는 말은 삼가해주시길 바랍니다.</h1>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress("jinuktest208@gmail.com","test"));
        return message;
    }

    public MimeMessage createContentDelMessage(String to) throws Exception {
        LOGGER.info("[createMessage] 보내는 대상 {}", to);
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("[콘텐츠 삭제 알림] HeartHub에 작성하신 콘텐츠가 삭제됩니다. ");
        String msgg="";
        msgg+= "<h1> 반복적인 비방/저격 사유로 HeartHub에 작성하신 콘텐츠들은 모두 삭제됩니다. 게시글 작성 시 감정을 상하게 하는 말은 삼가해주시길 바랍니다.</h1>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress("jinuktest208@gmail.com","test"));
        return message;
    }

    public MimeMessage createSuspendMessage(String to) throws Exception {
        LOGGER.info("[createMessage] 보내는 대상 {}", to);
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("[이용 중지 알림] HeartHub 서비스 이용이 중지됩니다 ");
        String msgg="";
        msgg+= "<h1> 지속적인 비매너 사유로 HeartHub 서비스 이용이 중지됩니다. 귀하의 계정은 금일을 기준으로 7일 이후부터 서비스 이용이 가능합니다.</h1>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress("jinuktest208@gmail.com","test"));
        return message;
    }

    @Override
    public String sendVerificationCode(String to)throws Exception {
        MimeMessage message = createVerificationMessage(to);
        try{
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return code;
    }
    @Override
    public void sendUsername(String to, String username) throws Exception{
        MimeMessage message = createUsernameMessage(to, username);
        try{
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
    @Override
    public String sendTemporaryPasswd(String to) throws Exception{
        MimeMessage message = createTemporaryPasswdMessage(to);
        try{
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return code;
    }

    @Override
    public void sendWarningMessage(String to) throws Exception {
        MimeMessage message = createWarningMessage(to);
        try{
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void sendContentDelMessage(String to) throws Exception {
        MimeMessage message = createContentDelMessage(to);
        try{
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void sendSuspendMessage(String to) throws Exception {
        MimeMessage message = createSuspendMessage(to);
        try{
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
}