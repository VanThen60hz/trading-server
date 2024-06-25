package com.dtu.treading_server.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String otp;
    private String password;
}
