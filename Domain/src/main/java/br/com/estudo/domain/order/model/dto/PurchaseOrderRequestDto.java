package br.com.estudo.domain.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderRequestDto {

    private Integer userId;
    private String productId;
}
