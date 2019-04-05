package com.softuni.my_book.service;

import com.cloudinary.Cloudinary;
import com.softuni.my_book.service.contracts.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        File fileToUpload = File.createTempFile("temp-file", file.getOriginalFilename());
        file.transferTo(fileToUpload);
        Map upload = this.cloudinary.uploader().upload(fileToUpload, new HashMap());

        return (String) upload.get("url");
    }

    @Override
    public String deleteImage(String id) {
        return null;
    }
}
