package com.example.baeldungtest.libruaryoop.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

import javax.persistence.*;

//To suppress serializing properties with null values
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
//Ignoring new fields on JSON objects
@JsonIgnoreProperties(ignoreUnknown = true)
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

