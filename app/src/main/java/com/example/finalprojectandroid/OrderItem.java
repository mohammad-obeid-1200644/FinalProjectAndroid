package com.example.finalprojectandroid;
public class OrderItem {

    int numberoforder;
    int orderNumber_val;
    double Totalprice_val;
    double totalprice;

    public OrderItem(int numberoforder, double totalprice) {
        this.numberoforder = numberoforder;
        this.totalprice = totalprice;
    }



    public int getOrderNumber_val() {
        return orderNumber_val;
    }

    public void setOrderNumber_val(int orderNumber_val) {
        this.orderNumber_val = orderNumber_val;
    }

    public double getTotalprice_val() {
        return Totalprice_val;
    }

    public void setTotalprice_val(double totalprice_val) {
        Totalprice_val = totalprice_val;
    }



    public int getNumberoforder() {
        return numberoforder;
    }

    public void setNumberoforder(int numberoforder) {
        this.numberoforder = numberoforder;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }
}
