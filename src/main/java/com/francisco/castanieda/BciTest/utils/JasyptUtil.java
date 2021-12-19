package com.francisco.castanieda.BciTest.utils;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;


public class JasyptUtil {


        public static String encyptPwd(String password,String value){
            PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
            encryptor.setConfig(cryptor(password));
            String result = encryptor.encrypt(value);
            return result;
        }


        public static String decyptPwd(String password,String value){
            PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
            encryptor.setConfig(cryptor(password));
            encryptor.decrypt(value);
            String result = encryptor.decrypt(value);
            return result;
        }

        public static SimpleStringPBEConfig cryptor(String password){
            SimpleStringPBEConfig config = new SimpleStringPBEConfig();
            config.setPassword(password);
            config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
            config.setKeyObtentionIterations("1000");
            config.setPoolSize(1);
            config.setProviderName("SunJCE");
            config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
            config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
            config.setStringOutputType("base64");
            return config;
        }
}
