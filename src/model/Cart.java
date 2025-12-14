package model;

import java.util.ArrayList;

public class Cart {
    private ArrayList<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void addToCart(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    
    public void updateItemQuantity(Product product, int newQuantity) {
        if (newQuantity <= 0) {
            removeItem(product.getId());
            return;
        }
        
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(newQuantity);
                return;
            }
        }
    }
    
    
    public void removeItem(Product product) {
        removeItem(product.getId());
    }

    // Method Asli: Menghapus item berdasarkan ID Produk (Dipanggil dari updateItemQuantity)
    public void removeItem(String productId) {
        items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public int getTotal() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void clearCart() {
        items.clear();
    }
}