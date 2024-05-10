package br.com.estudo.orderservice.service;


import br.com.estudo.domain.order.model.dto.PurchaseOrderRequestDto;
import br.com.estudo.domain.order.model.dto.PurchaseOrderResponseDto;
import br.com.estudo.domain.order.model.dto.RequestContext;
import br.com.estudo.orderservice.client.ProductClient;
import br.com.estudo.orderservice.client.UserClient;
import br.com.estudo.orderservice.converter.Converter;
import br.com.estudo.orderservice.entity.PurchaseOrderEntity;
import br.com.estudo.orderservice.repository.PurchaseOrderRepository;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderFulfillmentService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductClient productClient;
    private final UserClient userClient;


    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono) {
        return requestDtoMono.map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(Converter::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(Converter::getPurchaseOrder)
                .map(this::save)
                .subscribeOn(Schedulers.boundedElastic());

    }

    private Mono<RequestContext> productRequestResponse(RequestContext requestContext) {
        return productClient.getProductById(requestContext.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(product -> {
                    log.info("Product: {}", product);
                    requestContext.setProductDto(product);
                })
                .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(100)))
                .thenReturn(requestContext);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext requestContext) {
        return userClient.authorizeTransaction(requestContext.getTransactionRequestDto())
                .doOnNext(requestContext::setTransactionResponseDto)
                .thenReturn(requestContext);
    }

    private PurchaseOrderResponseDto save(PurchaseOrderEntity orderEntity) {
        return Optional.of(purchaseOrderRepository.save(orderEntity))
                .map(Converter::toPurchaseOrderResponseDto)
                .orElseGet(PurchaseOrderResponseDto::new);
    }

}
