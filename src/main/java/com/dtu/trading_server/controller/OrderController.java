package com.dtu.trading_server.controller;

import com.dtu.trading_server.domain.OrderType;
import com.dtu.trading_server.entity.Coin;
import com.dtu.trading_server.entity.Order;
import com.dtu.trading_server.entity.User;
import com.dtu.trading_server.entity.WalletTransaction;
import com.dtu.trading_server.request.CreateOrderRequest;
import com.dtu.trading_server.service.CoinService;
import com.dtu.trading_server.service.OrderService;
import com.dtu.trading_server.service.UserService;
import com.dtu.trading_server.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CoinService coinService;
    @Autowired
    private WalletService walletService;

//    @Autowired
//    private WalletTransactionService walletTransactionService;

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @RequestBody CreateOrderRequest req
    ) throws Exception {
        User user = userService.findProfileByJwt(jwt);
        Coin coin = coinService.findById(req.getCoinId());

        Order order = orderService.processOrder(coin, req.getQuantity(), req.getOrderType(), user);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable Long orderId
    ) throws Exception {
        User user = userService.findProfileByJwt(jwtToken);

        Order order = orderService.getOrderById(orderId);
        if (order.getUser().getId().equals(user.getId())) {
            return ResponseEntity.ok(order);
        } else {
            throw new Exception("You don't have permission to view this order");
        }
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrdersOfUser(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) OrderType order_type,
            @RequestParam(required = false) String asset_symbol
    ) throws Exception {
        Long userId = userService.findProfileByJwt(jwt).getId();

        return ResponseEntity.ok(orderService.getAllOrdersOfUser(userId, order_type, asset_symbol));
    }
}
