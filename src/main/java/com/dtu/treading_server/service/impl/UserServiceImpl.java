package com.dtu.treading_server.service.impl;

import com.dtu.treading_server.config.JwtProvider;
import com.dtu.treading_server.domain.VerificationType;
import com.dtu.treading_server.entity.TwoFactorAuth;
import com.dtu.treading_server.entity.User;
import com.dtu.treading_server.repository.UserRepository;
import com.dtu.treading_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new Exception("User not found");
        }
        return user.get();
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType,
                                              String sendTo,
                                              User user) {
        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.setEnable(true);
        twoFactorAuth.setSendTo(verificationType);

        user.setTwoFactorAuth(twoFactorAuth);

        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRepository.save(user);
    }
}
