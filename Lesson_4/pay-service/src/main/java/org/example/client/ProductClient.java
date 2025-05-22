package org.example.client;

import lombok.RequiredArgsConstructor;
import org.example.dto.ProductDto;
import org.example.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public ProductDto getProductById(Long productId) {
        try {
            return restTemplate.getForObject(
                    productServiceUrl + "/products/" + productId,
                    ProductDto.class
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new ProductNotFoundException("Product not found");
        } catch (HttpClientErrorException e) {
            throw new RuntimeException(String.valueOf(e.getStatusCode().value()));
        }
    }




    public List<ProductDto> getProductsByUserId(Long userId) {
        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                productServiceUrl + "/products/by-user/" + userId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }
}
