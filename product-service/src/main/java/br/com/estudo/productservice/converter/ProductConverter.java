package br.com.estudo.productservice.converter;

import br.com.estudo.productservice.dto.ProductDto;
import br.com.estudo.productservice.entity.ProductEntity;
import java.util.Optional;

public class ProductConverter {

    public static ProductDto toProductDto(ProductEntity productEntity) {
        // BeanUtils.copyProperties(productEntity, ProductDto.class); good to save time not having to write the same code over and over again
        return new ProductDto(
                productEntity.getId(),
                productEntity.getDescription(),
                productEntity.getPrice()
        );
    }

    public static ProductEntity toProductEntity(ProductDto productDto) {
        return new ProductEntity(
                productDto.getId(),
                productDto.getDescription(),
                productDto.getPrice()
        );
    }

    public static ProductDto partialUpdate(ProductDto productBased, ProductDto productUpdate) {
        Optional.ofNullable(productUpdate.getDescription()).ifPresent(productBased::setDescription);
        Optional.ofNullable(productUpdate.getPrice()).ifPresent(productBased::setPrice);
        return productBased;
    }
}
