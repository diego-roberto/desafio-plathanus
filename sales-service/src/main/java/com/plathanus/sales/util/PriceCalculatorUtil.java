package com.plathanus.sales.util;

import com.plathanus.sales.dto.UserDTO;
import com.plathanus.sales.enums.Color;

import java.math.BigDecimal;

public class PriceCalculatorUtil {

    public static BigDecimal calcularPreco(BigDecimal basePrice, Color color, UserDTO client) {
        BigDecimal colorMultiplier = switch (color) {
            case SILVER -> BigDecimal.valueOf(1.01);
            case BLACK -> BigDecimal.valueOf(1.02);
            default -> BigDecimal.ONE;
        };

        BigDecimal adjusted = basePrice.multiply(colorMultiplier);

        if ("pj".equalsIgnoreCase(client.username())) {
            return adjusted.multiply(BigDecimal.valueOf(0.8));
        } else if ("pcd".equalsIgnoreCase(client.username())) {
            return adjusted.multiply(BigDecimal.valueOf(0.7));
        }

        return adjusted;
    }    
    
}
