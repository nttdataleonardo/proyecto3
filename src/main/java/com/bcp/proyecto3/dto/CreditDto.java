package com.bcp.proyecto3.dto;

import lombok.*;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditDto {
    private String id;
    private BigDecimal balance;
}
