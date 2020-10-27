package com.ssm.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//加密
public class BCryptPasswordEncoderUtils {
    private static BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
    public static String encodePassword(String password){
        return bCryptPasswordEncoder.encode(password);

    }

    public static void main(String[] args) {
        String password = BCryptPasswordEncoderUtils.encodePassword("123");
        System.out.println(password);
    }
}
