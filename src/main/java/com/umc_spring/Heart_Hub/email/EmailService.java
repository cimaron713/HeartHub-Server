package com.umc_spring.Heart_Hub.email;

public interface EmailService {
    String sendVerificationCode(String to) throws Exception;
    void sendUsername(String to, String username) throws Exception;
    String sendTemporaryPasswd(String to) throws Exception;

}