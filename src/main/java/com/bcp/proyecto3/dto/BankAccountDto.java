package com.bcp.proyecto3.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDto {
    private String id;
    private BigDecimal balance;
    private BigDecimal productId;

}
