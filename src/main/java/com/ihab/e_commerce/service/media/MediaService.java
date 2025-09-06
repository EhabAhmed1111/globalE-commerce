package com.ihab.e_commerce.service.media;


import com.cloudinary.Cloudinary;
import com.ihab.e_commerce.data.model.Media;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class MediaService {

    private final Cloudinary cloudinary;



}
