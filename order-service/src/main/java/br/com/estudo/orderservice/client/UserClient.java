package br.com.estudo.orderservice.client;


import br.com.estudo.domain.user.model.dto.TransactionRequestDto;
import br.com.estudo.domain.user.model.dto.TransactionResponseDto;
import br.com.estudo.domain.user.model.dto.UserDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserClient {


    @Value("${services.user.url}")
    private String url;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }


    public Mono<UserDto> getUserById(Integer id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    public Mono<TransactionResponseDto> getUserTransaction(Integer id) {
        return webClient.get()
                .uri("/transaction/{id}", id)
                .retrieve()
                .bodyToMono(TransactionResponseDto.class);
    }

    public Mono<TransactionResponseDto> authorizeTransaction(TransactionRequestDto request) {
        return webClient.post()
                .uri("/transaction")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TransactionResponseDto.class);
    }

    public Flux<UserDto> getAllUsers() {
        return webClient.get()
                .uri("")
                .retrieve()
                .bodyToFlux(UserDto.class);
    }
}
