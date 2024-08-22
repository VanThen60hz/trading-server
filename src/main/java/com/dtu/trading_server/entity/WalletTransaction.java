package com.dtu.trading_server.entity;

import com.dtu.trading_server.domain.WalletTransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallet_transaction")
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(name = "type", nullable = false)
    private WalletTransactionType type;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "transfer_id", nullable = false, length = 50)
    private String transferId;

    @Column(name = "purpose", length = 255)
    private String purpose;

    @Column(name = "amount", nullable = false)
    private Long amount;
}
