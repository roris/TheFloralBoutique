package com.example.floralboutique.ui.member.flowerlist;

public class MemberFlowerListItemModel {
    public final String name;
    public final String image;
    public final float price;
    public final float discountPercent;
    public final int quantity;

    public MemberFlowerListItemModel(String name, String image, float price, float discountPercent, int quantity) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.discountPercent = discountPercent;
        this.quantity = quantity;
    }
}
