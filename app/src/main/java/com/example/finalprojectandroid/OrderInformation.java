package com.example.finalprojectandroid;

public class OrderInformation {

    String foodname;
    int quantity;
    double price;
    String CafName;
    int imageID;
    double total;


    public OrderInformation(String foodname, int quantity, double price, String cafName, int imageID) {
        this.foodname = foodname;
        this.quantity = quantity;
        this.price = price;
        CafName = cafName;
        this.imageID = imageID;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCafName() {
        return CafName;
    }

    public void setCafName(String cafName) {
        CafName = cafName;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}