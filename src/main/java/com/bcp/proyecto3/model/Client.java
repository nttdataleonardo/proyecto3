package com.bcp.proyecto3.model;

import com.bcp.proyecto3.dto.ClientDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "clients")
public class Client implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String name;
    private ClientType type;


    public Client toClient() {
        Client client = new Client();
        BeanUtils.copyProperties(this, client);
        return client;
    }
    public ClientDto toClientDto() {
        ClientDto clientDto = new ClientDto();
        BeanUtils.copyProperties(this, clientDto);
        return clientDto;
    }
}
