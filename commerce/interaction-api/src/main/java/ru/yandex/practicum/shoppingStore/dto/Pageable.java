package ru.yandex.practicum.shoppingStore.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Pageable {
    private Integer page;
    private Integer size;
    private String sort;
}
