package com.ms.orderbookservice.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Execution {
    @NotEmpty(message = "instrumentId Must Not be Empty")
    private String instrumentId;
    @NotNull(message = "quantity Must Not be null")
    private Integer quantity;
    @NotNull(message = "price Must Not be null")
    private BigDecimal price;


}
