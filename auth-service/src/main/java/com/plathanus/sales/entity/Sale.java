package com.plathanus.sales.entity;

import com.plathanus.sales.enums.SaleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private SaleType type;

    private UUID clientId;

    private UUID sellerId;

    @OneToOne
    private Vehicle vehicle;

    private LocalDateTime date;

    private BigDecimal finalPrice;

}

