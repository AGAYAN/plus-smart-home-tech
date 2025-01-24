package ru.yandex.practicum.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.NewProductInWarehouseDto;
import ru.yandex.practicum.mapper.WarehouseMapper;
import ru.yandex.practicum.shoppingCart.dto.BookedProductsDto;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;
import ru.yandex.practicum.model.WarehouseProduct;
import ru.yandex.practicum.repository.WarehouseProductRepository;
import ru.yandex.practicum.service.WarehouseService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private WarehouseMapper warehouseMapper;
    private final WarehouseProductRepository warehouseRepository;

    @Override
    public BookedProductsDto checkAvailableProducts(ShoppingCartDto shoppingCartDto) {
        Map<Long, Integer> products = shoppingCartDto.getProducts();
        List<WarehouseProduct> warehouseProducts = warehouseRepository.findAllById(products.keySet());
        warehouseProducts.forEach(warehouseProduct -> {
            if (warehouseProduct.getQuantity() < products.get(warehouseProduct.getQuantity())) {
                throw new NullPointerException("Не достаточно товара на складе");
            }
        });

        double deliveryWeight = warehouseProducts.stream()
                .map(WarehouseProduct::getWeight)
                .mapToDouble(Double::doubleValue)
                .sum();

        double deliveryVolume = warehouseProducts.stream()
                .map(warehouseProduct -> warehouseProduct.getDimension().getWidth()
                        * warehouseProduct.getDimension().getHeight() * warehouseProduct.getDimension().getWidth())
                .mapToDouble(Double::doubleValue)
                .sum();

        boolean fragile = warehouseProducts.stream()
                .anyMatch(WarehouseProduct::isFragile);

        BookedProductsDto bookedProductsDto = new BookedProductsDto();
        bookedProductsDto.setDeliveryWeight(deliveryWeight);
        bookedProductsDto.setDeliveryVolume(deliveryVolume);
        bookedProductsDto.setFragile(fragile);

        return bookedProductsDto;
    }

    @Override
    public void createProductToWarehouse(NewProductInWarehouseDto request) {
        Optional<WarehouseProduct> product =   warehouseRepository.findById(Long.valueOf(request.getProductId()));

        if (product.isPresent())
            throw new NullPointerException("Такой продукт уже есть в БД");
        warehouseRepository.save(warehouseMapper.toWarehouse(request));
    }
}
