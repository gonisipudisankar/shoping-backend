package org.example.shoping.controller;

import org.example.shoping.model.Order;
import org.example.shoping.repository.OrderRepository;
import org.example.shoping.repository.ProductRepository;
import org.example.shoping.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Dashboard Stats
    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProducts", productRepository.count());
        stats.put("totalOrders", orderRepository.count());
        stats.put("totalCategories", categoryRepository.count());
        stats.put("totalRevenue", orderRepository.findAll()
                .stream()
                .mapToDouble(o -> o.getTotalAmount().doubleValue())
                .sum());
        stats.put("recentOrders", orderRepository.findAll()
                .stream()
                .sorted((a, b) -> b.getOrderDate().compareTo(a.getOrderDate()))
                .limit(5)
                .toList());
        return ResponseEntity.ok(stats);
    }

    // Get All Orders
    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    // Update Order Status
    @PutMapping("/orders/{id}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
        return ResponseEntity.ok("Order status updated!");
    }
}