package com.dtu.treading_server.service;

import com.dtu.treading_server.domain.VerificationType;
import com.dtu.treading_server.entity.User;

public interface UserService {
    User findProfileByJwt(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;

    User findUserById(Long userId) throws Exception;

    User enableTwoFactorAuthentication(VerificationType verificationType,
                                       String sendTo,
                                       User user);

    User updatePassword(User user, String newPassword);
}
