package com.dtu.treading_server.repository;

import com.dtu.treading_server.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    public VerificationCode findByUserId(Long userId);
}
