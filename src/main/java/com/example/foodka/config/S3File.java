package com.example.foodka.config;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
public class S3File {

    @Value("${bucketName}")
    private String bucket_name;
    @Autowired
    private AmazonS3 amazon;

    @Transactional
    public String postFile(MultipartFile multipartFile) {
        String name = String.valueOf(UUID.randomUUID());
        int ext = Objects.requireNonNull(multipartFile.getOriginalFilename()).lastIndexOf(".");
        name += multipartFile.getOriginalFilename().substring(ext);
        final  String link;
        File file = null;
        try {
            file = convertMultipartToFile(multipartFile,name);
            amazon.putObject(bucket_name,name,file);
            file.delete();
            link = "https://" + amazon.getUrl(bucket_name,name).getHost()+amazon.getUrl(bucket_name,name).getFile();
        } catch (IOException e) {
            return "DATABASE_ERROR : " + e.getMessage();
        }
        return link;
    }

    @Transactional
    public boolean deleteFile(String name) {
        try{
            amazon.deleteObject(bucket_name, name.substring(name.lastIndexOf("/") + 1));
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private File convertMultipartToFile(MultipartFile multipartFile,String name) throws IOException {
        File file = new File(name);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }


}