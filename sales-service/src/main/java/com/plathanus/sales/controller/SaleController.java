package com.plathanus.sales.controller;

import com.plathanus.sales.dto.SaleDTO;
import com.plathanus.sales.service.SaleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping("/fisica")
    public ResponseEntity<?> storeSale(HttpServletRequest request, @RequestBody SaleDTO saleDTO) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        saleService.storeSale(saleDTO, token);
        return ResponseEntity.ok("Venda física registrada com sucesso");
    }

}
