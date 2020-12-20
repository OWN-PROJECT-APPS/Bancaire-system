package com.m.bank.Modules;

import java.io.Serializable;
public class Transaction  implements Serializable {

        private int num ;
        private Double price;
        private String date;

    public Transaction(int num , Double price, String date) {
        this.num  = num;
        this.price = price;
        this.date = date;
    }

    public Integer getNum() {
        return num;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "num=" + num +
                ", price=" + price +
                ", date='" + date + '\'' +
                '}';
    }
}
