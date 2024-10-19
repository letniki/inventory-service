package org.example.inventoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.inventoryservice.dto.ProductAvailability;
import org.example.inventoryservice.dto.ProductDto;
import org.example.inventoryservice.service.ProductService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
@Service
public class RestTemplateProductService implements ProductService {

    private final Supplier<String> tokenProvider;
    @Override
    public void updateProductAvailability(Long productId, ProductAvailability value) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + tokenProvider.get());
        ProductDto body = ProductDto.builder()
                .availability(value)
                .build();

        HttpEntity<ProductDto> httpEntity = new HttpEntity<>(body, httpHeaders);


        ResponseEntity<ProductDto> response = restTemplate
                .exchange(
                "http://localhost:8080/products/{productId}",
                HttpMethod.PUT,
                httpEntity,
                ProductDto.class,
                productId);

        log.info("Product availability updated. Status: {}. Body: {}", response.getStatusCode(), response.getBody());
    }
}
