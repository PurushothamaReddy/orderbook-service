package com.ms.orderbookservice.service;

import com.ms.orderbookservice.data.Execution;
import com.ms.orderbookservice.data.Order;


public interface OrderBookService {
    public String openOrderBook(String instrumentId);

    public String closeOrderBook(String instrumentId);

    public Order addOrder(Order order);

    public Execution addExecution(Execution execution);

}
