package ru.yandex.practicum.mapper;

import ru.yandex.practicum.shoppingStore.dto.ProductDto;
import ru.yandex.practicum.model.Product;

public class ProductMapper {
    public Product convertToEntity(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setImageSrc(productDto.getImageSrc());
        product.setQuantityState(productDto.getQuantityState());
        product.setProductState(productDto.getProductState());
        product.setRating(productDto.getRating());
        product.setProductCategory(productDto.getProductCategory());
        product.setPrice(productDto.getPrice());
        return product;
    }

    public ProductDto convertToDto(Product product) {
        if (product == null) {
            return null;
        }
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getProductId());
        productDto.setProductName(product.getProductName());
        productDto.setDescription(product.getDescription());
        productDto.setImageSrc(product.getImageSrc());
        productDto.setQuantityState((product.getQuantityState()));
        productDto.setProductState((product.getProductState()));
        productDto.setRating(product.getRating());
        productDto.setProductCategory(product.getProductCategory());
        productDto.setPrice(product.getPrice());
        return productDto;
    }
}
