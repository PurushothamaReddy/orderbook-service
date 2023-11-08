package com.ms.orderbookservice.repository;

import com.ms.orderbookservice.data.Execution;
import com.ms.orderbookservice.data.Order;
import com.ms.orderbookservice.repository.impl.OrderBookDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class OrderBookDaoImplTest {

    private OrderBookDao orderBookDao;

    @BeforeEach
    public void init() {
        orderBookDao = new OrderBookDaoImpl();
    }

    @Test
    public void test_OpenOrderBook() {
        String instrumentId = "111";
        orderBookDao.openOrderBook(instrumentId);
        assertNotNull(orderBookDao.getOrderBookByInstrumentId(instrumentId));

    }

    @Test
    public void test_CloseOrderBook() {
        String instrumentId = "111";
        orderBookDao.closeOrderBook(instrumentId);
        assertNull(orderBookDao.getOrderBookByInstrumentId(instrumentId));
    }

    @Test
    public void test_AddOrder() {
        String instrumentId = "111";
        orderBookDao.openOrderBook(instrumentId);
        assertNotNull(orderBookDao.getOrderBookByInstrumentId(instrumentId));
        Order order = createOrder(instrumentId);
        orderBookDao.addOrder(order);
        assertEquals(1, orderBookDao.getOrderBookByInstrumentId(instrumentId).size());
    }

    @Test
    public void test_AddExecution() {
        String instrumentId = "111";
        orderBookDao.closeOrderBook(instrumentId);
        assertNull(orderBookDao.getOrderBookByInstrumentId(instrumentId));
        Execution execution = createExecution(instrumentId);
        orderBookDao.addExecution(execution);
    }


    private Order createOrder(String instrumentId) {
        return new Order(3, "11-05-2023", instrumentId, new BigDecimal(10.5), "buy");
    }

    private Execution createExecution(String instrumentId){
        return new Execution(instrumentId, 2,new BigDecimal(10.5) );
    }
}
