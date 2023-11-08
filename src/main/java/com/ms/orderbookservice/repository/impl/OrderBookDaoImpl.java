package com.ms.orderbookservice.repository.impl;

import com.ms.orderbookservice.data.Execution;
import com.ms.orderbookservice.data.Order;
import com.ms.orderbookservice.repository.OrderBookDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Slf4j
public class OrderBookDaoImpl implements OrderBookDao {

    private Map<String, List<Order>> orderBookMap = null;
    private Map<String, List<Execution>> executionMap = null;

    @Autowired
    public OrderBookDaoImpl() {
        orderBookMap = new ConcurrentHashMap<>();
        executionMap = new ConcurrentHashMap<>();

    }

    @Override
    public void openOrderBook(String instrumentId) {
        orderBookMap.put(instrumentId, new ArrayList<>());
        log.info("OrderBook Opened for the given instrumentId:{}", instrumentId);
    }

    @Override
    public void closeOrderBook(String instrumentId) {
        if (orderBookMap.containsKey(instrumentId)) {
            orderBookMap.remove(instrumentId);
            log.info("OrderBook closed for the given instrumentId:{}", instrumentId);
        } else {
            log.warn("OrderBook not exist for the given instrumentId:{}", instrumentId);
        }

    }

    @Override
    public void addOrder(Order order) {
        String instrumentId = order.getInstrumentId();
        if (orderBookMap.containsKey(instrumentId)) {
            orderBookMap.get(instrumentId).add(order);
        }
        log.info("Added order for the given instrumentId:{}", instrumentId);

    }

    @Override
    public void addExecution(Execution execution) {
        String instrumentId = execution.getInstrumentId();
        if (!executionMap.containsKey(instrumentId)) {
            executionMap.put(instrumentId, new ArrayList<>());
        }
        executionMap.get(instrumentId).add(execution);
        log.info("Added Execution for the given instrumentId:{}", instrumentId);
    }


    @Override
    public List<Order> getOrderBookByInstrumentId(String instrumentId) {

        return orderBookMap.get(instrumentId) != null ? Collections.unmodifiableList(orderBookMap.get(instrumentId)): null;
    }
}
