package com.example.libruaryoop.Model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class ChiTietMuonDto {
    private List<ChiTietMuon> chiTietMuonList =new ArrayList<>();
     public void addChiTietMuon(ChiTietMuon chiTietMuon){
         this.chiTietMuonList.add(chiTietMuon);
     }
}
