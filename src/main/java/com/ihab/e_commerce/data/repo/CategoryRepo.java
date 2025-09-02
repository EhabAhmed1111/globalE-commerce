package com.ihab.e_commerce.data.repo;

import com.ihab.e_commerce.data.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    public Optional<Category> findByName(String name);
}
