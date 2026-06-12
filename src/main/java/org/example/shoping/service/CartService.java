package org.example.shoping.service;

import org.example.shoping.model.*;
import org.example.shoping.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart getOrCreateCart(String sessionId) {
        return cartRepository.findBySessionId(sessionId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setSessionId(sessionId);
                    cart.setCartItems(new ArrayList<>());
                    return cartRepository.save(cart);
                });
    }

    public Cart addToCart(String sessionId, Long productId, Integer quantity) {
        Cart cart = getOrCreateCart(sessionId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if product already in cart
        for (CartItem item : cart.getCartItems()) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                return cartRepository.save(cart);
            }
        }

        // Add new cart item
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cart.getCartItems().add(cartItem);
        return cartRepository.save(cart);
    }

    public Cart removeFromCart(String sessionId, Long cartItemId) {
        Cart cart = getOrCreateCart(sessionId);
        cart.getCartItems().removeIf(item -> item.getId().equals(cartItemId));
        return cartRepository.save(cart);
    }

    public void clearCart(String sessionId) {
        Cart cart = getOrCreateCart(sessionId);
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
}