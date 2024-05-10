package br.com.estudo.domain.order.model.dto;

import br.com.estudo.domain.product.model.dto.ProductDto;
import br.com.estudo.domain.user.model.dto.TransactionRequestDto;
import br.com.estudo.domain.user.model.dto.TransactionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestContext {

    private PurchaseOrderRequestDto purchaseOrderRequestDto;
    private ProductDto productDto;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;

    public RequestContext(PurchaseOrderRequestDto purchaseOrderRequestDto) {
        this.purchaseOrderRequestDto = purchaseOrderRequestDto;
    }

}
