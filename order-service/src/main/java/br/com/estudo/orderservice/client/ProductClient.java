package br.com.estudo.orderservice.client;

import br.com.estudo.domain.product.model.dto.ProductDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {

    @Value("${services.product.url}")
    private String url; //http://localhost:8091/product

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<ProductDto> getProductById(String id) {
        return webClient.get()
                .uri(path -> path.path("/{id}").build(id))
                .retrieve()
                .bodyToMono(ProductDto.class)
                .log("URL");
    }

    public Flux<ProductDto> getAllProducts() {
        return webClient.get()
                .uri("")
                .retrieve()
                .bodyToFlux(ProductDto.class);
    }


}
