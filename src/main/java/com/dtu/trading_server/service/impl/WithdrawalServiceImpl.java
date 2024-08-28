package com.dtu.trading_server.service.impl;

import com.dtu.trading_server.domain.WithdrawalStatus;
import com.dtu.trading_server.entity.User;
import com.dtu.trading_server.entity.Withdrawal;
import com.dtu.trading_server.repository.WithdrawalRepository;
import com.dtu.trading_server.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {
    @Autowired
    private WithdrawalRepository withdrawalRepository;

    @Override
    public Withdrawal requestWithdrawal(Long amount, User user) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setUser(user);
        withdrawal.setStatus(WithdrawalStatus.PENDING);

        return withdrawalRepository.save(withdrawal);
    }

    @Override
    public Withdrawal proceedWithdrawal(Long withdrawalId, boolean accept) throws Exception {
        Optional<Withdrawal> optionalWithdrawal = withdrawalRepository.findById(withdrawalId);
        if (optionalWithdrawal.isEmpty()) {
            throw new Exception("Withdrawal not found");
        }

        Withdrawal withdrawal = optionalWithdrawal.get();
        withdrawal.setDate(LocalDateTime.now());
        if (accept) {
            withdrawal.setStatus(WithdrawalStatus.SUCCESS);
        } else {
            withdrawal.setStatus(WithdrawalStatus.DECLINE);
        }

        return withdrawalRepository.save(withdrawal);
    }

    @Override
    public List<Withdrawal> getUsersWithdrawalHistory(User user) {
        return withdrawalRepository.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequest() {
        return withdrawalRepository.findAll();
    }
}
