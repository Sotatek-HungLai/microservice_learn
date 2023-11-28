package com.example.ms_product_service.services;

import com.cloudinary.Cloudinary;
import com.example.ms_product_service.exceptions.CloudinaryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public Map upload(MultipartFile file) {
        try {
            return this.cloudinary.uploader().upload(file.getBytes(), Map.of(
                    "folder", "products",
                    "use_filename", "true",
                    "unique_filename", "true",
                    "resource_type", "image"
            ));
        } catch (IOException io) {
            throw new CloudinaryException(io.getMessage());
        }
    }

    public void delete(String publicId) {
        try {
            log.info("publicId : {}", publicId);
            this.cloudinary.uploader().destroy(publicId, Map.of(
                    "resource_type", "image"
            ));
        } catch (IOException io) {
            throw new CloudinaryException(io.getMessage());
        }
    }
}
