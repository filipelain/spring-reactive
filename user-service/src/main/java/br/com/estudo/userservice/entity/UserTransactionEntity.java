package br.com.estudo.userservice.entity;

import br.com.estudo.domain.user.model.enums.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("users_transaction")
public class UserTransactionEntity {

    private Integer id;
    @Column("user_id")
    private Integer userId;
    private BigDecimal amount;
    @Column("transaction_status")
    private TransactionStatus status;
    @Column("transaction_date")
    private LocalDateTime createdAt;
}
