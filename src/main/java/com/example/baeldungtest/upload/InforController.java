package com.example.baeldungtest.upload;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class InforController {
    @GetMapping("/infor")
        public String register(){
            return "/views/upload";
        }
@PostMapping("/infor/upload")
    public String save(@RequestParam("name") String name,
                       @RequestParam ("photo")MultipartFile photo,
                       Model model){
                       Infor infor= new Infor();
                       infor.setName(name.trim());
                       if(photo.isEmpty()){
                           return "/views/upload";
                       }
    Path path = Paths.get("uploads/");
                       try{
                           InputStream inputStream= photo.getInputStream();
                           Files.copy(inputStream,path.resolve(photo.getOriginalFilename()),
                                   StandardCopyOption.REPLACE_EXISTING);
                           infor.setPhoto(photo.getOriginalFilename().toLowerCase());
                           model.addAttribute("INFOR",infor);
                       } catch (IOException e) {
                           throw new RuntimeException(e);
                       }
    return "/views/view-info";
    }
}
