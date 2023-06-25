package com.example.baeldungtest.libruaryoop.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//To suppress serializing properties with null values
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
//Ignoring new fields on JSON objects
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "phieu_muon")
@Data
public class PhieuMuon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maPhieuMuon;
    @Column(nullable = false)
    private int soDongMuon;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayMuon;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayTra;
    @Column(name = "`checkPhieuMuon`")
    private boolean checkPhieuMuon;
    @Column(name = "`checkluutru`")
    private boolean checkluutru;
    ////
    @OneToMany(mappedBy = "phieuMuon")
    private List<ChiTietMuon> chiTietMuon;

    @ManyToOne
    @JoinColumn(name = "soThe")
    private TheTV ttv;
}

