package br.com.estudo.productservice.config;


import br.com.estudo.domain.product.model.dto.ProductDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

@Configuration
public class SinkConfig {

    @Bean
    public Many<ProductDto> sink() {
        return Sinks.many()
                .replay()
                .limit(1);
    }

    @Bean
    public Flux<ProductDto> productBroadcast(Many<ProductDto> sink) {
        return sink.asFlux();
    }


}
