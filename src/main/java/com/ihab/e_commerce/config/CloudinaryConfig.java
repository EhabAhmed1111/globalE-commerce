package com.ihab.e_commerce.config;


import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    private String cloudName;
    private String cloudApiKey;
    private String cloudApiSecret;

    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("cloud_key", cloudApiKey);
        config.put("cloud_secret", cloudApiSecret);

        return new Cloudinary(config);
    }

}
