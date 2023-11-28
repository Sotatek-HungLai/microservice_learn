package com.example.ms_product_service.configs.configs;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Value("${cloudinary.url}")
    private String CLOUDINARY_URL;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Cloudinary getCloudinary() {
        return new Cloudinary(CLOUDINARY_URL);
    }
}


