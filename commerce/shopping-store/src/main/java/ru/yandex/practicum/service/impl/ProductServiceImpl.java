package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shoppingStore.dto.ProductDto;
import ru.yandex.practicum.shoppingStore.dto.SetProductQuantityStateDto;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.repository.ProductRepository;
import ru.yandex.practicum.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private ProductMapper productMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.convertToEntity(productDto);

        Product crateProductMapper = productRepository.save(product);

        return productMapper.convertToDto(crateProductMapper);
    }

    @Override
    public Optional<Product> getProductId(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<ProductDto> productsByCategory(String category, Pageable pageable) {
        Page<Product> productPage = productRepository.findByProductCategory(category, pageable);

        List<ProductDto> productDtos = productPage.stream()
                .map(product -> productMapper.convertToDto(product))
                .collect(Collectors.toList());

        return productDtos;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Optional<Product> existingProduct = productRepository.findByProductId(productDto.getProductId());

        if (!existingProduct.isPresent()) {
            throw new RuntimeException("Нету такого товара в таким id");
        }

        Product product = existingProduct.get();
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setImageSrc(productDto.getImageSrc());
        product.setQuantityState((productDto.getQuantityState()));
        product.setProductState((productDto.getProductState()));
        product.setRating(productDto.getRating());
        product.setProductCategory(productDto.getProductCategory());
        product.setPrice(productDto.getPrice());

        Product productSave = productRepository.save(product);

        return productMapper.convertToDto(productSave);
    }

    @Override
    public Optional<Product> deleteProductFromStore(Long productId) {
        Optional<Product> existingProduct = productRepository.findByProductId(productId);

        if (!existingProduct.isPresent()) {
            throw new RuntimeException("Нету такого товара в таким id");
        }

        productRepository.deleteById(productId);

        return existingProduct;
    }

    @Override
    public ProductDto updateQuantityState(SetProductQuantityStateDto setProductQuantityStateDto) {
        Optional<Product> existingProduct = productRepository.findByProductId(setProductQuantityStateDto.getProductId());

        if (!existingProduct.isPresent()) {
            throw new RuntimeException("Нету такого товара в таким id");
        }

        Product product = existingProduct.get();
        product.setQuantityState(setProductQuantityStateDto.getQuantityState());

        return productMapper.convertToDto(product);
    }
}
