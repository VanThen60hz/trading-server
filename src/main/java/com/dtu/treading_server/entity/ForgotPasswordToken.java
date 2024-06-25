package com.dtu.treading_server.entity;

import com.dtu.treading_server.domain.VerificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "forgot_password_token")
public class ForgotPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private String id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "otp")
    private String otp;

    @Column(name = "verification_type")
    private VerificationType verificationType;

    @Column(name = "send_to")
    private String sendTo;

}