package br.com.estudo.orderservice.converter;

import br.com.estudo.domain.order.model.dto.PurchaseOrderResponseDto;
import br.com.estudo.domain.order.model.dto.RequestContext;
import br.com.estudo.domain.order.model.enums.OrderStatus;
import br.com.estudo.domain.user.model.dto.TransactionRequestDto;
import br.com.estudo.domain.user.model.enums.TransactionStatus;
import br.com.estudo.orderservice.entity.PurchaseOrderEntity;
import java.util.Optional;

public class Converter {


    public static void setTransactionRequestDto(RequestContext requestContext) {
        TransactionRequestDto requestDto = new TransactionRequestDto();
        requestDto.setAmount(requestContext.getProductDto().getPrice());
        requestDto.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
        requestContext.setTransactionRequestDto(requestDto);
    }

    public static PurchaseOrderEntity getPurchaseOrder(RequestContext requestContext) {
        PurchaseOrderEntity purchaseOrderEntity = new PurchaseOrderEntity();
        purchaseOrderEntity.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
        purchaseOrderEntity.setProductId(requestContext.getPurchaseOrderRequestDto().getProductId());
        purchaseOrderEntity.setAmount(requestContext.getProductDto().getPrice());

        var status = TransactionStatus.APPROVED.equals(requestContext.getTransactionResponseDto().getStatus())
                ? OrderStatus.COMPLETE
                : OrderStatus.FAILED;
        purchaseOrderEntity.setStatus(status);

        return purchaseOrderEntity;
    }

    public static PurchaseOrderResponseDto toPurchaseOrderResponseDto(PurchaseOrderEntity purchaseOrderEntity) {
        return new PurchaseOrderResponseDto(
                purchaseOrderEntity.getId(),
                purchaseOrderEntity.getUserId(),
                purchaseOrderEntity.getProductId(),
                purchaseOrderEntity.getAmount(),
                purchaseOrderEntity.getStatus()
        );
    }
}
