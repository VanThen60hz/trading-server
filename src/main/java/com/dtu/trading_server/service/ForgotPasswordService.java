package com.dtu.trading_server.service;

import com.dtu.trading_server.domain.VerificationType;
import com.dtu.trading_server.entity.ForgotPasswordToken;
import com.dtu.trading_server.entity.User;

public interface ForgotPasswordService {
    ForgotPasswordToken createForgotPasswordToken(User user, String id, String otp, VerificationType verificationType, String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);
}
