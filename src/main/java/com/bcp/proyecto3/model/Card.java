package com.bcp.proyecto3.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "cards")
public class Card {
    private String holderName;
    private Date dueDate;
    private String securityCode;
    private String issuerBank;
    private String cardMark;
    private boolean active;
    private String mainAccount;
}
