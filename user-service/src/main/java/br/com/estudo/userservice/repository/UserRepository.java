package br.com.estudo.userservice.repository;

import br.com.estudo.userservice.entity.UserEntity;
import java.math.BigDecimal;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity, Integer> {

    //think twice before using this query
    @Modifying
    @Query("UPDATE user SET balance = balance + :amount WHERE id = :userId and balance + :amount >= 0")
    Mono<Boolean> updateUserBalance(Integer userId, BigDecimal amount);
}
