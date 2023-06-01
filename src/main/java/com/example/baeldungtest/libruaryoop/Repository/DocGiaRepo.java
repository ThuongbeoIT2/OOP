package com.example.libruaryoop.Repository;

import com.example.libruaryoop.Model.DocGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocGiaRepo extends JpaRepository<DocGia, Long> {
    @Query("select u from doc_gia u where u.checkDocGia=true order by u.checkDocGia asc ")
    List<DocGia> findAllCheckTrue();

    @Query("select u from doc_gia u where u.checkDocGia=false order by u.maDocGia asc ")
    List<DocGia> findAllCheckFalse();

    @Query("select u from doc_gia u where u.soDienThoai like %:key% or u.tenDocGia like %:key% or u.email like %:key% or u.CCCD like %:key% order by u.maDocGia asc ")
    List<DocGia> searchPro(String key);

    @Query("SELECT u FROM doc_gia u WHERE u.tenDocGia = :tenDocGia")
    DocGia findByName(@Param("tenDocGia") String tenDocGia);



}
