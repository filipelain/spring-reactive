package br.com.estudo.userservice.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("users")
public class UserEntity {

    @Id
    private Integer id;
    @Column("name")
    private String name;
    @Column("balance")
    private BigDecimal balance;
}
