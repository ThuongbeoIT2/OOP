package com.example.libruaryoop.Controller;


import com.example.libruaryoop.Model.*;
import com.example.libruaryoop.Service.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
public class PhieuMuonController {
    @Autowired
    private PhieuMuonService phieuMuonService;
    @Autowired
    private ChiTietMuonService chiTietMuonService;
    @Autowired
    private SachService sachService;
    @Autowired
    private NhanSuService nhanSuService;
    @Autowired
    private TheTVService theTVService;
    @GetMapping("/list-phieumuon")
    public String index(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        System.out.println(username);
        model.addAttribute("listTrue", phieuMuonService.findAllCheckTrue());
        model.addAttribute("listFalse", phieuMuonService.findAllCheckFalse());
        return "admin/phieumuon/list_phieumuon";
    }
    @GetMapping("/queue-phieumuon")
    public String HangDoi(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        System.out.println(username);
        model.addAttribute("listFalse", phieuMuonService.findAllCheckFalse());
        return "admin/phieumuon/hangdoi_phieumuon";
    }
    @GetMapping("/list-luutru")
    public String Store(Model model,HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("listluutru", phieuMuonService.findAllCheckLuutru());
        return "admin/phieumuon/list_luutru";
    }
    @GetMapping("/add-phieumuon")
    public String addPhieuMuon(Model model,HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("phieuMuon", new PhieuMuon());
        model.addAttribute("list_user",nhanSuService.findAllCheckTrue() );
        return "admin/phieumuon/add_phieumuon";
    }
    @PostMapping("/add-phieumuon")
    public String addPhieuMuon(@ModelAttribute("phieuMuon") PhieuMuon phieuMuon) {

        phieuMuon.setSoDongMuon(phieuMuon.getSoDongMuon());
        phieuMuon.setChiTietMuon(phieuMuon.getChiTietMuon());
        phieuMuon.setTtv(phieuMuon.getTtv());
        phieuMuon.setNgayMuon(phieuMuon.getNgayMuon());
        phieuMuon.setNgayTra(phieuMuon.getNgayTra());
        phieuMuonService.addPhieuMuon(phieuMuon);
        return "redirect:/list-phieumuon";
    }

////

    @GetMapping("/edit-phieumuon/{maPhieuMuon}")
    public String editPhieuMoi(Model model, @PathVariable Long maPhieuMuon,HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        PhieuMuon phieuMuon = phieuMuonService.findPhieuMuonById(maPhieuMuon);

        model.addAttribute("phieuMuon", phieuMuon);
        return "admin/phieumuon/edit_phieumuon";
    }

    @PostMapping("/update-phieumuon")
    public String editPhieuMuon(@ModelAttribute("phieumuon") PhieuMuon phieuMuon) {
        phieuMuonService.updatePhieuMuon(phieuMuon);

        return "redirect:/list-phieumuon";
    }

    @GetMapping("/delete-phieumuon/{maPhieuMuon}")
    public String deleteById(@PathVariable("maPhieuMuon") Long maPhieuMuon,HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        phieuMuonService.deleteById(maPhieuMuon);
        return "redirect:/list-phieumuon";
    }
    @GetMapping("/change-phieumuon/{maPhieuMuon}")
    public String changeById(@PathVariable("maPhieuMuon") Long maPhieuMuon,HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        phieuMuonService.changeById(maPhieuMuon);
        return "redirect:/list-phieumuon";
    }
    ///////////////////
    @GetMapping("/list-chitietmuon/{maPhieuMuon}")
    public String ChiTietPhieu(Model model,@PathVariable("maPhieuMuon") Long maPhieuMuon,HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("list", chiTietMuonService.findAllPhieuMuon(maPhieuMuon));

        return "admin/phieumuon/list_chitiet";
    }

    //////
    @GetMapping("/add-chitietphieu/{maPhieuMuon}")
    public String addImport(Model model, @PathVariable Long maPhieuMuon,HttpSession session){
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        PhieuMuon phieuMuondto = phieuMuonService.findPhieuMuonById(maPhieuMuon);
        model.addAttribute("pmdto", phieuMuondto);
        model.addAttribute("phieumuon",phieuMuonService.findAllCheckFalse());
        model.addAttribute("chitiet", new ChiTietMuon());
        model.addAttribute("sach", sachService.findAllCheckTrue());

//        List<Product> products = new ArrayList<>();
        ChiTietMuonDto chiTietMuonDto = new ChiTietMuonDto();
        for (int i = 0; i < phieuMuondto.getSoDongMuon(); i++) {
            chiTietMuonDto.addChiTietMuon(new ChiTietMuon());
        }
        model.addAttribute("sach",sachService.findAllCheckTrue());
        model.addAttribute("chitietList", chiTietMuonDto);
        return "admin/phieumuon/add_chitiet";
    }

    @PostMapping("add-chitietphieu")
    public String addChiTiet(@ModelAttribute("chitietList") ChiTietMuonDto chiTietMuonDto,
                      @ModelAttribute("pmdto") PhieuMuon phieuMuondto){
        System.out.println(phieuMuondto.getMaPhieuMuon());
        PhieuMuon phieuMuon=phieuMuonService.findPhieuMuonById(phieuMuondto.getMaPhieuMuon());
        phieuMuon.setCheckPhieuMuon(true);
        chiTietMuonDto.getChiTietMuonList().forEach(p -> {
            Sach sach = sachService.findSachById(p.getSach().getMaSach());
            System.out.println(phieuMuondto.getMaPhieuMuon());
            PhieuMuon phieuMuon1=phieuMuonService.findPhieuMuonById(phieuMuondto.getMaPhieuMuon());
            ChiTietMuon chiTietMuon = new ChiTietMuon();
            chiTietMuon.setSach(sach);
            chiTietMuon.setSoLuongMuon(p.getSoLuongMuon());
            chiTietMuon.setPhieuMuon(phieuMuon1);
            //// update số lượng
            sach.setSoLuongCon(sach.getSoLuongCon()-p.getSoLuongMuon());
            sach.setDaMuon(sach.getDaMuon()+ p.getSoLuongMuon());
            //
            sachService.updateSach(sach);
            chiTietMuonService.addChiTiet(chiTietMuon);
        });
//        PhieuMuon foundpm = phieuMuonService.findPhieuMuonById(phieuMuondto.getMaPhieuMuon());
//        System.out.println("so the"+ foundpm.getTtv().getSoThe());
//        TheTV theTV = theTVService.findTTVById(phieuMuondto.getTtv().getSoThe());
//        theTV.setCheckTTV(false);

        return "redirect:/list-phieumuon";
    }
    @GetMapping("/delete-chitietphieu/{maPhieuMuon}")
    public String deleteById(Model model,@PathVariable("maPhieuMuon") Long maPhieuMuon,HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        List<ChiTietMuon> chiTietMuonList = chiTietMuonService.findAllPhieuMuon(maPhieuMuon);
        chiTietMuonList.forEach(p->{
            Sach sach = sachService.findSachById(p.getSach().getMaSach());
            sach.setSoLuongCon(sach.getSoLuongCon()+p.getSoLuongMuon());
            System.out.println(sach.getDaMuon());
            sach.setDaMuon(sach.getDaMuon()- p.getSoLuongMuon());
            //
            sachService.updateSach(sach);
        });
        //
//        System.out.println(maPhieuMuon);
//        TheTV theTV = theTVService.findTTVById(maPhieuMuon);
//        theTV.setCheckTTV(true);

        phieuMuonService.deleteById(maPhieuMuon);
        return "redirect:/list-phieumuon";
    }
}
