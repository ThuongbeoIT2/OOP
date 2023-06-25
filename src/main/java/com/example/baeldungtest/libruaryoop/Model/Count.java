package com.example.baeldungtest.libruaryoop.Model;

import lombok.Data;

@Data
public class Count {
    private int soLuong;
    private Long maSach;

    public Count() {
        this.soLuong=0;
    }
}
