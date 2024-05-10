package br.com.estudo.domain.user.model.dto;

import br.com.estudo.domain.user.model.enums.TransactionStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {


    private Integer userId;
    private BigDecimal amount;
    private TransactionStatus status;
}
