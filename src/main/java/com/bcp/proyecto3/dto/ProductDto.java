package com.bcp.proyecto3.dto;

import com.bcp.proyecto3.model.ProductType;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String id;
    private String name;
    private ProductType type;
    private String idClient;
    private BigDecimal balance;
    private BigDecimal credit;
    private BigDecimal paycredit;
}
