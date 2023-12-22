package com.example.foodka.rest;

import com.example.foodka.config.S3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileResources {
    @Autowired
    private S3File s3File;

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
    public String upload(@ModelAttribute MultipartFile file){
        return s3File.postFile(file);
    }

}
