package br.com.estudo.productservice.service;

import static br.com.estudo.productservice.converter.ProductConverter.partialUpdate;

import br.com.estudo.productservice.converter.ProductConverter;
import br.com.estudo.domain.product.model.dto.ProductDto;
import br.com.estudo.productservice.repository.ProductRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks.Many;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final Many<ProductDto> sink;


    public Flux<ProductDto> findAll() {
        return productRepository
                .findAll()
                .map(ProductConverter::toProductDto);
    }

    public Mono<ProductDto> findById(String id) {
        return productRepository
                .findById(id)
                .map(ProductConverter::toProductDto);
    }

    public Mono<ProductDto> create(Mono<ProductDto> productDto) {
        return productDto
                .flatMap(this::save)
                .doOnNext(sink::tryEmitNext);
    }

    public Mono<ProductDto> update(String id, Mono<ProductDto> productDtoMono) {
        return findById(id)
                .flatMap(p -> productDtoMono.doOnNext(e -> e.setId(id)))
                .flatMap(this::save);
    }


    public Mono<Void> delete(String id) {
        return productRepository.findById(id)
                .flatMap(productRepository::delete);
    }


    private Mono<ProductDto> save(ProductDto productDto) {
        return productRepository
                .save(ProductConverter.toProductEntity(productDto))
                .map(ProductConverter::toProductDto);
    }

    public Mono<ProductDto> updatePartial(String id, Mono<ProductDto> productDtoMono) {
        return productDtoMono.flatMap(productDto -> findById(id)
                .map(p -> partialUpdate(p, productDto))
                .flatMap(this::save));

    }

    public Flux<ProductDto> findByPriceRange(BigDecimal min, BigDecimal max) {
        return productRepository.findByPriceBetween(Range.closed(min, max))
                .map(ProductConverter::toProductDto);
    }
}
