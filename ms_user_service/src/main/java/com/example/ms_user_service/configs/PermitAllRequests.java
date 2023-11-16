package com.example.ms_user_service.configs;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "permit-all-requests")
@Component
@Getter
public class PermitAllRequests {

    private List<String> GetList = new ArrayList<>();
    private List<String> PostList = new ArrayList<>();

}
