package com.dtu.trading_server.service;

import com.dtu.trading_server.domain.VerificationType;
import com.dtu.trading_server.entity.User;
import com.dtu.trading_server.entity.VerificationCode;

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
