package com.dtu.trading_server.controller;

import com.dtu.trading_server.entity.User;
import com.dtu.trading_server.entity.Wallet;
import com.dtu.trading_server.entity.WalletTransaction;
import com.dtu.trading_server.entity.Withdrawal;
import com.dtu.trading_server.service.UserService;
import com.dtu.trading_server.service.WalletService;
import com.dtu.trading_server.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class WithdrawalController {
    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private WalletTranstionService walletTranstionService;

    @PostMapping("/api/withdrawal/{amount}")
    public ResponseEntity<?> withdrawalRequest(
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findProfileByJwt(jwt);
        Wallet userWallet = walletService.getUserWallet(user);

        Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
        walletService.addBalance(userWallet, -withdrawal.getAmount());

//        WalletTransaction walletTransaction = walletService.createTransaction(
//                userWallet,
//                walletTransactionType.WITHDRAWAL,
//                null,
//                "bank account withdrawal",
//                withdrawal.getAmount()
//        );

        return ResponseEntity.ok(withdrawal);
    }

    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(
            @PathVariable Long id,
            @PathVariable boolean accept,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findProfileByJwt(jwt);

        Withdrawal withdrawal = withdrawalService.proceedWithdrawal(id, accept);

        Wallet userWallet = walletService.getUserWallet(user);
        if (!accept) {
            walletService.addBalance(userWallet, withdrawal.getAmount());
        }

        return ResponseEntity.ok(withdrawal);
    }


    @GetMapping("/api/withdrawal")
    public ResponseEntity<?> getWithdrawalHistory(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findProfileByJwt(jwt);
        List<Withdrawal> withdrawalList = withdrawalService.getUsersWithdrawalHistory(user);

        return ResponseEntity.ok(withdrawalList);
    }

    @GetMapping("/api/admin/withdrawal")
    public ResponseEntity<?> getAllWithdrawalRequest(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findProfileByJwt(jwt);
        List<Withdrawal> withdrawalList = withdrawalService.getAllWithdrawalRequest();

        return ResponseEntity.ok(withdrawalList);
    }
}
