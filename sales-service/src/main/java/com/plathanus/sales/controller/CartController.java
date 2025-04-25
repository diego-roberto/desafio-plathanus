package com.plathanus.sales.controller;

import com.plathanus.sales.dto.CartDTO;
import com.plathanus.sales.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDTO> viewCart(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        CartDTO cart = cartService.getCart(token);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<CartDTO> addToCart(@RequestParam UUID vehicleId, HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        CartDTO cart = cartService.addToCart(token, vehicleId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        cartService.checkout(token);
        return ResponseEntity.ok("Venda registrada com sucesso!");
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<Void> cancelCart(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        cartService.cancel(token);
        return ResponseEntity.noContent().build();
    }

}