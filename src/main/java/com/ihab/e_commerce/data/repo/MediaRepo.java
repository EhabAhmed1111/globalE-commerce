package com.ihab.e_commerce.data.repo;

import com.ihab.e_commerce.data.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepo extends JpaRepository<Media, Long> {

    public List<Media> findByProductId(Long productId);

    public List<Media> findByProductIdAndFileType(Long productId, String fileType);

    Optional<Media> findByCloudinaryPublicId(String publicId);

    void deleteByCloudinaryPublicId(String publicId);


}
