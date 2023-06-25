package com.example.baeldungtest.libruaryoop.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

import javax.persistence.*;

//To suppress serializing properties with null values
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
//Ignoring new fields on JSON objects
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "sach")
@Data
public class Sach {
    private static final long serialVersionUID = 8609175038441759607L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maSach;
    @Column(nullable = false)
    private String tenSach;
    @Column(nullable = false)
    private int soLuongCon;
    @Column(nullable = false)
    private int daMuon;
    @Column(nullable = false)
    private String tacGia;
    @Column(nullable = false)
    private int namXB;
    @Column(nullable = false)
    private String NXB;
    @Column(name = "`checkSach`")
    private boolean checkSach;
    //////



}

