package br.com.estudo.orderservice.controller;


import br.com.estudo.domain.order.model.dto.PurchaseOrderRequestDto;
import br.com.estudo.domain.order.model.dto.PurchaseOrderResponseDto;
import br.com.estudo.domain.product.model.dto.ProductDto;
import br.com.estudo.domain.user.model.dto.UserDto;
import br.com.estudo.orderservice.client.ProductClient;
import br.com.estudo.orderservice.client.UserClient;
import br.com.estudo.orderservice.service.OrderFulfillmentService;
import br.com.estudo.orderservice.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class PurchaseOrderController {
    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderQueryService orderQueryService;
    private final ProductClient productClient;
    private final UserClient userClient;

    @PostMapping
    public Mono<ResponseEntity<PurchaseOrderResponseDto>> order(@RequestBody Mono<PurchaseOrderRequestDto> requestDtoMono) {
        return orderFulfillmentService.processOrder(requestDtoMono)
                .map(ResponseEntity::ok)
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
                .onErrorReturn(WebClientRequestException.class, ResponseEntity.badRequest().build());

    }

    @GetMapping("user/{id}")
    public Flux<PurchaseOrderResponseDto> getOrderById(@PathVariable Integer id) {
        return orderQueryService.getOrderById(id);
    }

    @GetMapping("test/product/{id}")
    public Mono<ProductDto> testProduct (@PathVariable String id) {
        return productClient.getProductById(id);
    }
    @GetMapping("test/user/{id}")
    public Mono<UserDto> testClient (@PathVariable Integer id) {
        return userClient.getUserById(id);
    }

}
