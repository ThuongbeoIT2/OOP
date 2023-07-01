package com.example.baeldungtest.libruaryoop.Model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "doc_gia")
@Data
public class DocGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maDocGia;
    @Column(nullable = false)
    private String tenDocGia;
    @Column(nullable = false)
    private String soDienThoai;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String CCCD;
    @Column(name = "`checkDocGia`")
    private boolean checkDocGia;
    /////

}

