package com.dtu.treading_server.repository;

import com.dtu.treading_server.entity.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String>{
    TwoFactorOTP findByUserId(Long userId);
}
