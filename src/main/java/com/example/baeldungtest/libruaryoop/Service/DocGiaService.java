package com.example.baeldungtest.libruaryoop.Service;

import com.example.baeldungtest.libruaryoop.Model.DocGia;
import com.example.baeldungtest.libruaryoop.Repository.DocGiaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocGiaService {
    @Autowired
    private DocGiaRepo docGiaRepo;

    public List<DocGia> findAllCheckTrue() {
        return docGiaRepo.findAllCheckTrue();
    }
    public List<DocGia> findAllCheckFalse() {
        return docGiaRepo.findAllCheckFalse();
    }
    public List<DocGia> searchPro(String key) {
        return docGiaRepo.searchPro(key);
    }

    public void deleteById(Long maDocGia) {
        DocGia fromDB = docGiaRepo.findById(maDocGia).orElse(null);
        if (fromDB == null) {
            return;

        }
        fromDB.setCheckDocGia(false);
        docGiaRepo.save(fromDB);

    }
    public void changeById(Long maDocGia) {
        DocGia fromDB = docGiaRepo.findById(maDocGia).orElse(null);
        if (fromDB == null) {
            return;

        }
        fromDB.setCheckDocGia(true);
        docGiaRepo.save(fromDB);

    }

    public void addDocGia(DocGia docGia) {
        docGia.setCheckDocGia(false);
        docGiaRepo.save(docGia);
    }

    public DocGia updateDocGia(DocGia docGia){
        DocGia existing = docGiaRepo.findById(docGia.getMaDocGia()).orElse(null);
        if(existing!=null){
            existing.setTenDocGia(docGia.getTenDocGia().trim());
            existing.setSoDienThoai(docGia.getSoDienThoai().trim());
            existing.setCCCD(docGia.getCCCD().trim());
            existing.setEmail(docGia.getEmail().trim());
            return docGiaRepo.save(existing);
        }
        return null;
    }
    public DocGia findDocGiaById(Long maDocGia){
        return docGiaRepo.findById(maDocGia).orElse(null);
    }
    public DocGia findUserByName(String tenDocGia){
        return docGiaRepo.findByName(tenDocGia);
    }

}
