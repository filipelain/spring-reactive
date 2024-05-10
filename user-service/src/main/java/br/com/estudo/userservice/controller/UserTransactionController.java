package br.com.estudo.userservice.controller;


import br.com.estudo.domain.user.model.dto.TransactionRequestDto;
import br.com.estudo.domain.user.model.dto.TransactionResponseDto;
import br.com.estudo.userservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
@RequiredArgsConstructor
public class UserTransactionController {
    private final TransactionService userTransactionService;


    @PostMapping
    public Mono<TransactionResponseDto> create(@RequestBody Mono<TransactionRequestDto> transactionRequestDtoMono) {
        return transactionRequestDtoMono.flatMap(userTransactionService::create);
    }

    @GetMapping("/{userId}")
    public Flux<TransactionResponseDto> findUserAll(@PathVariable Integer userId) {
       return userTransactionService.findUserAll(userId);
    }
}
