package com.arnan.realization_of_knowledge.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    public static String encrypt(String oldPwd,String salt){
        String password = "";
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5Hex(salt + oldPwd + salt);
        }
        return password.toUpperCase();
    }
}
