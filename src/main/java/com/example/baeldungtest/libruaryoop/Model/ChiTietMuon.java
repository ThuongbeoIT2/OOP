package com.example.libruaryoop.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;

//To suppress serializing properties with null values
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
//Ignoring new fields on JSON objects
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "chi_tiet_muon")
@Data
public class ChiTietMuon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dongPhieu;
    @Column(nullable = false)
    private int soLuongMuon;
    /////
    @OneToOne
    @JoinColumn(name = "maSach")
    private Sach sach;

    @ManyToOne
    @JoinColumn(name = "maPhieuMuon")
    private PhieuMuon phieuMuon;
}

