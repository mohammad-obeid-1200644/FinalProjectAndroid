package com.example.finalprojectandroid;

public class FoodItem {
    private String FoodName;
    private double FoodPrice;
    private int FoodImg;
    private String extras[];
    private String sizes[];
    private int quantity;


    public FoodItem() {
    }
    public FoodItem(String foodName, double foodPrice,int Foodimg) {
        FoodName = foodName;
        FoodPrice = foodPrice;
        FoodImg=Foodimg;
    }

    public FoodItem(String foodName, double foodPrice, int foodImg, String[] extras, String[] sizes,int Quantity) {
        FoodName = foodName;
        FoodPrice = foodPrice;
        FoodImg = foodImg;
        this.extras = extras;
        this.sizes = sizes;
        quantity=Quantity;
    }
    //todo: add id

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public double getFoodPrice() {
        return FoodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        FoodPrice = foodPrice;
    }

    public int getFoodImg() {
        return FoodImg;
    }

    public void setFoodImg(int foodImg) {
        FoodImg = foodImg;
    }

    public String[] getExtras() {
        return extras;
    }

    public void setExtras(String[] extras) {
        this.extras = extras;
    }

    public String[] getSizes() {
        return sizes;
    }

    public void setSizes(String[] sizes) {
        this.sizes = sizes;
    }
}

