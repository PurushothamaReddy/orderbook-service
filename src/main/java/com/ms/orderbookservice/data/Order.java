package com.ms.orderbookservice.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @NotNull(message = "orderQuantity Must Not be null")
    private Integer orderQuantity;

    @NotNull
    @Pattern(regexp = "(0[1-9]|1[1,2])(\\/|-)(0[1-9]|[12][0-9]|3[01])(\\/|-)(19|20)\\d{2}",message = "data should be in mm-dd-yyyy or mm/dd/yyyy")
    private String entryDate;
    @NotEmpty(message = "InstrumentId Cannot be Empty")
    private String instrumentId;
    private BigDecimal price;
     @NotNull(message = "side Must Not be null either buy/sell")

    private String side;
    /* buy/sell */
}
