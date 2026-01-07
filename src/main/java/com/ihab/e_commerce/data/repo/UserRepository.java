package com.ihab.e_commerce.data.repo;

import com.ihab.e_commerce.data.enums.Role;
import com.ihab.e_commerce.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    List<User> findAllByRole(Role role);
}
