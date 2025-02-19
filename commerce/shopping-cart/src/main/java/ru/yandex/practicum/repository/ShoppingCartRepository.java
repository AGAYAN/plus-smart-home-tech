package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findAllByUsername(String username);
    void deleteByUsername(String username);
    Optional<ShoppingCart> findByUsername(String username);
    Optional<ShoppingCart> findByUsernameAndActive(String username, boolean active);
}
