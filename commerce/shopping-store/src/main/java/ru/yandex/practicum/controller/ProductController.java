package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shoppingStore.dto.ProductDto;
import ru.yandex.practicum.shoppingStore.dto.SetProductQuantityStateDto;
import ru.yandex.practicum.exception.ErrorResponse;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping-store")
public class ProductController {

    private ProductService productService;

    @PostMapping
    private ResponseEntity<Object> createProduct(@RequestBody ProductDto productDto) {
        try {
            productService.createProduct(productDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Произошла ошибка при создании товара", e.getMessage()));
        }
    }

    @GetMapping("/{productId}")
    private ResponseEntity<Object> ProductIdInformation(@PathVariable Long productId) {
        try {
            if (productId == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Товар не найден", "Товар с ID " + productId + " не существует"));
            }
            Optional<Product> product = productService.getProductId(productId);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Произошла ошибка", e.getMessage()));
        }
    }

    @GetMapping
    private ResponseEntity<List<ProductDto>> getProductByCategory(@RequestParam("category") String category, Pageable pageable) {
        List<ProductDto> productDtos = productService.productsByCategory(category, pageable);
        return ResponseEntity.ok(productDtos);
    }

    @PutMapping
    private ResponseEntity<Object> updateProduct(@RequestBody ProductDto productDto) {
        try {
            productService.updateProduct(productDto);
            return ResponseEntity.status(HttpStatus.OK).body(productDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Произошла ошибка при обнавление продуктов", e.getMessage()));
        }
    }

    @PostMapping("/removeProductFromStore")
    private ResponseEntity<Object> deleteProductFromStore(@RequestParam("productId") Long productId) {
        try {
            productService.deleteProductFromStore(productId);
            return ResponseEntity.status(HttpStatus.OK).body(productId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Произошла ошибка при удаление продуктов", e.getMessage()));
        }
    }

    @PostMapping("/quantityState")
    private ResponseEntity<Object> addProducrQuantityState(@RequestBody SetProductQuantityStateDto setProductQuantityStateDto) {
        try {
            ProductDto updateProduct = productService.updateQuantityState(setProductQuantityStateDto);
            return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Произошла ошибка при обновлении статуса количества товара", e.getMessage()));
        }
    }
}
