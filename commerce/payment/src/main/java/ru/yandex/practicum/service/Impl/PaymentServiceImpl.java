package ru.yandex.practicum.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mapper.PaymentMapper;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.order.controller.OrderClient;
import ru.yandex.practicum.payment.Dto.OrderDto;
import ru.yandex.practicum.payment.Dto.PaymentDto;
import ru.yandex.practicum.repository.PaymentRepository;
import ru.yandex.practicum.service.PaymentService;
import ru.yandex.practicum.shoppingStore.controller.ShoppingStoreClient;
import ru.yandex.practicum.shoppingStore.dto.ProductDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private OrderClient orderClient;
    private ShoppingStoreClient shoppingStoreClient;

    @Override
    public PaymentDto createPayment(OrderDto orderDto) {
        Payment payment = new Payment();
        payment.setPaymentId(orderDto.getPaymentId().toString());
        payment.setTotalPayment(orderDto.getTotalPrice());
        payment.setDeliveryTotal(orderDto.getDeliveryPrice());
        payment.setFeeTotal(orderDto.getTotalPrice());
        paymentRepository.save(payment);
        return paymentMapper.PaymentDtoToPayment(payment);
    }

    @Override
    public BigDecimal calculateProductCost(OrderDto order) {
        if (order.getProducts() == null || order.getProducts().isEmpty()) {
            throw new NullPointerException("No products in order ");
        }
        BigDecimal productCost = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);

        for (Map.Entry<Long, Integer> entry : order.getProducts().entrySet()) {
            ProductDto product = getProductById(entry.getKey());

            BigDecimal productPrice = BigDecimal.valueOf(product.getPrice());
            BigDecimal quantity = BigDecimal.valueOf(entry.getValue());

            BigDecimal itemCost = productPrice.multiply(quantity)
                    .setScale(2, RoundingMode.HALF_UP);
            productCost = productCost.add(itemCost);
        }

        return productCost;
    }


    @Override
    public BigDecimal totalCost(OrderDto orderDto) {
        BigDecimal productPrice = orderDto.getProductPrice();
        BigDecimal deliveryPrice = orderDto.getDeliveryPrice();
        BigDecimal tax = productPrice.multiply(BigDecimal.valueOf(0.1));
        BigDecimal totalCost = productPrice.add(tax).add(deliveryPrice);

        return totalCost;
    }

    @Override
    public void paymendRefund(String paymentId) {
        Optional<Payment> optionalDelivery = paymentRepository.findById(paymentId);
        if (optionalDelivery.isEmpty()) {
            throw new RuntimeException("Не сушествует такого id");
        }

        Payment payment = optionalDelivery.get();
        orderClient.paymentRefund(payment.getOrderId());
        paymentRepository.save(payment);
    }

    @Override
    public void paymendFailed(String paymentId) {
        Optional<Payment> optionalDelivery = paymentRepository.findById(paymentId);
        if (optionalDelivery.isEmpty()) {
            throw new RuntimeException("Не сушествует такого id");
        }

        Payment payment = optionalDelivery.get();
        orderClient.deliveryOrderFailed(payment.getOrderId());
        paymentRepository.save(payment);
    }

    public ProductDto getProductById(Long productId) {
        ResponseEntity<Object> response = shoppingStoreClient.ProductIdInformation(productId);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Object responseBody = response.getBody();

            if (responseBody instanceof ProductDto) {
                return (ProductDto) responseBody;
            } else {
                throw new IllegalStateException("Unexpected response type for product: " + responseBody.getClass());
            }
        } else {
            throw new NullPointerException("Product with ID " + productId + " not found");
        }
    }

}
