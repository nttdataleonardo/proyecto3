package com.bcp.proyecto3.model;

import com.bcp.proyecto3.dto.MovementDto;
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
@Document(collection = "movements")
public class Movement {
    @Id
    private String id;
    private String name;
    private String idClient;
    private String idProduct;


    public Movement toMovement() {
        Movement movement = new Movement();
        BeanUtils.copyProperties(this, movement);
        return movement;
    }
    public MovementDto toMovementDto() {
        MovementDto movementDto = new MovementDto();
        BeanUtils.copyProperties(this, movementDto);
        return movementDto;
    }
}
