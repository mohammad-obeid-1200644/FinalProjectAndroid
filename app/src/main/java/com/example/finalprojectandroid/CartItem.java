package com.example.finalprojectandroid;

public class CartItem {
    private int image;
    private String CafName,FoodName;
    private double price;
    private int quantity;
    private int id;

    public CartItem() {}

    public CartItem(int id,String cafName, String foodName, double price, int quantity,int image) {
        CafName = cafName;
        FoodName = foodName;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.id=id;
    }
    public String getCafName() {
        return CafName;
    }

    public void setCafName(String cafName) {
        CafName = cafName;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
