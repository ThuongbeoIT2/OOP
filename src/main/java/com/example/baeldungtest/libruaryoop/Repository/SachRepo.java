package com.example.baeldungtest.libruaryoop.Repository;


import com.example.baeldungtest.libruaryoop.Model.Sach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SachRepo extends JpaRepository<Sach,Long> {
    // Xuất ra các sách có thể cho mượn
    @Query("select u from sach u where u.checkSach=true order by u.maSach asc ")
    List<Sach> findAllCheckTrue();
    @Query("select u from sach u order by u.maSach asc ")
    List<Sach> findAll();
    //Xuất ra các sách đã hết nhưng có trong thư viện
    @Query("select u from sach u where u.checkSach=false order by u.maSach asc ")
    List<Sach> findAllCheckFalse();
    // Tìm kiếm theo tên sách . Phục vụ khi làm phiếu
    @Query("SELECT u FROM sach u WHERE u.tenSach = :tenSach")
    Sach findByName(@Param("tenSach") String tenSach);
    // Tìm kiếm nhanh khi làm phiếu
    @Query("select u from sach u where u.tenSach like %:key% or u.tacGia like %:key% or u.NXB like %:key% and u.checkSach=true order by u.maSach asc ")
    List<Sach> searchPro(String key);
    // Tìm kiếm theo mã sách
    @Query("SELECT u from sach u where u.maSach= :maSach")
    Sach findSachById(@Param("maSach") Long maSach);
    @Query("select u from sach  u where u.daMuon>0 order by u.maSach desc ")
    List<Sach> findByDaMuon();
    @Query("select u.soLuongCon from sach  u where u.maSach=:maSach ")
    Sach findsoLuongById(Long maSach);
    @Query("select u from sach  u where u.soLuongCon<=:soLuong ")
    List<Sach> findSachBySoLuong(int soLuong);
}
