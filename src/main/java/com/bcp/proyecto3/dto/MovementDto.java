package com.bcp.proyecto3.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementDto {
    private String id;
    private String name;
    private String idClient;
    private String idProduct;
}
