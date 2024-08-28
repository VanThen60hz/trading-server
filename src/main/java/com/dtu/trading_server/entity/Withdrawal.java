package com.dtu.trading_server.entity;

import com.dtu.trading_server.domain.WithdrawalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "withdrawal")
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated
    @Column(name = "status")
    private WithdrawalStatus status;

    @Column(name = "amount")
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime date = LocalDateTime.now();
}