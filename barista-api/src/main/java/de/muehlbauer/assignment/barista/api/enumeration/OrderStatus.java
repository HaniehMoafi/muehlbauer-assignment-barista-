package de.muehlbauer.assignment.barista.api.enumeration;

public enum OrderStatus {

    WAITING,
    IN_PREPARATION,
    FINISHED,
    PICKED_UP;

    public static OrderStatus findNextStep(OrderStatus status) {
        if(status.equals(PICKED_UP))
            return PICKED_UP;
        return OrderStatus.values()[status.ordinal()+1];
    }
}
