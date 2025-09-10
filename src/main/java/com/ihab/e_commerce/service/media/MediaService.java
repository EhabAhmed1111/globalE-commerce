package com.ihab.e_commerce.service.media;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ihab.e_commerce.data.dto.MediaDto;
import com.ihab.e_commerce.data.mapper.MediaMapper;
import com.ihab.e_commerce.data.model.Media;
import com.ihab.e_commerce.data.model.Product;
import com.ihab.e_commerce.data.repo.MediaRepo;
import com.ihab.e_commerce.data.repo.ProductRepo;
import com.ihab.e_commerce.exception.GlobalNotFoundException;
import com.ihab.e_commerce.service.product.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MediaService {

    private final Cloudinary cloudinary;
    private final MediaRepo mediaRepo;
    private final ProductService productService;
    private final MediaMapper mediaMapper;

    // Should I get make download method???

    // Maybe we change class response name
    public List<MediaDto> uploadImage(List<MultipartFile> files, Long productId)  {
        validateImageFile(files);
        // TODO(Change this method in product)
        Product product = productService.getProductForOthersClasses(productId);

        String uniqueId = UUID.randomUUID().toString();
        var option = ObjectUtils.asMap(
                "public_id", "products/images/" + uniqueId,
                "folder", "ecommerce",
                "use_filename", true,
                "unique_filename", false,
                "overwrite", false,
                "resource_type", "image"
        );
        List<MediaDto> mediaDto = files.stream().map(
                file -> {

                    Map uploadResult = null;
                    try {
                        uploadResult = cloudinary.uploader().upload(file.getBytes(), option);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Media media = Media.builder()
                            .fileType((String) uploadResult.get("resource_type"))
                            .fileName(file.getOriginalFilename())
                            .cloudinaryPublicId((String) uploadResult.get("public_id"))
                            .url((String) uploadResult.get("secure_url"))
                            .product(product)
                            .build();
                    return mediaMapper.fromMediaToDto(media);
                }
        ).collect(Collectors.toList());

        return mediaDto;
    }

    private void validateImageFile(List<MultipartFile> files) {
        files.stream().map(
                file->{
                    if (file.isEmpty())
                        throw new IllegalArgumentException("File can not be empty");

                    String contentType = file.getContentType();
                    if (contentType == null || !contentType.startsWith("image/"))
                        throw new IllegalArgumentException("File must be an image");

                    // Image must be 5MB
                    if (file.getSize() > 5 * 1024 * 1024)
                        throw new IllegalArgumentException("Image size must be less than 5MB");
                    return file;
                }
        );


    }
    public MediaDto uploadVideo(MultipartFile file, Long productId)  {

        validateVideoFile(file);
        Product product = productService.getProductForOthersClasses(productId);


        String uniqueId = UUID.randomUUID().toString();
        var option = ObjectUtils.asMap(
                "public_id", "products/videos/" + uniqueId,
                "folder", "ecommerce",
                "use_filename", true,
                "unique_filename", false,
                "overwrite", false,
                "resource_type", "video"
        );

        Map uploadResult = null;

        try {
            uploadResult = cloudinary.uploader().upload(file.getBytes(), option);
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }

        Media media = Media.builder()
                .fileType((String) uploadResult.get("resource_type"))
                .fileName(file.getOriginalFilename())
                .cloudinaryPublicId((String) uploadResult.get("public_id"))
                .url((String) uploadResult.get("secure_url"))
                .product(product)
                .build();

        return mediaMapper.fromMediaToDto(media);
    }

    private void validateVideoFile(MultipartFile file) {
        if (file.isEmpty())
            throw new IllegalArgumentException("File can not be empty");

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("video/"))
            throw new IllegalArgumentException("File must be an video");
    }

    public void deleteFile(String publicId)  {
        try {
            Media media = mediaRepo.findByCloudinaryPublicId(publicId).orElseThrow(()-> new GlobalNotFoundException("There is no media with this public key: " + publicId));
            cloudinary.uploader().destroy(media.getCloudinaryPublicId(), ObjectUtils.asMap(
                    "resource_type", media.getFileType()
            ));
            mediaRepo.delete(media);
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }

    }

}
