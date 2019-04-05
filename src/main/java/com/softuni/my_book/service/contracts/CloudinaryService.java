package com.softuni.my_book.service.contracts;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
    String uploadImage(MultipartFile file) throws IOException;

    String deleteImage(String id);
}
