package com.example.libruaryoop.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

//To suppress serializing properties with null values
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
//Ignoring new fields on JSON objects
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "thetv")
@Data
public class TheTV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long soThe;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayBatDau;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayHetHan;
    @Column(name = "`checkTTV`")
    private boolean checkTTV;
    /////
    @OneToOne
    @JoinColumn(name = "maDocGia")
    private DocGia docGia;
    @OneToMany(mappedBy = "ttv")
    private List<PhieuMuon> phieuMuon;
}
