package com.dtu.treading_server.repository;

import com.dtu.treading_server.entity.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, String> {

    ForgotPasswordToken findByUserId(Long userId);
}
