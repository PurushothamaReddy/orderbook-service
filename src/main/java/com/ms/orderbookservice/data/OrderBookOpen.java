package com.ms.orderbookservice.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBookOpen {
    @NotEmpty(message = "InstrumentId Cannot be Empty")
    private String instrumentId;
}
