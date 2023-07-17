package com.umc_spring.Heart_Hub.email;

public interface EmailService {
    String sendVerificationCode(String to) throws Exception;
    void sendId(String to, String id) throws Exception;
    String sendTemporaryPasswd(String to) throws Exception;

}