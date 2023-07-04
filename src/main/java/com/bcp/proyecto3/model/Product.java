package com.bcp.proyecto3.model;

import com.bcp.proyecto3.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private ProductType type;
    private String idClient;
    private BigDecimal balance = BigDecimal.ZERO;
    private BigDecimal credit;

    @JsonIgnore
    private BigDecimal paycredit = BigDecimal.ZERO;

    public Product toProduct(){
        Product product = new Product();
        BeanUtils.copyProperties(product, this);
        return product;
    }
    public ProductDto toProductDto(){
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(this, productDto);
        return productDto;
    }
}
