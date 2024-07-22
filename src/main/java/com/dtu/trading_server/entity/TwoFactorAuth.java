package com.dtu.trading_server.entity;

import com.dtu.trading_server.domain.VerificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class TwoFactorAuth {
    @Column(name = "is_enable")
    private boolean isEnable = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "send_to")
    private VerificationType sendTo;
}
