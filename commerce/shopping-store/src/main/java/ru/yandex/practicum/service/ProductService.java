package ru.yandex.practicum.service;

import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.shoppingStore.dto.ProductDto;
import ru.yandex.practicum.shoppingStore.dto.SetProductQuantityStateDto;
import ru.yandex.practicum.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

    Optional<Product> getProductId(Long productId);

    List<ProductDto> productsByCategory(String category, Pageable pageable);

    ProductDto updateProduct(ProductDto productDto);

    Optional<Product> deleteProductFromStore(Long productId);

    ProductDto updateQuantityState(SetProductQuantityStateDto request);
}
