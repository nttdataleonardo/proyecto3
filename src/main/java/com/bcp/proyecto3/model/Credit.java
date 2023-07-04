package com.bcp.proyecto3.model;

import com.bcp.proyecto3.dto.CreditDto;
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
@Document(collection = "credits")
public class Credit {
    @Id
    private String id;
    private BigDecimal balance;

    public Credit toCredit() {
        Credit credit = new Credit();
        BeanUtils.copyProperties(this, credit);
        return credit;
    }
    public CreditDto toCreditDto() {
        CreditDto creditDto = new CreditDto();
        BeanUtils.copyProperties(this, creditDto);
        return creditDto;
    }
}
