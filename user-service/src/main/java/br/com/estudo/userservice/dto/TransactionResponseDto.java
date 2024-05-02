package br.com.estudo.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {


    private Integer userId;
    private Integer amount;
    private TransactionStatus status;
}
