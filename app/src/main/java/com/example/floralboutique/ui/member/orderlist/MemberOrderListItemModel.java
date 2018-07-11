package com.example.floralboutique.ui.member.orderlist;

import java.util.Date;

public class MemberOrderListItemModel {
    public final int id;
    public final Date date;
    public final String status;

    public MemberOrderListItemModel(int id, String status, Date date) {
        this.id = id;
        this.date = date;
        this.status = status;
    }

    /*
    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
    */
}
