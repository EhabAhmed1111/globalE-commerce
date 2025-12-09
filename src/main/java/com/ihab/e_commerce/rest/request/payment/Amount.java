package com.ihab.e_commerce.rest.request.payment;

import java.math.BigDecimal;

public record Amount(String currency, BigDecimal value){}
