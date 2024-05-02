package br.com.estudo.userservice.entity;

import br.com.estudo.userservice.dto.TransactionStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("user_transaction")
public class UserTransactionEntity {

    private Integer id;
    private Integer userId;
    private Integer amount;
    private TransactionStatus status;
    private LocalDateTime createdAt;
}
