package com.alibou.app.config;

import com.alibou.app.security.KeyUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.PrivateKey;
import java.security.PublicKey;

@Configuration
public class JwtKeyConfig {

    @Bean("jwtPrivateKey")
    @Cacheable("jwt-keys") // Simpler - no explicit key needed
    public PrivateKey jwtPrivateKey() throws Exception {
        System.out.println("Loading private key..."); // Will only print once
        return KeyUtils.loadPrivateKey("keys/local-only/private_key.pem");
    }

    @Bean("jwtPublicKey")
    @Cacheable("jwt-keys")
    public PublicKey jwtPublicKey() throws Exception {
        System.out.println("Loading public key..."); // Will only print once
        return KeyUtils.loadPublicKey("keys/local-only/public_key.pem");
    }
}
