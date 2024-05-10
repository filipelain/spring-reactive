package br.com.estudo.orderservice;

import br.com.estudo.domain.order.model.dto.PurchaseOrderRequestDto;
import br.com.estudo.domain.product.model.dto.ProductDto;
import br.com.estudo.domain.user.model.dto.UserDto;
import br.com.estudo.orderservice.client.ProductClient;
import br.com.estudo.orderservice.client.UserClient;
import br.com.estudo.orderservice.service.OrderFulfillmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServiceApplicationTests {

    @Autowired
    private UserClient userClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Test
    void contextLoads() {

        var flux = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
                .map(t -> createOrderRequestDto(t.getT1(), t.getT2()))
                .flatMap(dto -> orderFulfillmentService.processOrder(Mono.just(dto)))
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(3)
                .verifyComplete();
    }

    private PurchaseOrderRequestDto createOrderRequestDto(UserDto userDto, ProductDto productDto) {
        return new PurchaseOrderRequestDto(
                userDto.getId(),
                productDto.getId()
        );
    }

}
