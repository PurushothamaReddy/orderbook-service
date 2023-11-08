package com.ms.orderbookservice.service;

import com.ms.orderbookservice.constants.ErrorCode;
import com.ms.orderbookservice.data.Execution;
import com.ms.orderbookservice.data.Order;
import com.ms.orderbookservice.exception.ApplicationException;
import com.ms.orderbookservice.exception.InvalidInputException;
import com.ms.orderbookservice.repository.OrderBookDao;
import com.ms.orderbookservice.service.impl.OrderBookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class OrderBookServiceImplTest {

    @InjectMocks
    private OrderBookServiceImpl orderBookService;
    @Mock
    private OrderBookDao orderBookDao;

    @Test
    public void test_OpenAlreadyExistingOrderBook_ShouldThrowInvalidInputException() {
        String instrumentId = "111";
        Mockito.when(orderBookDao.getOrderBookByInstrumentId(instrumentId)).thenReturn(new ArrayList<>());
        InvalidInputException invalidInputException = assertThrows(InvalidInputException.class, () -> orderBookService.openOrderBook(instrumentId));
        assertEquals(ErrorCode.ORDBK_101.getErrorCode(), invalidInputException.getErrorCode());
    }

    @Test
    public void test_OpenOrderBook_ShouldRetrunGivenInstrumentId() {
        String instrumentId = "111";
        Mockito.doNothing().when(orderBookDao).openOrderBook(instrumentId);
        Mockito.when(orderBookDao.getOrderBookByInstrumentId(instrumentId)).thenReturn(null);
        assertEquals(instrumentId, orderBookService.openOrderBook(instrumentId));
    }

    @Test
    public void test_OpenOrderBookWithDaoError_ShouldThrowApplicationException() {
        String instrumentId = "111";
        Mockito.when(orderBookDao.getOrderBookByInstrumentId(instrumentId)).thenThrow(new NullPointerException());
        ApplicationException exception = assertThrows(ApplicationException.class, () -> orderBookService.openOrderBook(instrumentId));
        assertEquals(ErrorCode.ORDBK_102.getErrorCode(), exception.getErrorCode());
    }

    @Test
    public void test_CloseOrderBookWhichDoesNotExist_ShouldThrowInvalidInputException() {
        String instrumentId = "111";
        Mockito.when(orderBookDao.getOrderBookByInstrumentId(instrumentId)).thenReturn(null);
        InvalidInputException invalidInputException = assertThrows(InvalidInputException.class, () -> orderBookService.closeOrderBook(instrumentId));
        assertEquals(ErrorCode.ORDBK_103.getErrorCode(), invalidInputException.getErrorCode());
    }

    @Test
    public void test_CloseOrderBook_ShouldRetrunGivenInstrumentId() {
        String instrumentId = "111";
        Mockito.doNothing().when(orderBookDao).closeOrderBook(instrumentId);
        Mockito.when(orderBookDao.getOrderBookByInstrumentId(instrumentId)).thenReturn(new ArrayList<>());
        assertEquals(instrumentId, orderBookService.closeOrderBook(instrumentId));
    }

    @Test
    public void test_CloseOrderBookWithDaoError_ShouldThrowApplicationException() {
        String instrumentId = "111";
        Mockito.when(orderBookDao.getOrderBookByInstrumentId(instrumentId)).thenThrow(new NullPointerException());
        ApplicationException exception = assertThrows(ApplicationException.class, () -> orderBookService.closeOrderBook(instrumentId));
        assertEquals(ErrorCode.ORDBK_104.getErrorCode(), exception.getErrorCode());
    }

    @Test
    public void test_AddOrderToOrderBookWhichisNotOpened_ShouldThrowInvalidInputException() {
        Order order = createOrder();
        Mockito.when(orderBookDao.getOrderBookByInstrumentId(order.getInstrumentId())).thenReturn(null);
        InvalidInputException invalidInputException = assertThrows(InvalidInputException.class, () -> orderBookService.addOrder(order));
        assertEquals(ErrorCode.ORDBK_105.getErrorCode(), invalidInputException.getErrorCode());
    }

    @Test
    public void test_AddOrder_ShouldReturnSameOrder() {
        Order order = createOrder();
        Mockito.doNothing().when(orderBookDao).addOrder(order);
        Mockito.when(orderBookDao.getOrderBookByInstrumentId(order.getInstrumentId())).thenReturn(new ArrayList<>());
        assertEquals(order.getInstrumentId(), orderBookService.addOrder(order).getInstrumentId());
    }

    @Test
    public void test_AddOrderWithDaoError_ShouldThrowApplicationException() {
        Order order = createOrder();
        Mockito.when(orderBookDao.getOrderBookByInstrumentId(order.getInstrumentId())).thenThrow(new NullPointerException());
        ApplicationException exception = assertThrows(ApplicationException.class, () -> orderBookService.addOrder(order));
        assertEquals(ErrorCode.ORDBK_106.getErrorCode(), exception.getErrorCode());
    }


    @Test
    public void test_AddExecution_ShouldReturnSameExecution() {
        Execution execution = createExecution();
        Mockito.doNothing().when(orderBookDao).addExecution(execution);
        assertEquals(execution.getInstrumentId(), orderBookService.addExecution(execution).getInstrumentId());
    }

    @Test
    public void test_AddExecutionWithDaoError_ShouldThrowApplicationException() {
        Execution execution = createExecution();
        Mockito.doThrow(new NullPointerException()).when(orderBookDao).addExecution(execution);
        ApplicationException exception = assertThrows(ApplicationException.class, () -> orderBookService.addExecution(execution));
        assertEquals(ErrorCode.ORDBK_107.getErrorCode(), exception.getErrorCode());
    }

    private Order createOrder(){
        return new Order(3, "11-05-2023", "111", new BigDecimal(10.5), "buy");
    }

    private Execution createExecution(){
        return new Execution("111", 2,new BigDecimal(10.5) );
    }


}
