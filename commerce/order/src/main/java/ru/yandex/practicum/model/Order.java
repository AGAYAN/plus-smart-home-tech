package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.payment.Dto.OrderDto;
import ru.yandex.practicum.payment.enums.OrderState;

import java.math.BigDecimal;
import java.util.Map;


@Getter
@Setter
@Table(name = "orders")
@Entity
@RequiredArgsConstructor
public class Order extends OrderDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String orderId;
    private String shoppingCartId;
    @ElementCollection
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Long, Integer> products;
    private String paymentId;
    private String deliveryId;
    private OrderState state;
    private double deliveryWeight;
    private double deliveryVolume;
    private boolean fragile;
    private BigDecimal totalPrice;
    private BigDecimal deliveryPrice;
    private BigDecimal productPrice;
}
