package ru.yandex.practicum.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.Dimension;
import ru.yandex.practicum.order.Dto.ProductReturnRequest;
import ru.yandex.practicum.warehouse.dto.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.dto.AddressDto;
import ru.yandex.practicum.mapper.WarehouseMapper;
import ru.yandex.practicum.shoppingCart.dto.BookedProductsDto;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;
import ru.yandex.practicum.model.WarehouseProduct;
import ru.yandex.practicum.repository.WarehouseProductRepository;
import ru.yandex.practicum.service.WarehouseService;
import ru.yandex.practicum.warehouse.dto.NewProductInWarehouseDto;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private static final String[] ADDRESSES = {"ADDRESS_1", "ADDRESS_2"};
    private static final String CURRENT_ADDRESS = ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];

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
            warehouseProduct.setQuantity(warehouseProduct.getQuantity() - products.get(warehouseProduct.getId()));
            warehouseRepository.save(warehouseProduct);
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

    @Override
    public AddressDto addAddress() {
        AddressDto address = new AddressDto();
        address.setCountry(CURRENT_ADDRESS);
        address.setCity(CURRENT_ADDRESS);
        address.setStreet(CURRENT_ADDRESS);
        address.setHouse(CURRENT_ADDRESS);
        address.setFlat(CURRENT_ADDRESS);
        return address;
    }

    @Override
    public void increaseProductQuantity(AddProductToWarehouseRequest request) {
        WarehouseProduct warehouseProduct = warehouseRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Нету такого продукта"));

        warehouseProduct.setQuantity(Math.toIntExact(warehouseProduct.getQuantity() + request.getQuantity()));
        warehouseRepository.save(warehouseProduct);
    }

    @Override
    public BookedProductsDto addProducts(ProductReturnRequest request) {
        Map<Long, Integer> products = request.getProducts();
        List<WarehouseProduct> warehouseProducts = warehouseRepository.findAllById(products.keySet());

        warehouseProducts.forEach(warehouseProduct -> {
            warehouseProduct.setQuantity(warehouseProduct.getQuantity() + products.get(warehouseProduct.getId()));
            warehouseRepository.save(warehouseProduct);
        });

        double weight = warehouseProducts.stream()
                .mapToDouble(WarehouseProduct::getWeight)
                .sum();

        double volume = warehouseProducts.stream()
                .mapToDouble(warehouseProduct -> warehouseProduct.getDimension().getWidth()
                        * warehouseProduct.getDimension().getHeight()
                        * warehouseProduct.getDimension().getDepth())
                .sum();

        boolean fragile = warehouseProducts.stream().anyMatch(WarehouseProduct::isFragile);

        return new BookedProductsDto(weight, volume, fragile);

    }


}
