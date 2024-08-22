package com.dtu.trading_server.domain;

public enum OrderStatus {
    PENDING,
    FILED,
    CANCELLED,
    PARTIALLY_FILLED,
    ERROR,
    SUCCESS
}
