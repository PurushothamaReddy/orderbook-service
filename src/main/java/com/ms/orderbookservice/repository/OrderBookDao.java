package com.ms.orderbookservice.repository;

import com.ms.orderbookservice.data.Execution;
import com.ms.orderbookservice.data.Order;

import java.util.List;

public interface OrderBookDao {

    public void openOrderBook(String instrumentId);

    public void closeOrderBook(String instrumentId);

    public void addOrder(Order order);

    public void addExecution(Execution execution);

    public List<Order>  getOrderBookByInstrumentId(String instrumentId);

}
