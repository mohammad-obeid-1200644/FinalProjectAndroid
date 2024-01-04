package com.example.finalprojectandroid;

public class FoodItem {
    private String FoodName;
    private double FoodPrice;
    private String FoodSize;
    private int FoodImg;

    public FoodItem() {
    }
    public FoodItem(String foodName, double foodPrice, String foodSize,int Foodimg) {
        FoodName = foodName;
        FoodPrice = foodPrice;
        FoodSize = "Default";
        FoodImg=Foodimg;
    }
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

    public String getFoodSize() {
        return FoodSize;
    }

    public void setFoodSize(String foodSize) {
        FoodSize = foodSize;
    }

    public int getFoodImg() {
        return FoodImg;
    }

    public void setFoodImg(int foodImg) {
        FoodImg = foodImg;
    }
}
