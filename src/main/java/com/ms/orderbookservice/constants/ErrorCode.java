package com.ms.orderbookservice.constants;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ORDBK_100("ORDBK-100", "Exception in Service "),
    ORDBK_101("ORDBK-101", "OrderBook already opened "),
    ORDBK_102("ORDBK-102", "Exception in OrderBookOpen"),
    ORDBK_103("ORDBK-103", "OrderBook doesn't exist to close"),
    ORDBK_104("ORDBK-104", "Exception in closingOrderBook"),

    ORDBK_105("ORDBK-105", "OrderBook closed cannot add orders Executions can be added"),
    ORDBK_106("ORDBK-106", "Exception in Adding orders to OrderBook"),
    ORDBK_107("ORDBK-107", "Exception in Adding Execution");
    private String errorCode;
    private String message;

    ErrorCode(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }


}
