package org.example.shoping.controller;

import org.example.shoping.model.Order;
import org.example.shoping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(
            @RequestParam String sessionId,
            @RequestParam String customerName,
            @RequestParam String customerEmail,
            @RequestParam String address) {
        return ResponseEntity.ok(orderService.placeOrder(
                sessionId, customerName, customerEmail, address));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/customer/{email}")
    public List<Order> getOrdersByEmail(@PathVariable String email) {
        return orderService.getOrdersByEmail(email);
    }
}
