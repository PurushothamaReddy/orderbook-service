package com.ms.orderbookservice.controller;

import com.ms.orderbookservice.data.Execution;
import com.ms.orderbookservice.data.Order;
import com.ms.orderbookservice.data.OrderBookOpen;
import com.ms.orderbookservice.service.OrderBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/orderbook")
@Slf4j
public class OrderBookController {

    @Autowired
    private OrderBookService orderBookService;


    @PostMapping("/open")
    public ResponseEntity<String> openOrderBook(@RequestBody(required = true) @Valid OrderBookOpen orderBookOpen) {
        log.info("Inside OrderBookController openOrderBook for the given instrumentId:{}", orderBookOpen.getInstrumentId());

        return ResponseEntity.status(HttpStatus.CREATED).body(orderBookService.openOrderBook(orderBookOpen.getInstrumentId()));

    }

    @DeleteMapping("/close/{instrumentId}")
    public ResponseEntity<String> closeOrderBook(@PathVariable(required = true) String instrumentId) {
        log.info("Inside OrderBookController closeOrderBook for the given instrumentId:{}", instrumentId);

        return ResponseEntity.status(HttpStatus.OK).body(orderBookService.closeOrderBook(instrumentId));

    }


    @PostMapping("/addOrder")
    public ResponseEntity<Order> addOrder(@RequestBody(required = true) @Valid Order order) {
        log.info("Inside OrderBookController addOrder for the given instrumentId:{}", order.getInstrumentId());

        return ResponseEntity.status(HttpStatus.OK).body(orderBookService.addOrder(order));
    }


    @PostMapping("/addExecution")
    public ResponseEntity<Execution> addExecution(@RequestBody(required = true) @Valid Execution execution) {
        log.info("Inside OrderBookController addExecution for the given instrumentId:{}", execution.getInstrumentId());

        return ResponseEntity.status(HttpStatus.OK).body(orderBookService.addExecution(execution));
    }

}
