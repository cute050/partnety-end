package com.jobv;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PartnetyApplicationTests {

    @Test
    void testEncrypt() {

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword("zy0501");
        encryptor.setConfig(config);
        String plainText = "yyyppp0502";
        String encryptedText = encryptor.encrypt(plainText);
        System.out.println(encryptedText);
    }


    @Test
    public void testDe() throws Exception {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();

        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword("zy0501");
        standardPBEStringEncryptor.setConfig(config);
        String encryptedText = "pO1Qxw9kiYgQ1X+dJFRszXkQNKXOhGCA";
        String plainText = standardPBEStringEncryptor.decrypt(encryptedText);
        System.out.println(plainText);
    }

    //
    //

}
