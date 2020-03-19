package com.flygopher;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CipherEncryptionTest {

    // EncryptionBootstrapConfiguration
    // EncryptionAutoConfiguration
    @Test
    public void should_encrypt_and_decrypt() throws Exception {
        String text = "password";

        TextEncryptor encryptor = Encryptors.text("123456", "deadbeef");
        String encryptedText = encryptor.encrypt(text);

        log.info("encrypted : {}", encryptedText);

        String decryptText = encryptor.decrypt(encryptedText);

        log.info("decrypted : {}", decryptText);


        assertThat(decryptText).isEqualTo(text);

    }
}