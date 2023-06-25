package com.example.baeldungtest.libruaryoop.Service;

import com.example.baeldungtest.libruaryoop.Model.TheTV;
import com.example.baeldungtest.libruaryoop.Repository.TheTVRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheTVService {
    @Autowired
    private TheTVRepo theTVRepo;

    public List<TheTV> findAllCheckTrue() {
        return theTVRepo.findAllCheckTrue();
    }
    public List<TheTV> findAllCheckFalse() {
        return theTVRepo.findAllCheckFalse();
    }

    public void deleteById(Long soThe) {
        TheTV fromDB = theTVRepo.findById(soThe).orElse(null);
        if (fromDB == null) {
            return;
        }
        fromDB.setCheckTTV(false);
        theTVRepo.save(fromDB);

    }
    public void changeById(Long soThe) {
        TheTV fromDB = theTVRepo.findById(soThe).orElse(null);
        if (fromDB == null) {
            return;
        }
        fromDB.setCheckTTV(true);
        theTVRepo.save(fromDB);

    }

    public void addTTV(TheTV theTV) {
        theTV.setCheckTTV(true);
        theTVRepo.save(theTV);
    }

    public TheTV updateTTV(TheTV theTV){
        TheTV existing = theTVRepo.findById(theTV.getSoThe()).orElse(null);
        if(existing!=null){
            existing.setNgayBatDau(theTV.getNgayBatDau());
            existing.setNgayHetHan(theTV.getNgayHetHan());
            existing.setCheckTTV(true);
            System.out.println(existing);
            return theTVRepo.save(existing);
        }
        return null;
    }
    public TheTV findTTVById(Long soThe){
        return theTVRepo.findById(soThe).orElse(null);
    }
    public TheTV findUserByName(String tenNXB){
        return theTVRepo.findByName(tenNXB);
    }

}
