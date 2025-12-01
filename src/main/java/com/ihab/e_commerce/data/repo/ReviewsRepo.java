package com.ihab.e_commerce.data.repo;

import com.ihab.e_commerce.data.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsRepo extends JpaRepository<Reviews, Long> {
}
