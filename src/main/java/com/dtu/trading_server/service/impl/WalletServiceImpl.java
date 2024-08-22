package com.dtu.trading_server.service.impl;

import com.dtu.trading_server.domain.OrderType;
import com.dtu.trading_server.entity.Order;
import com.dtu.trading_server.entity.User;
import com.dtu.trading_server.entity.Wallet;
import com.dtu.trading_server.repository.WalletRepository;
import com.dtu.trading_server.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Override
    public Wallet getUserWallet(User user) throws Exception {
        Wallet wallet = walletRepository.findByUserId(user.getId());
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setUser(user);

        }
        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long amount) throws Exception {
        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance = balance.add(BigDecimal.valueOf(amount));

        wallet.setBalance(newBalance);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findById(Long id) throws Exception {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if (wallet.isPresent()) {
            return wallet.get();
        }
        throw new Exception("Wallet not found");
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception {
        Wallet senderWallet = getUserWallet(sender);

        if (senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new Exception("Not enough balance");
        }

        BigDecimal senderBalance = senderWallet
                .getBalance()
                .subtract(BigDecimal.valueOf(amount));
        senderWallet.setBalance(senderBalance);
        walletRepository.save(senderWallet);

        BigDecimal receiverBalance = receiverWallet
                .getBalance()
                .add(BigDecimal.valueOf(amount));

        receiverWallet.setBalance(receiverBalance);
        walletRepository.save(receiverWallet);
        return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet = getUserWallet(user);

        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance;
        if (order.getOrderType().equals(OrderType.BUY)) {
            newBalance = balance.subtract(order.getPrice());
            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new Exception("Insufficient funds for this transaction");
            }
        } else {
            newBalance = balance.add(order.getPrice());
        }
        wallet.setBalance(newBalance);

        walletRepository.save(wallet);
        return wallet;
    }
}
