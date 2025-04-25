package com.plathanus.sales.controller;

import com.plathanus.sales.dto.VehicleDTO;
import com.plathanus.sales.mapper.VehicleMapper;
import com.plathanus.sales.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;


    @GetMapping
    public ResponseEntity<List<VehicleDTO>> listAvailableVehicles() {
        List<VehicleDTO> available = vehicleRepository.findByAvailableTrue().stream()
                .map(vehicleMapper::toDto)
                .toList();
        return ResponseEntity.ok(available);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleDetails(@PathVariable UUID id) {
        return vehicleRepository.findById(id)
                .map(vehicleMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}

