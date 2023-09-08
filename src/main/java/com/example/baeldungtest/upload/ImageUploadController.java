package com.example.baeldungtest.upload;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageUploadController {
    @GetMapping(value = "/getimage/{photo}")
    @ResponseBody
    ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo) {
        if (!photo.equals("") || photo != null){
           try {
               Path filename = Paths.get("uploads",photo);
               byte[] buffer = Files.readAllBytes(filename);
               ByteArrayResource byteArrayResource= new ByteArrayResource(buffer);
               return ResponseEntity.ok().contentLength(buffer.length)
                       .contentType(MediaType.parseMediaType("image/png"))
                       .body(byteArrayResource);
           } catch (Exception e){
               System.out.println("Error");
           }
        }
            return ResponseEntity.badRequest().build();
    }
}
