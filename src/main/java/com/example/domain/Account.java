package com.example.domain;

import java.math.BigDecimal;

public interface Account {

    Card getCard();

    BigDecimal getBalance();
}
