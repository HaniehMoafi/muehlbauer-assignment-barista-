package de.muehlbauer.assignment.barista.service;


import de.muehlbauer.assignment.barista.exception.BaristaServiceException;

import java.util.List;
import java.util.Map;

public interface BaristaService {


    void addOrder(Integer orderNumber) throws BaristaServiceException;

    void cancelOrder(Integer orderNumber) throws BaristaServiceException;

    void forwardOrderStatus() throws BaristaServiceException;

    void removeOrder(Integer orderNumber);

    Map<Integer, String> getOrderStatus(List<Integer> orderNumbers);
}
