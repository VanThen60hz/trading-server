package com.dtu.trading_server.service;

import com.dtu.trading_server.domain.PaymentMethod;
import com.dtu.trading_server.entity.PaymentOrder;
import com.dtu.trading_server.entity.User;
import com.dtu.trading_server.response.PaymentResponse;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentOrder createOrder(User user, Long amount,
                             PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    Boolean processPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;

    PaymentResponse createRazorpayPaymentLink(User user, Long amount) throws RazorpayException;

    PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;
}
