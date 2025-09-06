package com.ihab.e_commerce.service.media;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ihab.e_commerce.data.model.Media;
import com.ihab.e_commerce.data.repo.MediaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MediaService {

    private final Cloudinary cloudinary;

    private final MediaRepo mediaRepo;

    public Map uploadImage(MultipartFile file) throws IOException {

        String contentType = file.getContentType();

        if (contentType == null || !contentType.startsWith("image/")){
            throw new IllegalArgumentException("File must be image");
        }
        var uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "public_id", "images/" + UUID.randomUUID().toString(),
                        "folder", "ecommerce/product"
                ));

        return uploadResult;
    }

    public Map uploadVideo(MultipartFile file) throws IOException {

        String contentType = file.getContentType();

        if (contentType == null || !contentType.startsWith("video/")){
            throw new IllegalArgumentException("File must be video");
        }
        var uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "video",
                        "public_id", "videos/" + UUID.randomUUID().toString(),
                        "folder", "ecommerce/product",
                        "duration", "15"
                ));

        return uploadResult;
    }

    public Map deleteFile(String publicId) throws IOException{
        var deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        return deleteResult;
    }

}
