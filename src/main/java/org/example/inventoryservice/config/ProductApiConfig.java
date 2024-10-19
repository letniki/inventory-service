package org.example.inventoryservice.config;

import lombok.RequiredArgsConstructor;
import org.example.inventoryservice.productapi.ApiClient;
import org.example.inventoryservice.productapi.client.ProductApi;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class ProductApiConfig {

    private final Supplier<String> tokenProvider;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .interceptors((request, body, execution) -> {
                    request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + tokenProvider.get());
                    return execution.execute(request, body);
                })
                .build();
    }
    @Bean
    public ApiClient productApiClient(){
        ApiClient apiClient = new ApiClient(restTemplate());
        apiClient.setBearerToken(tokenProvider);
        return apiClient;
    }
    @Bean
    public ProductApi productApi(){
        return new ProductApi(productApiClient());
    }
}
