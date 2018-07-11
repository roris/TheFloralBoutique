package com.example.floralboutique.ui.member.flowerdetail;

public class MemberFlowerDetailItemModel {
    public final String name;
    public final String image;
    public final String description;
    public final float price;
    public final float discountPercent;
    public final int quantity;

    public MemberFlowerDetailItemModel(String name, String description, String image, float price, float discountPercent, int quantity) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.discountPercent = discountPercent;
        this.quantity = quantity;
    }
}
