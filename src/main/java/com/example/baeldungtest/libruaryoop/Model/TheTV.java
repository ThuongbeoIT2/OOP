package com.example.baeldungtest.libruaryoop.Model;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
