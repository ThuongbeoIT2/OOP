package com.example.libruaryoop.Controller;

import com.example.libruaryoop.Model.Search;
import com.example.libruaryoop.Service.NhanSuService;
import com.example.libruaryoop.Service.SachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class SearchController {
    @Autowired
    private NhanSuService nhanSuService;
    @Autowired
    private SachService sachService;

    @GetMapping("/list-search")
    public String SearchNhanSu(Model model) {
       model.addAttribute("listSach",sachService.findAllCheckTrue());
        model.addAttribute("key",new Search());
        return "admin/search";
    }
    @PostMapping("/Search")
    public String SearchSach(@ModelAttribute("key") Search search, Model model){
        System.out.println(search.getKey());
        model.addAttribute("listSearchSach",sachService.searchPro(search.getKey()));
        return "admin/search";
    }


}
