package br.com.estudo.domain.order.model.dto;

import br.com.estudo.domain.order.model.enums.OrderStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderResponseDto {

    private Integer orderId;
    private Integer userId;
    private String productId;
    private BigDecimal amount;
    private OrderStatus status;
}
