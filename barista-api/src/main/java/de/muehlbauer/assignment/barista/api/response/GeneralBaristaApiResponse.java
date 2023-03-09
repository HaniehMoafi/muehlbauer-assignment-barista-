package de.muehlbauer.assignment.barista.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralBaristaApiResponse {

    private int statusCode;
    private String message;
}
