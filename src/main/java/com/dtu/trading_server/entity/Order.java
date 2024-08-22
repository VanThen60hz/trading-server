package com.dtu.trading_server.entity;

import com.dtu.trading_server.domain.OrderStatus;
import com.dtu.trading_server.domain.OrderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated
    @Column(name = "order_type", nullable = false)
    private OrderType orderType;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Enumerated
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderItem orderItem;
}