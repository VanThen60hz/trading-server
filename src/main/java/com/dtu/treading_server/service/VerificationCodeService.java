package com.dtu.treading_server.service;

import com.dtu.treading_server.domain.VerificationType;
import com.dtu.treading_server.entity.User;
import com.dtu.treading_server.entity.VerificationCode;

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
