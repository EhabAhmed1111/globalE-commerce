package com.ihab.e_commerce.rest.controller.media;


import com.ihab.e_commerce.data.model.Product;
import com.ihab.e_commerce.rest.response.GlobalSuccessResponse;
import com.ihab.e_commerce.data.dto.MediaDto;
import com.ihab.e_commerce.service.media.MediaService;
import com.ihab.e_commerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/medias")
public class MediaController {

    // todo change the service from here to service layer
    // todo there will be only one service which will be mediaService

    private final MediaService mediaService;
    private final ProductService productService;

    /* upload cover image */
    @PostMapping("/image/{id}/cover")
    public ResponseEntity<GlobalSuccessResponse> uploadProductCover(
            @RequestParam("file") MultipartFile file,
            @PathVariable Long id){
        Product product = productService.getProductForOthersClasses(id);
        MediaDto mediaDto = mediaService.uploadCoverImage(file, product);
        return ResponseEntity.ok(new GlobalSuccessResponse("Cover Image uploaded successfully", mediaDto));
    }

    @GetMapping("/image/{id}/cover")
    public ResponseEntity<GlobalSuccessResponse> uploadProductCover(@PathVariable Long id){
        Product product = productService.getProductForOthersClasses(id);
        MediaDto mediaDto = mediaService.getCoverImage(product);
        return ResponseEntity.ok(new GlobalSuccessResponse("Cover Image fetched successfully", mediaDto));
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<GlobalSuccessResponse> uploadProductImages(
            @RequestParam("files") List<MultipartFile> files,
            @PathVariable Long id){
        Product product = productService.getProductForOthersClasses(id);
        List<MediaDto> mediaDto = mediaService.uploadImage(files, product);
        return ResponseEntity.ok(new GlobalSuccessResponse("Image uploaded successfully", mediaDto));
    }

    @PostMapping("/{id}/video")
    public ResponseEntity<GlobalSuccessResponse> uploadProductVideo(
            @RequestParam MultipartFile file,
            @PathVariable Long id){
        Product product = productService.getProductForOthersClasses(id);
        MediaDto mediaDto = mediaService.uploadVideo(file, product);
        return ResponseEntity.ok(new GlobalSuccessResponse("Video uploaded successfully", mediaDto));
    }

    @DeleteMapping("{mediaId}")
    public ResponseEntity<GlobalSuccessResponse> deleteFile(
            @PathVariable String mediaId){
        mediaService.deleteFile(mediaId);
        return ResponseEntity.ok(new GlobalSuccessResponse("FIle deleted successfully", null));
    }
}
