package com.plathanus.sales.mapper;

import com.plathanus.sales.dto.VehicleDTO;
import com.plathanus.sales.entity.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public VehicleDTO toDto(Vehicle vehicle) {
        return new VehicleDTO(
                vehicle.getId(),
                vehicle.getYear(),
                vehicle.getBasePrice(),
                vehicle.getColor(),
                vehicle.getModel(),
                vehicle.isAvailable()
        );
    }

}
