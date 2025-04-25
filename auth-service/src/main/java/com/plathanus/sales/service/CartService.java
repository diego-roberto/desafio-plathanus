package com.plathanus.sales.service;

import com.plathanus.sales.dto.CartDTO;
import com.plathanus.sales.dto.TokenPayloadDTO;
import com.plathanus.sales.dto.UserDTO;
import com.plathanus.sales.entity.Cart;
import com.plathanus.sales.entity.Sale;
import com.plathanus.sales.entity.Vehicle;
import com.plathanus.sales.enums.SaleType;
import com.plathanus.sales.repository.CartRepository;
import com.plathanus.sales.repository.SaleRepository;
import com.plathanus.sales.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.plathanus.sales.util.PriceCalculatorUtil.calcularPreco;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final VehicleRepository vehicleRepository;
    private final SaleRepository saleRepository;
    private final UserService userService;

    public CartDTO getCart(String token) {
        TokenPayloadDTO payload = userService.getTokenPayload(token);
        if (payload == null || payload.id() == null) {
            throw new IllegalStateException("Não foi possível identificar o usuário.");
        }

        UUID clientId = payload.id();

        Cart cart = cartRepository.findByClientId(clientId)
                .orElseThrow(() -> new NoSuchElementException("Carrinho não encontrado"));

        return new CartDTO(cart.getId(), cart.getVehicle().getId(), cart.getCreatedAt());
    }

    @Transactional
    public CartDTO addToCart(String token, UUID vehicleId) {
        TokenPayloadDTO payload = userService.getTokenPayload(token);
        if (payload == null || payload.id() == null) {
            throw new IllegalStateException("Não foi possível identificar o usuário.");
        }

        UUID clientId = payload.id();

        if (!payload.roles().contains("ROLE_CLIENT")) {
            throw new IllegalStateException("Apenas clientes podem adicionar ao carrinho.");
        }

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NoSuchElementException("Veículo não encontrado"));

        if (!vehicle.isAvailable()) {
            throw new IllegalStateException("Veículo indisponível");
        }

        vehicle.setAvailable(false);
        vehicleRepository.save(vehicle);

        Cart cart = Cart.builder()
                .vehicle(vehicle)
                .clientId(clientId)
                .createdAt(LocalDateTime.now())
                .build();

        cartRepository.findByClientId(clientId).ifPresent(cartRepository::delete);

        cartRepository.save(cart);
        return new CartDTO(cart.getId(), cart.getVehicle().getId(), cart.getCreatedAt());
    }

    @Transactional
    public void cancel(String token) {
        TokenPayloadDTO payload = userService.getTokenPayload(token);
        if (payload == null || payload.id() == null) {
            throw new IllegalStateException("Não foi possível identificar o usuário.");
        }

        UUID clientId = payload.id();

        Cart cart = cartRepository.findByClientId(clientId)
                .orElseThrow(() -> new NoSuchElementException("Carrinho não encontrado"));

        Vehicle vehicle = cart.getVehicle();
        vehicle.setAvailable(true);

        vehicleRepository.save(vehicle);
        cartRepository.delete(cart);
    }

    @Transactional
    public void checkout(String token) {
        TokenPayloadDTO payload = userService.getTokenPayload(token);

        if (payload == null || payload.id() == null) {
            throw new IllegalStateException("Não foi possível identificar o usuário");
        }

        if (payload.roles().contains("ROLE_VENDOR")) {
            throw new IllegalStateException("Apenas clientes podem realizar compras online");
        }

        UUID clientId = payload.id();

        Cart cart = cartRepository.findByClientId(clientId)
                .orElseThrow(() -> new NoSuchElementException("Carrinho não encontrado"));

        if (cart.isExpired()) {
            cartRepository.delete(cart);
            throw new IllegalStateException("Tempo do carrinho expirado.");
        }

        Vehicle vehicle = cart.getVehicle();

        UserDTO client = userService.findUserById(clientId, token)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado"));

        BigDecimal finalPrice = calcularPreco(vehicle.getBasePrice(), vehicle.getColor(), client);

        Sale sale = Sale.builder()
                .type(SaleType.ONLINE)
                .clientId(client.id())
                .sellerId(null)
                .vehicle(vehicle)
                .date(LocalDateTime.now())
                .finalPrice(finalPrice)
                .build();

        vehicle.setAvailable(false);

        saleRepository.save(sale);
        vehicleRepository.save(vehicle);
        cartRepository.delete(cart);
    }

}


