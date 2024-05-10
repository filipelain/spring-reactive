package br.com.estudo.userservice.service;

import br.com.estudo.userservice.converter.TransactionConverter;
import br.com.estudo.domain.user.model.dto.TransactionRequestDto;
import br.com.estudo.domain.user.model.dto.TransactionResponseDto;
import br.com.estudo.domain.user.model.enums.TransactionStatus;
import br.com.estudo.userservice.entity.UserTransactionEntity;
import br.com.estudo.userservice.repository.UserRepository;
import br.com.estudo.userservice.repository.UserTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final UserTransactionRepository transactionRepository;
    private final UserRepository userRepository;


    public Mono<TransactionResponseDto> create(TransactionRequestDto requestDto) {
        return userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                .filter(Boolean::booleanValue)
                .map((b) -> TransactionConverter.toEntity(requestDto))
                .map(this::approve)
                .flatMap(this::save)
                .map(t -> applyStatus(t, TransactionStatus.APPROVED))
                .switchIfEmpty(this.emptyResponse(requestDto));

    }

    public Flux<TransactionResponseDto> findUserAll(Integer userId) {
       return transactionRepository.findByUserId(userId)
               .map(TransactionConverter::toResponseDto);
    }

    private UserTransactionEntity approve(UserTransactionEntity entity) {
        entity.setStatus(TransactionStatus.APPROVED);
        return entity;
    }
    private Mono<TransactionResponseDto> save(UserTransactionEntity entity) {
        return transactionRepository.save(entity)
                .map(TransactionConverter::toResponseDto);
    }

    private TransactionResponseDto applyStatus(TransactionResponseDto transactionResponseDto, TransactionStatus status) {
        transactionResponseDto.setStatus(status);
        return transactionResponseDto;
    }

    private Mono<TransactionResponseDto> emptyResponse(TransactionRequestDto transactionRequestDtoMono) {
        var response = TransactionConverter.toResponseDto(transactionRequestDtoMono);
        response.setStatus(TransactionStatus.DECLINED);
        return Mono.just(response);
    }
}
