package com.example.libruaryoop.Service;

import com.example.libruaryoop.Model.Sach;
import com.example.libruaryoop.Repository.SachRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SachService {
    @Autowired
    private SachRepo sachRepo;

    public List<Sach> findAllCheckTrue() {
        return sachRepo.findAllCheckTrue();
    }
    public List<Sach> findAllCheckFalse() {
        return sachRepo.findAllCheckFalse();
    }
    public List<Sach> findAll() {
        return sachRepo.findAll();
    }
    public List<Sach> searchPro(String key) {
        return sachRepo.searchPro(key);
    }
    public void deleteById(Long maSach) {
        Sach fromDB = sachRepo.findById(maSach).orElse(null);
        if (fromDB == null) {
            return;

        }
        fromDB.setCheckSach(false);
        sachRepo.save(fromDB);

    }
    public void changeById(Long maSach) {
        Sach fromDB = sachRepo.findById(maSach).orElse(null);
        if (fromDB == null) {
            return;

        }
        fromDB.setCheckSach(true);
        sachRepo.save(fromDB);

    }

    public void addSach(Sach sach) {
        sach.setCheckSach(false);
        sachRepo.save(sach);
    }

    public Sach updateSach(Sach sach){
        Sach existing = sachRepo.findById(sach.getMaSach()).orElse(null);
        if(existing!=null){
            existing.setTenSach(sach.getTenSach());
            existing.setTacGia(sach.getTacGia());
            existing.setDaMuon(sach.getDaMuon());
            existing.setSoLuongCon(sach.getSoLuongCon());
            existing.setCheckSach(true);
            existing.setNXB(sach.getNXB());
            existing.setNamXB(sach.getNamXB());
            return sachRepo.save(existing);
        }
        return null;
    }
    public Sach findSachById(Long maSach){
        return sachRepo.findSachById(maSach);
    }
    public Sach findByName(String tenSach){
        return sachRepo.findByName(tenSach);
    }
    public Sach findsoLuongById(Long maSach){
        return sachRepo.findsoLuongById(maSach);
    }
    public List<Sach> findByDaMuon(){
        return sachRepo.findByDaMuon();
    }
    public List<Sach> findSachBySoLuong(Long soLuong) {
        return  sachRepo.findSachBySoLuong(soLuong);
    }
}
