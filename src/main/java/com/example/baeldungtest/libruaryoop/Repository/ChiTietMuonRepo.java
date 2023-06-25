package com.example.baeldungtest.libruaryoop.Repository;

import com.example.baeldungtest.libruaryoop.Model.ChiTietMuon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietMuonRepo extends JpaRepository<ChiTietMuon,Long> {
    @Query("select u from chi_tiet_muon u order by u.dongPhieu asc ")
    List<ChiTietMuon> findAll();

    @Query("select u from chi_tiet_muon u where u.phieuMuon.maPhieuMuon =:maPhieuMuon order by u.dongPhieu asc ")
    List<ChiTietMuon> findAllPhieuMuon(@Param("maPhieuMuon") Long maPhieuMuon);
    @Query("SELECT u from chi_tiet_muon u where u.dongPhieu= :dongPhieu")
    ChiTietMuon findPhieuMuonById(@Param("dongPhieu") Long dongPhieu);


}
