package com.dtu.trading_server.service;

import com.dtu.trading_server.entity.Order;
import com.dtu.trading_server.entity.User;
import com.dtu.trading_server.entity.Wallet;

public interface WalletService {
    Wallet getUserWallet(User user) throws Exception;

    Wallet addBalance(Wallet wallet, Long amount) throws Exception;

    Wallet findById(Long id) throws Exception;

    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception;

    Wallet payOrderPayment(Order order, User user) throws Exception;
}
