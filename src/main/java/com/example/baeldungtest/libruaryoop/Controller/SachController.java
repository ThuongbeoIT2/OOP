package com.example.baeldungtest.libruaryoop.Controller;

import com.example.baeldungtest.libruaryoop.Model.Count;
import com.example.baeldungtest.libruaryoop.Model.Sach;
import com.example.baeldungtest.libruaryoop.Service.SachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SachController {
    @Autowired
    private SachService sachService;
    @GetMapping("/list-sach")
    public String index(Model model) {
        model.addAttribute("listTrue", sachService.findAllCheckTrue());
        return "admin/sach/list_sach";
    }
    @GetMapping("/luutru-sach")
    public String Luutru(Model model) {
        model.addAttribute("listFalse", sachService.findAllCheckFalse());
        return "admin/sach/luutru_sach";
    }
    @GetMapping("/add-sach")
    public String addSach(Model model) {
        model.addAttribute("sach", new Sach());
        return "admin/sach/add_sach";
    }
    @PostMapping("/add-sach")
    public String addSach(@ModelAttribute("sach") Sach sach) {
        sach.setTenSach(sach.getTenSach().trim());
        sach.setTacGia(sach.getTacGia().trim());
        sach.setNXB(sach.getNXB().trim());
        sach.setDaMuon(sach.getDaMuon());
        sach.setSoLuongCon(sach.getSoLuongCon());
        sach.setCheckSach(true);
        sach.setNamXB(sach.getNamXB());
        sachService.addSach(sach);
        return "redirect:/list-sach";
    }
    @GetMapping("/edit-sach/{maSach}")
    public String editSach(Model model, @PathVariable Long maSach) {
        Sach sach = sachService.findSachById(maSach);
        model.addAttribute("sach", sach);
        return "admin/sach/edit_sach";
    }

    @PostMapping("/update-sach")
    public String editSach(@ModelAttribute("sach") Sach sach) {
        sachService.updateSach(sach);
        return "redirect:/list-sach";
    }

    @GetMapping("/delete-sach/{maSach}")
    public String deleteById(@PathVariable("maSach") Long maSach) {

        sachService.deleteById(maSach);
        return "redirect:/list-sach";
    }
    @GetMapping("/change-sach/{maSach}")
    public String changeById(@PathVariable("maSach") Long maSach) {

        sachService.changeById(maSach);
        return "redirect:/list-sach";
    }

    @GetMapping("/thongke-sach")
    public String THONGKE(Model model) {
        model.addAttribute("listTk",sachService.findSachBySoLuong(5));
        return "admin/thongke/thongke_sach";
    }
    @GetMapping("/add-sach/{maSach}")
    public String NhapThem(@PathVariable("maSach") Long maSach, Model model) {
        Count count=new Count();
        count.setMaSach(maSach);
        Sach sach=sachService.findSachById(maSach);
        model.addAttribute("count", count);
        System.out.println(count);
        return "admin/sach/import_count";
    }
    @PostMapping("/nhapthem")
    public String UpdateNhap(@ModelAttribute("count") Count count){
        Sach sach= sachService.findSachById(count.getMaSach());
        System.out.println(count.getSoLuong());
        sach.setSoLuongCon(sach.getSoLuongCon()+count.getSoLuong());
        sachService.updateSach(sach);
        return "redirect:/list-sach";
    }
    @PostMapping("/add-sach/{maSach}")
    public String NhapThem(@ModelAttribute("sach") Sach sach) {
        sach.setTenSach(sach.getTenSach().trim());
        sach.setTacGia(sach.getTacGia().trim());
        sach.setNXB(sach.getNXB().trim());
        sach.setDaMuon(sach.getDaMuon());
        sach.setSoLuongCon(sach.getSoLuongCon());
        sach.setCheckSach(true);
        sach.setNamXB(sach.getNamXB());
        sachService.addSach(sach);
        return "redirect:/list-sach";
    }
    @GetMapping("/thongke")
    public String MuonNhieu(Model model) {

        model.addAttribute("listmuonnhieu", sachService.findByDaMuon());
        return "admin/thongke/muonnhieu_sach";
    }}
