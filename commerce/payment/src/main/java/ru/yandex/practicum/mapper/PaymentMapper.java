package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.payment.Dto.PaymentDto;

@Mapper
public interface PaymentMapper {
    PaymentDto PaymentDtoToPayment(Payment payment);

}
