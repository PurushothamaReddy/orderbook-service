package com.ms.orderbookservice.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class OrderBookUtil {

    public static String getStackTraceString(Exception exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
