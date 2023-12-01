package com.example.ms_market.services;

import com.example.ms_market.dtos.ProductDTO;
import com.example.ms_market.dtos.UpdateProductAmountDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final RestTemplate restTemplate;
    private final String PRODUCT_SERVICE_API = "http://localhost:9900/api/product";

    public ProductDTO getProduct(Long id, String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String GET_PRODUCT_API = PRODUCT_SERVICE_API + "/" + id;
        ResponseEntity<ProductDTO> response = restTemplate.exchange(GET_PRODUCT_API, HttpMethod.GET, entity, ProductDTO.class);
        return response.getBody();
    }

}
