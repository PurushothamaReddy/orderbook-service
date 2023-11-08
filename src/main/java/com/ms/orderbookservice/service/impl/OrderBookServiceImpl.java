package com.ms.orderbookservice.service.impl;

import com.ms.orderbookservice.data.Execution;
import com.ms.orderbookservice.data.Order;
import com.ms.orderbookservice.exception.ApplicationException;
import com.ms.orderbookservice.exception.InvalidInputException;
import com.ms.orderbookservice.repository.OrderBookDao;
import com.ms.orderbookservice.service.OrderBookService;
import com.ms.orderbookservice.util.OrderBookUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ms.orderbookservice.constants.ErrorCode.*;

@Service
@Slf4j
public class OrderBookServiceImpl implements OrderBookService {

    @Autowired
    private OrderBookDao orderBookDao;

    @Override
    public String openOrderBook(String instrumentId) {
        try {
            List<Order> orderList = orderBookDao.getOrderBookByInstrumentId(instrumentId);
            if (orderList != null) {
                throw new InvalidInputException(ORDBK_101.getErrorCode(), ORDBK_101.getMessage());
            }
            orderBookDao.openOrderBook(instrumentId);
        } catch (InvalidInputException ex) {
            log.error("Exception in OrderBookOpen for the given instrumentId:{} cause:{}", instrumentId, OrderBookUtil.getStackTraceString(ex));
            throw ex;
        } catch (Exception ex) {
            log.error("Exception in OrderBookOpen for the given instrumentId:{} cause:{}", instrumentId, OrderBookUtil.getStackTraceString(ex));
            throw new ApplicationException(ORDBK_102.getErrorCode(), ORDBK_102.getMessage(), ex);
        }
        return instrumentId;
    }

    @Override
    public String closeOrderBook(String instrumentId) {
        try {
            List<Order> orderList = orderBookDao.getOrderBookByInstrumentId(instrumentId);
            if (orderList == null) {
                log.error("OrderBook doesn't exist for the given instrumentId:{}", instrumentId);
                throw new InvalidInputException(ORDBK_103.getErrorCode(), ORDBK_103.getMessage());
            }
            orderBookDao.closeOrderBook(instrumentId);
        } catch (InvalidInputException ex) {
            log.error("OrderBook doesn't exist for the given instrumentId:{} cause:{}", instrumentId, OrderBookUtil.getStackTraceString(ex));
            throw ex;
        } catch (Exception ex) {
            log.error("Exception in closingOrderBook for the given instrumentId:{}  cause:{}", instrumentId, OrderBookUtil.getStackTraceString(ex));
            throw new ApplicationException(ORDBK_104.getErrorCode(), ORDBK_104.getMessage(), ex);
        }
        return instrumentId;
    }

    @Override
    public Order addOrder(Order order) {
        String instrumentId = order.getInstrumentId();
        try {
            List<Order> orderList = orderBookDao.getOrderBookByInstrumentId(instrumentId);
            if (orderList == null) {
                throw new InvalidInputException(ORDBK_105.getErrorCode(), ORDBK_105.getMessage());
            }
            orderBookDao.addOrder(order);
        } catch (InvalidInputException ex) {
            log.error("OrderBook closed cannot add orders Executions can be added for the given instrumentId:{} cause:{}", instrumentId, OrderBookUtil.getStackTraceString(ex));
            throw ex;
        } catch (Exception ex) {
            log.error("Exception in Adding orders to OrderBook for the given instrumentId:{}  cause:{}", instrumentId, OrderBookUtil.getStackTraceString(ex));
            throw new ApplicationException(ORDBK_106.getErrorCode(), ORDBK_106.getMessage(), ex);
        }
        return order;
    }

    @Override
    public Execution addExecution(Execution execution) {
        String instrumentId = execution.getInstrumentId();
        try {
            orderBookDao.addExecution(execution);
        } catch (Exception ex) {
            log.error("Exception in Adding Execution for the given instrumentId:{} cause:{}", instrumentId, OrderBookUtil.getStackTraceString(ex));
            throw new ApplicationException(ORDBK_107.getErrorCode(), ORDBK_107.getMessage(), ex);
        }
        return execution;
    }
}
