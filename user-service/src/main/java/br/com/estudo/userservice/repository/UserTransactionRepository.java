package br.com.estudo.userservice.repository;

import br.com.estudo.userservice.entity.UserTransactionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransactionEntity, Integer> {

    Flux<UserTransactionEntity> findByUserId(Integer userId);


}
