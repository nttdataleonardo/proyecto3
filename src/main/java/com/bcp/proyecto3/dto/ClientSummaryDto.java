package com.bcp.proyecto3.dto;

import com.bcp.proyecto3.model.Product;
import com.bcp.proyecto3.model.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientSummaryDto {
    private String clientId;
    private String clientName;
    private List<ProductType> typeProducts;
    private BigDecimal totalBalance;
    private BigDecimal creditAvailableTotal;
}
