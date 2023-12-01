package com.example.ms_market.configs;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@ConfigurationProperties(prefix = "permit-all-requests")
@Component
@Getter
@Slf4j
public class PermitAllRequestsConfig {

    private final List<String> getList = new ArrayList<>();
    private final List<String> postList = new ArrayList<>();

    public boolean checkPermitAllRequest(String uri, HttpMethod method) {
        log.info("isPermitAllRequest uri : {}, method: {}", uri, method);
        Predicate<String> checkGet = get_uri -> uri.startsWith(get_uri) && method.equals(HttpMethod.GET);
        Predicate<String> checkPost = post_uri -> uri.startsWith(post_uri) && method.equals(HttpMethod.POST);
        log.info("checkPermit: {}", getList.stream().anyMatch(checkGet) || postList.stream().anyMatch(checkPost));
        return getList.stream().anyMatch(checkGet) || postList.stream().anyMatch(checkPost);
    }

    public String[] getPermitAllAntMatchers() {
        List<String> permitAllAntMatchers = new ArrayList<>();
        getList.forEach(uri -> {
            permitAllAntMatchers.add(uri + "/**");
            permitAllAntMatchers.add(uri);
        });
        postList.forEach(uri -> {
            permitAllAntMatchers.add(uri + "/**");
            permitAllAntMatchers.add(uri);
        });
        return permitAllAntMatchers.toArray(new String[0]);
    }
}
