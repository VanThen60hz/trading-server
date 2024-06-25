package com.dtu.treading_server.controller;

import com.dtu.treading_server.config.JwtProvider;
import com.dtu.treading_server.domain.VerificationType;
import com.dtu.treading_server.entity.ForgotPasswordToken;
import com.dtu.treading_server.entity.TwoFactorOTP;
import com.dtu.treading_server.entity.User;
import com.dtu.treading_server.entity.VerificationCode;
import com.dtu.treading_server.repository.UserRepository;
import com.dtu.treading_server.request.ForgotPasswordTokenRequest;
import com.dtu.treading_server.request.ResetPasswordRequest;
import com.dtu.treading_server.response.ApiResponse;
import com.dtu.treading_server.response.AuthResponse;
import com.dtu.treading_server.service.*;
import com.dtu.treading_server.utils.OtpUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {
        User isUserExist = userRepository.findByEmail(user.getEmail());
        if (isUserExist != null) {
            throw new Exception("email is already used with another account");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFullName(user.getFullName());

        User savedUser = userRepository.save(newUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setStatus(true);
        authResponse.setMessage("User registered successfully");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws MessagingException {
        String username = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticated(username, password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);

        User authUser = userRepository.findByEmail(username);

        if (user.getTwoFactorAuth().isEnable()) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Two factor authentication is enabled!");
            authResponse.setTwoFactorAuthEnabled(true);

            String otp = OtpUtils.generateOtp();

            TwoFactorOTP oldTwoFactorOtp = twoFactorOtpService.findByUser(authUser.getId());
            if (oldTwoFactorOtp != null) {
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOtp);
            }

            TwoFactorOTP newTwoFactorOtp = twoFactorOtpService.createTwoFactorOtp(authUser, otp, jwt);

            emailService.sendVerificationOtpEmail(username, otp);

            authResponse.setSession(newTwoFactorOtp.getId());
            return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setStatus(true);
        authResponse.setMessage("User login successfully");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("two-factor-auth/verify-otp/{otp}")
    public ResponseEntity<AuthResponse> verifySignInOtp(
            @PathVariable String otp,
            @RequestParam String id) throws Exception {

        TwoFactorOTP twoFactorOtp = twoFactorOtpService.findById(id);

        if (twoFactorOtpService.verifyTwoFactorOtp(twoFactorOtp, otp)) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Two factor authentication is verified!");
            authResponse.setStatus(true);
            authResponse.setJwt(twoFactorOtp.getJwt());

            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        }

        throw new Exception("Invalid OTP");
    }

    @PostMapping("user/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
            @RequestBody ForgotPasswordTokenRequest req) throws Exception {

        User user = userService.findUserByEmail(req.getSendTo());
        String otp = OtpUtils.generateOtp();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());

        if (token == null) {
            token = forgotPasswordService.createForgotPasswordToken(
                    user, id, otp,
                    req.getVerificationType(),
                    req.getSendTo()
            );
        }

        if (req.getVerificationType().equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOtpEmail(
                    user.getEmail(),
                    token.getOtp()
            );
        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setSession(token.getId());
        authResponse.setMessage("Password reset OTP sent successfully");

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PatchMapping("/user/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestParam String id,
            @RequestBody ResetPasswordRequest req) throws Exception {

        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);

        boolean isVerified = forgotPasswordToken.getOtp().equals(req.getOtp());

        if (isVerified) {
            userService.updatePassword(forgotPasswordToken.getUser(), req.getPassword());
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("Password reset successfully");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
        throw new Exception("Wrong OTP");
    }

    private Authentication authenticated(String username, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }

        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
