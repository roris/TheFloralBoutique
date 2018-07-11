package com.example.floralboutique.ui.admin.promotiondetail;

public class CheckedFlower {
    public final String name;
    public final String image;
    public final float price;
    public final float discountPercent;
    public boolean checked;

    public CheckedFlower(String name, String image, float price, float discountPercent, boolean checked) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.discountPercent = discountPercent;
        this.checked = checked;
    }
}
