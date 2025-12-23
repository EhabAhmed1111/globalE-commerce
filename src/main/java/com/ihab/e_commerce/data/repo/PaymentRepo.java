package com.ihab.e_commerce.data.repo;

import com.ihab.e_commerce.data.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
}
