package com.bcp.proyecto3.model;

import com.bcp.proyecto3.dto.HolderDto;
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
@Document(collection = "holders")
public class Holder {
    @Id
    private String Id;
    private String name;
    private String idProduct;

    public Holder toHolder() {
        Holder holder = new Holder();
        BeanUtils.copyProperties(this, holder);
        return holder;
    }
    public HolderDto toHolderDto() {
        HolderDto holderDto = new HolderDto();
        BeanUtils.copyProperties(this, holderDto);
        return holderDto;
    }
}
