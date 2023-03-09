package de.muehlbauer.assignment.barista.api.response;

import lombok.Data;

import java.util.Map;

@Data
public class GetOrderStatus {

    private Map<Integer, String> orderStatus;
}
