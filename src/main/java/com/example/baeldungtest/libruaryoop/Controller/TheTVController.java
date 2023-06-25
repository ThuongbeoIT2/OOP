package com.example.baeldungtest.libruaryoop.Controller;

import com.example.baeldungtest.libruaryoop.Model.DocGia;
import com.example.baeldungtest.libruaryoop.Model.TheTV;
import com.example.baeldungtest.libruaryoop.Service.DocGiaService;
import com.example.baeldungtest.libruaryoop.Service.TheTVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class TheTVController {
    @Autowired
    private TheTVService theTVService;
    @Autowired
    private DocGiaService docGiaService;
    @GetMapping("/list-thetv")
    public String index(Model model, HttpSession session) {


        model.addAttribute("listTrue", theTVService.findAllCheckTrue());
        return "admin/thetv/list_thetv";
    }
    @GetMapping("/luutru-thetv")
    public String Luutru(Model model,HttpSession session) {


        model.addAttribute("listFalse", theTVService.findAllCheckFalse());
        return "admin/thetv/luutru-thetv";
    }

    @GetMapping("/add-thetv")
    public String addTTV(Model model,HttpSession session) {


        model.addAttribute("listDocGia",docGiaService.findAllCheckFalse());
        model.addAttribute("thetv", new TheTV());

        return "admin/thetv/add_thetv";
    }
    @PostMapping("/add-thetv")
    public String addTTV(@ModelAttribute("thetv") TheTV theTV) {
        theTV.setDocGia(theTV.getDocGia());
        DocGia docGia=docGiaService.findDocGiaById(theTV.getDocGia().getMaDocGia());
        docGia.setCheckDocGia(true);
        theTV.setNgayBatDau(theTV.getNgayBatDau());
        theTV.setNgayHetHan(theTV.getNgayHetHan());
        theTV.setPhieuMuon(theTV.getPhieuMuon());
        theTV.setCheckTTV(true);
        theTVService.addTTV(theTV);
        return "redirect:/list-thetv";
    }

//

    @GetMapping("/edit-thetv/{soThe}")
    public String editTTV(Model model, @PathVariable Long soThe,HttpSession session) {

        TheTV theTV = theTVService.findTTVById(soThe);
        System.out.println(soThe);
        model.addAttribute("thetv", theTV);
        return "admin/thetv/edit_thetv";
    }

    @PostMapping("/update-thetv")
    public String updateTTV(@ModelAttribute("thetv") TheTV theTV) {
        theTVService.updateTTV(theTV);
        System.out.println(theTV);
        return "redirect:/list-thetv";
    }

    @GetMapping("/delete-thetv/{soThe}")
    public String deleteById(@PathVariable("soThe") Long soThe,HttpSession session) {

        theTVService.deleteById(soThe);
        return "redirect:/list-thetv";
    }
    @GetMapping("/change-thetv/{soThe}")
    public String changeById(@PathVariable("soThe") Long soThe,HttpSession session) {
        theTVService.changeById(soThe);
        return "redirect:/list-thetv";
    }
}
