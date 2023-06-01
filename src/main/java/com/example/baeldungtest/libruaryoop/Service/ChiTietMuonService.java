package com.example.libruaryoop.Service;

import com.example.libruaryoop.Model.ChiTietMuon;
import com.example.libruaryoop.Repository.ChiTietMuonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ChiTietMuonService {
    @Autowired
    private ChiTietMuonRepo chiTietMuonRepo;

    public List<ChiTietMuon> findAll() {
        return chiTietMuonRepo.findAll();
    }

    public List<ChiTietMuon> findAllPhieuMuon(Long maPhieuMuon) {
        return chiTietMuonRepo.findAllPhieuMuon(maPhieuMuon);
    }

    public void deleteById(Long dongPhieu) {
        ChiTietMuon fromDB = chiTietMuonRepo.findById(dongPhieu).orElse(null);
        if (fromDB == null) {
            return;

        }

        chiTietMuonRepo.save(fromDB);

    }


    public void addChiTiet(ChiTietMuon chiTietMuon) {

        chiTietMuonRepo.save(chiTietMuon);
    }

    public ChiTietMuon updateChiTiet(ChiTietMuon chiTietMuon){
        ChiTietMuon existing = chiTietMuonRepo.findById(chiTietMuon.getDongPhieu()).orElse(null);
        if(existing!=null){
            existing.setSoLuongMuon(chiTietMuon.getSoLuongMuon());
            existing.setSach(chiTietMuon.getSach());

             chiTietMuonRepo.save(existing);
        }
        return null;
    }
    public ChiTietMuon findUserById(Long dongPhieu){
        return chiTietMuonRepo.findById(dongPhieu).orElse(null);
    }

}
