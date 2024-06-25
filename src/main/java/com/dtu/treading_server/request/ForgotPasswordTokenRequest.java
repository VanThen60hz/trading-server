package com.dtu.treading_server.request;

import com.dtu.treading_server.domain.VerificationType;
import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {
    private String sendTo;
    private VerificationType verificationType;
}
