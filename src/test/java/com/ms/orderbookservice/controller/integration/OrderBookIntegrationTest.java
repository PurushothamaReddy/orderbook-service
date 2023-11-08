package com.ms.orderbookservice.controller.integration;

import com.ms.orderbookservice.OrderBookServiceApplication;
import com.ms.orderbookservice.data.ErrorResponse;
import com.ms.orderbookservice.data.Order;
import com.ms.orderbookservice.data.OrderBookOpen;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static com.ms.orderbookservice.constants.ErrorCode.ORDBK_105;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OrderBookServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderBookIntegrationTest {
    @LocalServerPort
    private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testAddOrder_WithOpenOrderBook() {
        String instrumentId = "111";
        OrderBookOpen orderBookOpen = new OrderBookOpen(instrumentId);
        HttpEntity<OrderBookOpen> entity = new HttpEntity<>(orderBookOpen, headers);

        ResponseEntity<String> openResponse = restTemplate.exchange(
                createURLWithPort("/orderbook/open"),
                HttpMethod.POST, entity, String.class);
        assertEquals(instrumentId, openResponse.getBody());
        assertEquals(HttpStatus.CREATED, openResponse.getStatusCode());

        //Open Successful then add the order
        Order order = createOrder(instrumentId);
        ResponseEntity<Order> addOrderResponse = restTemplate.exchange(
                createURLWithPort("/orderbook/addOrder"),
                HttpMethod.POST, new HttpEntity<>(order, headers), Order.class);
        assertEquals(HttpStatus.OK, addOrderResponse.getStatusCode());
        assertEquals(instrumentId, addOrderResponse.getBody().getInstrumentId());
        assertEquals(order.getOrderQuantity(), addOrderResponse.getBody().getOrderQuantity());
        assertEquals(order.getEntryDate(), addOrderResponse.getBody().getEntryDate());
        assertEquals(order.getPrice(), addOrderResponse.getBody().getPrice());
        assertEquals(order.getSide(), addOrderResponse.getBody().getSide());
    }

    @Test
    public void testAddOrder_WithClosedOrderBook() {
        String instrumentId = "111";
        Order order = createOrder(instrumentId);
        ResponseEntity<ErrorResponse> addOrderResponse = restTemplate.exchange(
                createURLWithPort("/orderbook/addOrder"),
                HttpMethod.POST, new HttpEntity<>(order, headers), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, addOrderResponse.getStatusCode());
        assertEquals(ORDBK_105.getErrorCode(), addOrderResponse.getBody().getErrorCode());
        assertEquals(ORDBK_105.getMessage(), addOrderResponse.getBody().getMessage());
    }

    @Test
    public void testCloseOrderBook() {
        String instrumentId = "111";
        OrderBookOpen orderBookOpen = new OrderBookOpen(instrumentId);
        HttpEntity<OrderBookOpen> entity = new HttpEntity<>(orderBookOpen, headers);

        ResponseEntity<String> openResponse = restTemplate.exchange(
                createURLWithPort("/orderbook/open"),
                HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.CREATED.value(), openResponse.getStatusCode().value());
        assertEquals(instrumentId, openResponse.getBody());
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/orderbook/close/" + instrumentId),
                HttpMethod.DELETE, new HttpEntity<>(null, headers), String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(instrumentId, response.getBody());
    }


    //    @Test
//    public void testOpenOrderBook()  {
//        String instrumentId = "111";
//        OrderBookOpen orderBookOpen = new OrderBookOpen(instrumentId);
//        HttpEntity<OrderBookOpen> entity = new HttpEntity<>(orderBookOpen, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                createURLWithPort("/orderbook/open"),
//                HttpMethod.POST, entity, String.class);
//
//        assertEquals(instrumentId, response.getBody());
//    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private Order createOrder(String instrumentId) {
        return new Order(3, "11-05-2023", instrumentId, new BigDecimal(10.5), "buy");
    }
}
