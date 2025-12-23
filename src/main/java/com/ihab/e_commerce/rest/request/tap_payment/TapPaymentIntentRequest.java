package com.ihab.e_commerce.rest.request.tap_payment;


import com.ihab.e_commerce.data.enums.PaymentMethod;

public record TapPaymentIntentRequest(
        Long orderId,
        // this is the amount of money that will be paid
        Amount amount,
        // this is the email of the customer(that identified customer)
        Customer customer,
        // what kind of card id you use (visa, Apple Pay)
        PaymentMethod paymentMethod,
        // this is the url that front will go to after payment
        Redirect redirect,
        // It is the server-to-server real confirmation.
        //payment succeeded
        //payment failed
        //payment canceled
        //payment expired)
        Post webhook
) { }

