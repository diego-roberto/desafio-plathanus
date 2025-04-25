package com.plathanus.sales.entity;

import com.plathanus.sales.enums.Color;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int year;

    private BigDecimal basePrice;

    @Enumerated(EnumType.STRING)
    private Color color;

    private String model;

    private boolean available = true;

}
