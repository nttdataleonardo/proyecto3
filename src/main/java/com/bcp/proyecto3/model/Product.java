package com.bcp.proyecto3.model;

import com.bcp.proyecto3.constants.ProductoConstans;
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
public class Product extends ProductoConstans {
    @Id
    private String id;
    private String name;
    private ProductType type;
    private String idClient;
    private BigDecimal balance = BigDecimal.ZERO;
    private BigDecimal credit;
    private String mainAccount;
    private String bank;
    private Integer maxTransaction;

    @JsonIgnore
    private BigDecimal paycredit = BigDecimal.ZERO;
    @JsonIgnore
    private BigDecimal transSecondBank = BigDecimal.ZERO;
    @JsonIgnore
    private String idAnotherAccount;


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

    public Boolean compareMinBalanceWidthValue(BigDecimal minBalance) {
        Boolean validate = false;
        if (this.balance.compareTo(minBalance) < 0) {
            validate = true;
        }
        return validate;
    }

    public Boolean compareMaxTransactions() {
        Boolean validate = false;
        if(this.maxTransaction > 20) {
            this.balance.add(COMISION);
            validate = true;
        } else {
            this.maxTransaction++;
        }
        return validate;
    }
}
