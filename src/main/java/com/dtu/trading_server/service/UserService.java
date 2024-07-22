package com.dtu.trading_server.service;

import com.dtu.trading_server.domain.VerificationType;
import com.dtu.trading_server.entity.User;

public interface UserService {
    User findProfileByJwt(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;

    User findUserById(Long userId) throws Exception;

    User enableTwoFactorAuthentication(VerificationType verificationType,
                                       String sendTo,
                                       User user);

    User updatePassword(User user, String newPassword);
}
