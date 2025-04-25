package com.plathanus.sales.job;

import com.plathanus.sales.entity.Cart;
import com.plathanus.sales.entity.Vehicle;
import com.plathanus.sales.repository.CartRepository;
import com.plathanus.sales.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartCleanupScheduler {

    private final CartRepository cartRepository;
    private final VehicleRepository vehicleRepository;

    @Scheduled(fixedRate = 60_000) /* 1 minuto */
    public void cleanExpiredCarts() {
        List<Cart> expiredCarts = cartRepository.findAll().stream()
                .filter(Cart::isExpired)
                .toList();

        for (Cart cart : expiredCarts) {
            Vehicle vehicle = cart.getVehicle();
            vehicle.setAvailable(true);
            vehicleRepository.save(vehicle);
            cartRepository.delete(cart);
            log.info("[SCHEDULER] Carrinho expirado removido: {}", cart.getId());
        }
    }

}

