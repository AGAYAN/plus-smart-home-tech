package ru.yandex.practicum.shoppingStore.dto;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.shoppingStore.enums.QuantityState;

@Getter
@Setter
public class SetProductQuantityStateDto {
    private Long productId;
    private QuantityState quantityState;

}
