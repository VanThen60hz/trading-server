package com.dtu.trading_server.entity;

import com.dtu.trading_server.domain.PaymentMethod;
import com.dtu.trading_server.domain.PaymentOrderStatus;
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
@Table(name = "payment_order")
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @Enumerated
    @Column(name = "status")
    private PaymentOrderStatus status;

    @Enumerated
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}