package com.dtu.trading_server.controller;

import com.dtu.trading_server.entity.Order;
import com.dtu.trading_server.entity.User;
import com.dtu.trading_server.entity.Wallet;
import com.dtu.trading_server.entity.WalletTransaction;
import com.dtu.trading_server.service.UserService;
import com.dtu.trading_server.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long walletId,
            @RequestBody WalletTransaction req) throws Exception {
        User senderUser = userService.findProfileByJwt(jwt);
        Wallet receiverWallet = walletService.findById(walletId);

        Wallet wallet = walletService.walletToWalletTransfer(
                senderUser, receiverWallet,
                req.getAmount());

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

//    @PutMapping("/order/{orderId}/pay")
//    public ResponseEntity<Wallet> payOrderPayment(
//            @RequestHeader("Authorization") String jwt,
//            @PathVariable Long orderId
//    ) throws Exception {
//        User user = userService.findProfileByJwt(jwt);
//        Order order = orderService.findById(orderId);
//
//        Wallet wallet = walletService.payOrderPayment(order, user);
//
//        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
//    }
}
