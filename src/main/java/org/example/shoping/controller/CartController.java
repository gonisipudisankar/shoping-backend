package org.example.shoping.controller;

import org.example.shoping.model.Cart;
import org.example.shoping.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{sessionId}")
    public ResponseEntity<Cart> getCart(@PathVariable String sessionId) {
        return ResponseEntity.ok(cartService.getOrCreateCart(sessionId));
    }

    @PostMapping("/{sessionId}/add")
    public ResponseEntity<Cart> addToCart(@PathVariable String sessionId,
                                          @RequestParam Long productId,
                                          @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.addToCart(sessionId, productId, quantity));
    }

    @DeleteMapping("/{sessionId}/remove/{cartItemId}")
    public ResponseEntity<Cart> removeFromCart(@PathVariable String sessionId,
                                               @PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartService.removeFromCart(sessionId, cartItemId));
    }

    @DeleteMapping("/{sessionId}/clear")
    public ResponseEntity<String> clearCart(@PathVariable String sessionId) {
        cartService.clearCart(sessionId);
        return ResponseEntity.ok("Cart cleared successfully");
    }
}