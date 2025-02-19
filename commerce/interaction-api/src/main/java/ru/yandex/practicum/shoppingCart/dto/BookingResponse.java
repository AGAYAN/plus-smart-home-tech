package ru.yandex.practicum.shoppingCart.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BookingResponse {
    private double deliveryWeight;
    private double deliveryVolume;
    private boolean fragile;

    public BookingResponse(double totalWeight, double totalVolume, boolean hasFragile) {
        this.deliveryVolume = totalVolume;
        this.deliveryWeight = totalWeight;
        this.fragile = hasFragile;
    }
}
