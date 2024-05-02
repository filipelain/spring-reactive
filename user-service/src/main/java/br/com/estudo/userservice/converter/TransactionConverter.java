package br.com.estudo.userservice.converter;

import br.com.estudo.userservice.dto.TransactionRequestDto;
import br.com.estudo.userservice.dto.TransactionResponseDto;
import br.com.estudo.userservice.entity.UserTransactionEntity;
import java.time.LocalDateTime;

public class TransactionConverter {


    public static UserTransactionEntity toEntity(TransactionRequestDto transactionDto) {
        var transaction = new UserTransactionEntity();
        transaction.setUserId(transaction.getUserId());
        transaction.setAmount(transaction.getAmount());
        transaction.setCreatedAt(LocalDateTime.now());
        return transaction;
    }

    public static TransactionResponseDto toResponseDto(UserTransactionEntity transaction) {
        return new TransactionResponseDto(transaction.getUserId(), transaction.getAmount(), transaction.getStatus());
    }

}
