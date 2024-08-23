package com.dtu.trading_server.service;

import com.dtu.trading_server.domain.OrderType;
import com.dtu.trading_server.entity.Coin;
import com.dtu.trading_server.entity.Order;
import com.dtu.trading_server.entity.OrderItem;
import com.dtu.trading_server.entity.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderItem orderItem, OrderType orderType) throws Exception;

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) throws Exception;

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;
}
