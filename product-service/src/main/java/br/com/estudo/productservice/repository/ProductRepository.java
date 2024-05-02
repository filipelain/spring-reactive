package br.com.estudo.productservice.repository;

import br.com.estudo.productservice.entity.ProductEntity;
import java.math.BigDecimal;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<ProductEntity, String> {
    Flux<ProductEntity> findByPriceBetween(Range<BigDecimal> range);
}