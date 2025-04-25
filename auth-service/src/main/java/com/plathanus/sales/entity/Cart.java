package com.plathanus.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    private Vehicle vehicle;

    private UUID clientId;

    private LocalDateTime createdAt;

    public boolean isExpired() {
        return createdAt.plusMinutes(1).isBefore(LocalDateTime.now());
    }

}

