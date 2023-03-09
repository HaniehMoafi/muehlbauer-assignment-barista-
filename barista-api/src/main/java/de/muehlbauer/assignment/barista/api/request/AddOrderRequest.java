package de.muehlbauer.assignment.barista.api.request;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddOrderRequest {


    @NotNull
    private Integer orderNumber;
}
