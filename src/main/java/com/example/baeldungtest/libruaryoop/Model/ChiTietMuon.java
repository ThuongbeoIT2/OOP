package com.example.baeldungtest.libruaryoop.Model;

import lombok.Data;

import javax.persistence.*;

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

