package com.bcp.proyecto3.dto;

import com.bcp.proyecto3.model.ClientType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private String id;
    private String name;
    private ClientType type;
}
