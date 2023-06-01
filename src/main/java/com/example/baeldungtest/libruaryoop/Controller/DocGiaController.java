package com.example.libruaryoop.Controller;

import com.example.libruaryoop.Model.DocGia;
import com.example.libruaryoop.Service.DocGiaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DocGiaController {
    @Autowired
    private DocGiaService docGiaService;
    @GetMapping("/list-docgia")
    public String index(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("listTrue", docGiaService.findAllCheckTrue());
        return "admin/docgia/list_docgia";
    }
    @GetMapping("/capthe-docgia")
    public String CapThe(Model model,HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("listFalse", docGiaService.findAllCheckFalse());
        return "admin/docgia/capthe_docgia";
    }
    @GetMapping("/add-docgia")
    public String addDocGia(Model model,HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("docGia", new DocGia());

        return "admin/docgia/add_docgia";
    }
    @PostMapping("/add-docgia")
    public String addUser( @ModelAttribute("docGia") DocGia docGia, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        docGia.setTenDocGia(docGia.getTenDocGia());
        docGia.setSoDienThoai(docGia.getSoDienThoai());
        docGia.setCCCD(docGia.getCCCD());
        docGia.setEmail(docGia.getEmail());;
        docGia.setCheckDocGia(false);
        docGiaService.addDocGia(docGia);
        return "redirect:/list-docgia";
    }

//

    @GetMapping("/edit-docgia/{maDocGia}")
    public String editDocGia(Model model, @PathVariable Long maDocGia,HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        DocGia docGia = docGiaService.findDocGiaById(maDocGia);
        System.out.println(docGia);
        model.addAttribute("docGia", docGia);
        return "admin/docgia/edit_docgia";
    }

    @PostMapping("/update-docgia")
    public String editSupplier(@ModelAttribute("docGia") DocGia docGia,HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        docGiaService.updateDocGia(docGia);
        System.out.println(docGia);
        return "redirect:/list-docgia";
    }

    @GetMapping("/delete-docgia/{maDocGia}")
    public String deleteUserById(@PathVariable("maDocGia") Long maDocGia,HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        docGiaService.deleteById(maDocGia);
        return "redirect:/list-docgia";
    }
    @GetMapping("/change-docgia/{maDocGia}")
    public String changeUserById(@PathVariable("maDocGia") Long maDocGia,HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        docGiaService.changeById(maDocGia);
        return "redirect:/list-docgia";
    }
    /////
}
