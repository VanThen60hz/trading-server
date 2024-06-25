package com.dtu.treading_server.service.impl;

import com.dtu.treading_server.domain.VerificationType;
import com.dtu.treading_server.entity.ForgotPasswordToken;
import com.dtu.treading_server.entity.User;
import com.dtu.treading_server.repository.ForgotPasswordRepository;
import com.dtu.treading_server.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Override
    public ForgotPasswordToken createForgotPasswordToken(User user, String id,
                                                         String otp, VerificationType verificationType,
                                                         String sendTo) {
        ForgotPasswordToken token = new ForgotPasswordToken();
        token.setUser(user);
        token.setSendTo(sendTo);
        token.setVerificationType(verificationType);
        token.setOtp(otp);
        token.setId(id);

        return forgotPasswordRepository.save(token);
    }

    @Override
    public ForgotPasswordToken findById(String id) {
        Optional<ForgotPasswordToken> token = forgotPasswordRepository.findById(id);

        return token.orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUser(Long userId) {
        return forgotPasswordRepository.findByUserId(userId);
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {
        forgotPasswordRepository.delete(token);
    }
}
