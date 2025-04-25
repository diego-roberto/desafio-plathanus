package com.plathanus.sales.service;

import com.plathanus.sales.dto.SaleDTO;
import com.plathanus.sales.dto.UserDTO;
import com.plathanus.sales.entity.Sale;
import com.plathanus.sales.entity.Vehicle;
import com.plathanus.sales.enums.SaleType;
import com.plathanus.sales.repository.SaleRepository;
import com.plathanus.sales.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static com.plathanus.sales.util.PriceCalculatorUtil.calcularPreco;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final UserService userService;
    private final VehicleRepository vehicleRepository;
    private final SaleRepository saleRepository;

    @Transactional
    public void storeSale(SaleDTO saleDTO, String token) {
        Vehicle vehicle = vehicleRepository.findById(saleDTO.vehicleId())
                .orElseThrow(() -> new NoSuchElementException("Veículo não encontrado"));

        if (!vehicle.isAvailable()) {
            throw new IllegalStateException("Veículo indisponível");
        }

        UserDTO client = userService.findUserById(saleDTO.clientId(), token)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado"));

        if (!"CLIENT".equalsIgnoreCase(String.valueOf(client.role()))) {
            throw new IllegalStateException("Usuário informado não é um CLIENTE válido");
        }

        UserDTO seller = userService.findUserById(saleDTO.sellerId(), token)
                .orElseThrow(() -> new NoSuchElementException("Vendedor não encontrado"));

        if (!"VENDOR".equalsIgnoreCase(String.valueOf(seller.role()))) {
            throw new IllegalStateException("Usuário informado não é um VENDEDOR válido");
        }

        BigDecimal finalPrice = calcularPreco(vehicle.getBasePrice(), vehicle.getColor(), client);

        Sale sale = Sale.builder()
                .type(SaleType.FISICA)
                .clientId(client.id())
                .sellerId(seller.id())
                .vehicle(vehicle)
                .date(LocalDateTime.now())
                .finalPrice(finalPrice)
                .build();

        vehicle.setAvailable(false);
        saleRepository.save(sale);
        vehicleRepository.save(vehicle);
    }

}

