package de.muehlbauer.assignment.barista.exception;


import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class BaristaServiceException extends Exception {

    private String message;

    public BaristaServiceException(String message) {
        super(message);
        this.message = message;
    }
}
