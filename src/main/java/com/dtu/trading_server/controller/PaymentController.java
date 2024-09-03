package com.dtu.trading_server.controller;

import com.dtu.trading_server.domain.PaymentMethod;
import com.dtu.trading_server.entity.PaymentOrder;
import com.dtu.trading_server.entity.User;
import com.dtu.trading_server.response.PaymentResponse;
import com.dtu.trading_server.service.PaymentService;
import com.dtu.trading_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{paymentMethod}/amount/amount")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findProfileByJwt(jwt);
        PaymentResponse paymentResponse;

        PaymentOrder paymentOrder = paymentService.createOrder(user, amount, paymentMethod);

        if (paymentMethod.equals(PaymentMethod.RAZORPAY)) {
            paymentResponse = paymentService.createRazorpayPaymentLink(user, amount);
        } else {
            paymentResponse = paymentService.createStripePaymentLink(user, amount, paymentOrder.getId());
        }
        return new ResponseEntity<>(paymentResponse, HttpStatus);
    }
}
