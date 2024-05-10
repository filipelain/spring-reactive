package br.com.estudo.productservice.controller;

import br.com.estudo.domain.product.model.dto.ProductDto;
import br.com.estudo.productservice.service.ProductService;
import java.math.BigDecimal;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    public Flux<ProductDto> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> findOne(@PathVariable String id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public Mono<ResponseEntity<ProductDto>> create(@RequestBody Mono<ProductDto> productDto) {
        return productService.create(productDto)
                .map(p -> ResponseEntity
                        .created(URI.create("/product/" + p.getId()))
                        .body(p));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> update(@PathVariable String id, @RequestBody Mono<ProductDto> productDto) {
        return productService.update(id, productDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> updatePartial(@PathVariable String id, @RequestBody Mono<ProductDto> productDto) {
        return productService.updatePartial(id, productDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return productService.delete(id)
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/price-range")
    public Flux<ProductDto> findPriceRange(
            @RequestParam(value = "min", required = false) BigDecimal min,
            @RequestParam(value = "max", required = false) BigDecimal max) {
        return productService.findByPriceRange(min, max);
    }
}
