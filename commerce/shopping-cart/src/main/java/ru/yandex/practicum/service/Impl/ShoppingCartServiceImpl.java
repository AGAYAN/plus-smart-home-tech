package ru.yandex.practicum.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mapper.CartMapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.repository.ProductRepository;
import ru.yandex.practicum.repository.ShoppingCartRepository;
import ru.yandex.practicum.service.ShoppingCartService;
import ru.yandex.practicum.shoppingCart.dto.BookedProductsDto;
import ru.yandex.practicum.shoppingCart.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    @Override
    public ShoppingCartDto addProductToCart(String username, Map<String, Integer> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Список товаров не может быть пустым.");
        }

        shoppingCartRepository.deleteByUsername(username);

        Map<Long, Integer> addedProducts = new HashMap<>();

        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            Long productId = Long.parseLong(entry.getKey());
            int quantity = entry.getValue();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Товар с ID " + productId + " не найден."));

            if (quantity <= 0) {
                throw new IllegalArgumentException("Количество товара должно быть больше 0 для: " + product.getProductName());
            }

            ShoppingCart cartItem = new ShoppingCart();
            cartItem.setUsername(username);
            cartItem.setProducts((Map<Long, Integer>) product);
            shoppingCartRepository.save(cartItem);

            addedProducts.put(productId, quantity);
        }

        return new ShoppingCartDto("Товары успешно добавлены в корзину.", addedProducts);

    }

    @Override
    public ShoppingCartDto getShoppingCart(String username) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUsernameAndActive(username, true)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUsername(username);
                    newCart.setActive(true);
                    newCart.setProducts(new HashMap<>());
                    return shoppingCartRepository.save(newCart);
                });

        return cartMapper.toShoppingCartDto(shoppingCart);
    }

    public void deactivateShoppingCart(String username) {
        shoppingCartRepository.deleteByUsername(username);
    }

    @Override
    public ShoppingCart updateCart(String username, Map<String, Integer> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Список товаров не может быть пустым.");
        }

        ShoppingCart cart = shoppingCartRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Корзина пользователя не найдена."));

        Map<Long, Integer> product = cart.getProducts();
        if (product == null) {
            throw new IllegalArgumentException("Продукт в корзине отсутствует или не входит в список продуктов.");
        }

        shoppingCartRepository.delete(cart);

        return cart;
    }

    @Override
    public ShoppingCartDto changeProductQuantity(String username, ChangeProductQuantityRequest request) {
        ShoppingCart cart = shoppingCartRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Корзина пользователя не найдена"));

        // Проверяем, что корзина активна
        cart = getActiveShoppingCart(username);

        Long productId = request.getProductId();
        int newQuantity = request.getNewQuantity();

        if (!cart.getProducts().containsKey(productId)) {
            throw new IllegalArgumentException("Товар с ID " + productId + " отсутствует в корзине");
        }

        if (newQuantity < 0) {
            throw new IllegalArgumentException("Количество товара не может быть отрицательным");
        }

        if (newQuantity == 0) {
            cart.getProducts().remove(productId);
        } else {
            cart.getProducts().put(productId, newQuantity);
        }

        shoppingCartRepository.save(cart);

        ShoppingCartDto shoppingCartDto = cartMapper.toShoppingCartDto(cart);

        return shoppingCartDto;
    }
    @Override
    public BookedProductsDto reserveItems(String username) {

        BookedProductsDto bookingResponse = new BookedProductsDto();
        bookingResponse.setDeliveryWeight(10.5);
        bookingResponse.setDeliveryVolume(5.2);
        bookingResponse.setFragile(true);

        return bookingResponse;
    }

    private ShoppingCart getActiveShoppingCart(String username) {
        return shoppingCartRepository.findByUsernameAndActive(username, true)
                .orElseThrow(() -> new RuntimeException("Корзина не найдена для пользователя: " + username));
    }


}
