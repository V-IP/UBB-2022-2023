package com.example.modelexamen.domainDTO;

public class MenuItemDTO {
    String category;
    String item;
    String price;
    int quantity;

    public MenuItemDTO(String category, String item, String price, int quantity) {
        this.category = category;
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getItem() {
        return item;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}