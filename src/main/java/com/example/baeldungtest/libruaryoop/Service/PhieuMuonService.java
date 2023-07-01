package com.example.baeldungtest.libruaryoop.Service;



import com.example.baeldungtest.libruaryoop.Model.PhieuMuon;
import com.example.baeldungtest.libruaryoop.Repository.PhieuMuonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhieuMuonService {
    @Autowired
    private PhieuMuonRepo phieuMuonRepo;
    public List<PhieuMuon> findAllCheckTrue() {
        return phieuMuonRepo.findAllCheckTrue();
    }

    public List<PhieuMuon> findAllCheckFalse() {
        return phieuMuonRepo.findAllCheckFalse();
    }

    public List<PhieuMuon> findAllCheckLuutru() {
        return phieuMuonRepo.findAllCheckLuutru();
    }
    public void deleteById(Long maPhieuMuon) {
        PhieuMuon fromDB = phieuMuonRepo.findById(maPhieuMuon).orElse(null);
        if (fromDB == null) {
            return;

        }
        fromDB.setCheckluutru(true);
        fromDB.setCheckPhieuMuon(false);
        phieuMuonRepo.save(fromDB);

    }

    public void changeById(Long maPhieuMuon) {
        PhieuMuon fromDB = phieuMuonRepo.findById(maPhieuMuon).orElse(null);
        if (fromDB == null) {
            return;

        }
        fromDB.setCheckPhieuMuon(true);
        fromDB.setCheckluutru(false);
        phieuMuonRepo.save(fromDB);

    }

    public void addPhieuMuon(PhieuMuon phieuMuon) {
        phieuMuon.setCheckPhieuMuon(false);
        phieuMuon.setCheckluutru(false);
        phieuMuonRepo.save(phieuMuon);

    }

    public PhieuMuon updatePhieuMuon(PhieuMuon phieuMuon) {
        PhieuMuon existing = phieuMuonRepo.findById(phieuMuon.getMaPhieuMuon()).orElse(null);
        if (existing != null) {
            existing.setSoDongMuon(phieuMuon.getSoDongMuon());
            existing.setTtv(phieuMuon.getTtv());
            existing.setChiTietMuon(phieuMuon.getChiTietMuon());
            existing.setNgayMuon(phieuMuon.getNgayMuon());
            existing.setNgayTra(phieuMuon.getNgayTra());
            existing.setCheckPhieuMuon(true);
            existing.setCheckluutru(false);
            return phieuMuonRepo.save(existing);
        }
        return null;
    }

    public PhieuMuon findPhieuMuonById(Long maPhieuMuon) {
        return phieuMuonRepo.findPhieuMuonById(maPhieuMuon);
    }



}