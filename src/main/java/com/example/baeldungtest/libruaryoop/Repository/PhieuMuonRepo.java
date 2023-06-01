package com.example.libruaryoop.Repository;


import com.example.libruaryoop.Model.PhieuMuon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhieuMuonRepo extends JpaRepository<PhieuMuon,Long> {
    @Query("select u from phieu_muon u where u.checkPhieuMuon=true and u.checkluutru=false order by u.maPhieuMuon asc ")
    List<PhieuMuon> findAllCheckTrue();

    @Query("select u from phieu_muon u where u.checkPhieuMuon=false and u.checkluutru=false order by u.maPhieuMuon asc ")
    List<PhieuMuon> findAllCheckFalse();

    @Query("select u from phieu_muon u where u.checkluutru=true order by u.maPhieuMuon asc ")
    List<PhieuMuon> findAllCheckLuutru();
    @Query("SELECT u from phieu_muon u where u.checkluutru=false and u.maPhieuMuon= :maPhieuMuon")
    PhieuMuon findPhieuMuonById(@Param("maPhieuMuon") Long maPhieuMuon);

}
