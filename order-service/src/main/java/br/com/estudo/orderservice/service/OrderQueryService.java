package br.com.estudo.orderservice.service;


import br.com.estudo.domain.order.model.dto.PurchaseOrderResponseDto;
import br.com.estudo.orderservice.converter.Converter;
import br.com.estudo.orderservice.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderQueryService {
    private final PurchaseOrderRepository purchaseOrderRepository;


    public Flux<PurchaseOrderResponseDto> getOrderById(Integer orderId) {
        return Flux.fromStream(() -> purchaseOrderRepository.findByUserId(orderId).stream())
                .map(Converter::toPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }
}
