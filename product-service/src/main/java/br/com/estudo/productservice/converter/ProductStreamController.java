package br.com.estudo.productservice.converter;


import br.com.estudo.domain.product.model.dto.ProductDto;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductStreamController {
    private final Flux<ProductDto> productBroadcast;

    @GetMapping(value = "stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> productStream(
            @RequestParam("maxValue") BigDecimal maxValue,
            @RequestParam("minValue") BigDecimal minValue) {
        return productBroadcast
                .filter(p -> Optional.ofNullable(minValue).map(v -> p.getPrice().compareTo(v) >= 0).orElse(true))
                .filter(p -> Optional.ofNullable(maxValue).map(v -> p.getPrice().compareTo(v) <= 0).orElse(true));
    }
}
