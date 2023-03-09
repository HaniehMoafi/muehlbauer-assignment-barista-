package de.muehlbauer.assignment.barista.service;

import de.muehlbauer.assignment.barista.exception.BaristaServiceException;

public interface OrderGateway {

    void finishOrder(Integer orderNumber) throws BaristaServiceException;
}
