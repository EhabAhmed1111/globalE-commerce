package com.ihab.e_commerce.data.mapper;

import com.ihab.e_commerce.data.dto.MediaDto;
import com.ihab.e_commerce.data.model.Media;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MediaMapper {

    // If we need to make the reverse we come here to create one
    public MediaDto fromMediaToDto(Media media){
        return MediaDto.builder()
                .url(media.getUrl())
                .cloudinaryPublicId(media.getCloudinaryPublicId())
                .fileType(media.getFileType())
                .uploadedAt(media.getUploadedAt())
                .build();
    }
    public List<MediaDto> fromListOfMediaToListOfDto(List<Media> medias){
       return medias.stream().map(
               media -> MediaDto.builder()
                      .url(media.getUrl())
                      .cloudinaryPublicId(media.getCloudinaryPublicId())
                      .fileType(media.getFileType())
                      .uploadedAt(media.getUploadedAt())
                      .build()
       ).collect(Collectors.toList());
    }
}
