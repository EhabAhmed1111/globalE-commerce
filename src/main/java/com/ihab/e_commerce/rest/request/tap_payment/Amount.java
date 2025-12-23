package com.ihab.e_commerce.rest.request.tap_payment;

import java.math.BigDecimal;

public record Amount(String currency, BigDecimal value){}
