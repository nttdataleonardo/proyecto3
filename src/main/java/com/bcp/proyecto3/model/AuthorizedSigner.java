package com.bcp.proyecto3.model;

import com.bcp.proyecto3.dto.AuthorizedSignerDto;
import com.bcp.proyecto3.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "authorizedsigners")
public class AuthorizedSigner {
    @Id
    private String Id;
    private String name;
    private String idProduct;

    public AuthorizedSigner toAuthorizedSigner() {
        AuthorizedSigner authorizedSigner = new AuthorizedSigner();
        BeanUtils.copyProperties(this, authorizedSigner);
        return authorizedSigner;
    }
    public AuthorizedSignerDto toAuthorizedSignerDto() {
        AuthorizedSignerDto authorizedSignerDto = new AuthorizedSignerDto();
        BeanUtils.copyProperties(this, authorizedSignerDto);
        return authorizedSignerDto;
    }
}
