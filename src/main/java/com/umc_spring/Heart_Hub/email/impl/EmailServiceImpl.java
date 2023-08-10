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

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    JavaMailSender emailSender;
    public static final String code = createKey();
    public static final String password = createPassword();

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 4; i++) {
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }

    public static String createPassword() {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&' };

        StringBuffer key = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i=0; i<8; i++) {
            idx = sr.nextInt(len);
            key.append(charSet[idx]);
        }

        return key.toString();
    }
    public MimeMessage createVerificationMessage(String to) throws Exception{
        LOGGER.info("[createMessage] 보내는 대상 {}, 인증 번호 {}", to, code);
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("[HEART HUB] 인증번호 안내");
        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 안녕하세요.HEART HUB 입니다. 회원님의 가입을 환영합니다! </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래의 인증번호를 복사하신 후 이메일 인증번호 입력란에 입력해 주시기 바랍니다. </p>";
        msgg+= "<br>";
        msgg+= "<p>인증번호는 발송된 시점부터 ;<span style=\"color: rgb(0, 0, 255);\">5분간만</span>; 유효하니 확인 후 바로 입력해 주시기 바랍니다.</p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>이메일 인증 코드 입니다</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "인증번호 : <strong>";
        msgg+= code+"</strong><div><br/> ";
        msgg+= "<p> 감사합니다. </p>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("jinuktest208@gmail.com","HEARTHUB"));
        return message;
    }

    public MimeMessage createUsernameMessage(String to, String username)throws Exception{
        LOGGER.info("[createMessage] 보내는 대상 {}, 아이디 {}", to, username);
        MimeMessage  message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("[HEART HUB] 아이디 발급");
        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<p>귀하의 귀하의 아이디는 <strong>";
        msgg+= username + "</strong>입니다.</p>";
        msgg+= "<br>";
        msgg+= "<p>귀하께서 요청하신 내용이 아니라면 회원님의 소중한 계정 정보 보호를 위해 계정의 비밀번호를 &apos;즉시&apos; 변경해 주세요.</p>";
        msgg+= "<br>";
        msgg+= "<p>추가 문의가 필요하신 경우 haeun9492@hub.com으로 메일을 보내주세요. 감사합니다.</p>";
        msgg+= "<br>";
        msgg+= "<p>*이 메일은 발신전용이므로 회신되지 않습니다.</p>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("jinuktest208@gmail.com","HEARTHUB"));
        return message;
    }

    public MimeMessage createTemporaryPasswdMessage(String to) throws Exception{
        LOGGER.info("[createMessage] 보내는 대상 {}, 임시비밀번호 {}", to, password);
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("[HEART HUB] 임시 비밀번호 발급");
        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<p>귀하의 임시 비밀번호는 <strong>";
        msgg+= password + "</strong>입니다.  로그인 후 반드시 비밀번호를 변경해 주시길 바랍니다.</p>";
        msgg+= "<br>";
        msgg+= "<p>귀하께서 요청하신 내용이 아니라면 회원님의 소중한 계정 정보 보호를 위해 계정의 비밀번호를 &apos;즉시&apos; 변경해 주세요.</p>";
        msgg+= "<br>";
        msgg+= "<p>추가 문의가 필요하신 경우 haeun9492@hub.com으로 메일을 보내주세요. 감사합니다.</p>";
        msgg+= "<br>";
        msgg+= "<p>*이 메일은 발신전용이므로 회신되지 않습니다.</p>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("jinuktest208@gmail.com","HEARTHUB"));
        return message;
    }

    public MimeMessage createWarningMessage(String to) throws Exception {
        LOGGER.info("[createMessage] 보내는 대상 {}", to);
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("[HEART HUB] 귀하의 계정이 커뮤니티 규정 위반으로 신고되었습니다.");
        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 귀하의 계정이 비매너의 사유로 신고되었습니다.</h1>";
        msgg+= "<p>앞으로 귀하의 게시물이 2번의 신고를 더 받게 되면 커뮤니티에서 <strong>자동 삭제</strong>될 수 있음을 경고 드립니다.</p>";
        msgg+= "<p>또한 앞으로 4번의 신고를 더 받게 되면 귀하의 계정이 <strong>7일</strong>동안 정지될 수 있음을 경고 드립니다.</p>";
        msgg+= "<p>모든 <strong>HEART HUB</strong> 회원이 즐거운 커뮤니티를 만들기 위해 커뮤니티 규정을 잘 숙지해 주시길 바랍니다. 감사합니다.</p>";
        msgg += "<p>추가 문의가 필요하신 경우 haeun4778@hub.com으로 메일을 보내주세요. 감사합니다.</p>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress("jinuktest208@gmail.com","HEARTHUB"));
        return message;
    }

    public MimeMessage createContentDelMessage(String to) throws Exception {
        LOGGER.info("[createMessage] 보내는 대상 {}", to);
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("[HEART HUB] 귀하의 계정이 커뮤니티 규정 위반으로 신고되었습니다.");
        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 귀하의 계정이 비매너의 사유로 신고되었습니다.</h1>";
        msgg+= "<p>커뮤니티 가이드 정책에 따라 게시물이 '자동 삭제'되었습니다.</p>";
        msgg+= "<p>앞으로 귀하의 게시물이 2번의 신고를 더 받게 되면 계정이 '7일'동안 정지될 수 있음을 경고 드립니다.</p>";
        msgg+= "<p>모든 <strong>HEART HUB</strong> 회원이 즐거운 커뮤니티를 만들기 위해 커뮤니티 규정을 잘 숙지해 주시길 바랍니다. 감사합니다.</p>";
        msgg += "<p>추가 문의가 필요하신 경우 haeun4778@hub.com으로 메일을 보내주세요. 감사합니다.</p>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress("jinuktest208@gmail.com","HEARTHUB"));
        return message;
    }

    public MimeMessage createSuspendMessage(String to) throws Exception {
        LOGGER.info("[createMessage] 보내는 대상 {}", to);
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("[HEART HUB] 귀하의 계정이 커뮤니티 규정 위반으로 정지되었습니다.");
        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 귀하의 계정이 비매너의 사유로 신고되었습니다.</h1>";
        msgg+= "<p>신고가 5회 누적되어 커뮤니티 가이드대로 계정이 '7일'동안 정지되었음을 알려드립니다.</p>";
        msgg+= "<p>정지 기간 '7일'이 지나면 자동으로 계정 정지가 풀림을 알려드립니다.</p>";
        msgg+= "<p>모든 <strong>HEART HUB</strong> 회원이 즐거운 커뮤니티를 만들기 위해 커뮤니티 규정을 잘 숙지해 주시길 바랍니다. 감사합니다.</p>";
        msgg += "<p>추가 문의가 필요하신 경우 haeun4778@hub.com으로 메일을 보내주세요. 감사합니다.</p>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress("jinuktest208@gmail.com","HEARTHUB"));
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
        return password;
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