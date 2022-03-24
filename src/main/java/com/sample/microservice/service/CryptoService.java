package com.sample.microservice.service;

import com.sample.microservice.client.MicroserviceService;
import com.sample.microservice.exception.MicroserviceException;
import com.sample.microservice.exception.MicroserviceServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

@Service
@Slf4j
public class CryptoService {

    public static final String CIPHER_TYPE = "AES/GCM/NoPadding";
    public static final String MESSAGE_DIGEST_INSTANCE_TYPE = "SHA-512";

    public CryptoService() {
    }

    @Value("${service.crypto.key:REPLACE_THIS_WITH_A_REAL_ENCRYPTION_KEY}")
    private String cryptoKey;

    @Value("${service.crypto.expiration:300}")
    private int expiration;

    private SecretKeySpec secretKey;

    public synchronized void createSecretKeySpec(String muKey) throws MicroserviceServiceException {
        MessageDigest sha = null;
        try {
            byte[] key = muKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance(MESSAGE_DIGEST_INSTANCE_TYPE);
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            throw new MicroserviceServiceException("someMessage");
        }
    }

    public String encrypt(String strToEncrypt) throws MicroserviceServiceException {

        createSecretKeySpec(cryptoKey);
        try {
            //Do some magic encryption wizardry
            final Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedText = cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8));
            byte[] iv = cipher.getIV();
            byte[] message = new byte[12 + strToEncrypt.getBytes(StandardCharsets.UTF_8).length + 16];
            System.arraycopy(iv, 0, message, 0, 12);
            System.arraycopy(encryptedText, 0, message, 12, encryptedText.length);

            //Encode encrypted String to Base64 so we can send as a json param
            return new String(Base64.getEncoder().encode(message), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new MicroserviceServiceException("someMessage");
        }
    }

    public String decrypt(String encryptedString) throws MicroserviceServiceException {

        createSecretKeySpec(cryptoKey);
        try {
            //Decode from base64 and convert to Byte Array
            byte[] encryptedText = Base64.getDecoder().decode(encryptedString.getBytes(StandardCharsets.UTF_8));

            //Do some magic decryption wizardry
            final Cipher AES_cipherInstance = Cipher.getInstance(CIPHER_TYPE);
            GCMParameterSpec params = new GCMParameterSpec(128, encryptedText, 0, 12);
            AES_cipherInstance.init(Cipher.DECRYPT_MODE, secretKey, params);
            byte[] decyptedText = AES_cipherInstance.doFinal(encryptedText, 12, encryptedText.length - 12);
            return new String(decyptedText, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new MicroserviceServiceException("someMessage");
        }
    }
}
