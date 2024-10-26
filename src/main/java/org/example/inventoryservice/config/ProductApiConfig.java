package org.example.inventoryservice.config;

import lombok.RequiredArgsConstructor;
import org.example.inventoryservice.productapi.ApiClient;
import org.example.inventoryservice.productapi.client.ProductApi;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class ProductApiConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .interceptors((request, body, execution) -> {
                    request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + tokenProvider.getToken());
                    return execution.execute(request, body);
                })
                .build();
    }
    @Bean
    public ApiClient productApiClient(){
        ApiClient apiClient = new ApiClient(restTemplate());
        apiClient.setBearerToken(tokenProvider::getToken);
        return apiClient;
    }
    @Bean
    public ProductApi productApi(){
        return new ProductApi(productApiClient());
    }
}
