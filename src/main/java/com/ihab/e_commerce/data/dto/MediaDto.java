package com.ihab.e_commerce.data.dto;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediaDto {

    private String url;
    private String fileType;
    private String cloudinaryPublicId; // Important for deletion
    private LocalDateTime uploadedAt;
}
