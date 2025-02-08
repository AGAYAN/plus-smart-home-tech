package ru.yandex.practicum.order.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private String country;
    private String city;
    private String street;
    private String house;
    private String flat;
}
