package com.dtu.trading_server.service;

import com.dtu.trading_server.entity.PaymentDetails;
import com.dtu.trading_server.entity.User;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber,
                                            String accountHolderName,
                                            String ifsc,
                                            String bankName,
                                            User user) throws Exception;

    public PaymentDetails getUsersPaymentDetails(User user) throws Exception;


}
