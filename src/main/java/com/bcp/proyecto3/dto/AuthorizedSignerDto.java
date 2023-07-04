package com.bcp.proyecto3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizedSignerDto {
    private String Id;
    private String name;
    private String idProduct;
}
