package de.muehlbauer.assignment.barista.service.impl;

import de.muehlbauer.assignment.barista.api.enumeration.OrderStatus;
import de.muehlbauer.assignment.barista.entity.BaristaOrderEntity;
import de.muehlbauer.assignment.barista.exception.BaristaServiceException;
import de.muehlbauer.assignment.barista.repository.BaristaOrderRepository;
import de.muehlbauer.assignment.barista.service.BaristaService;
import de.muehlbauer.assignment.barista.service.OrderGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
public class BaristaServiceImpl implements BaristaService {

    private final BaristaOrderRepository baristaOrderRepository;
    private final OrderGateway orderGateway;


    @Override
    public void addOrder(Integer orderNumber) throws BaristaServiceException {
        BaristaOrderEntity entity = new BaristaOrderEntity();
        entity.setOrderNumber(orderNumber);
        entity.setStatus(OrderStatus.WAITING);
        try {
            baristaOrderRepository.save(entity);
        } catch (Throwable t) {
            throw new BaristaServiceException("barista.add.order.failed");
        }
    }

    @Override
    @Transactional
    public void cancelOrder(Integer orderNumber) throws BaristaServiceException {
        Optional<BaristaOrderEntity> foundOrder = baristaOrderRepository.findByOrderNumber(orderNumber);
        if (foundOrder.isPresent()) {
            if (Objects.equals(foundOrder.get().getStatus(), OrderStatus.WAITING)) {
                removeOrder(orderNumber);
            } else {
                throw new BaristaServiceException("barista.cancel.order.failed");
            }
        } else {
            throw new BaristaServiceException("barista.order.not.found");
        }
    }

    @Override
    @Transactional
    public void forwardOrderStatus() throws BaristaServiceException {
        List<BaristaOrderEntity> allOrders = baristaOrderRepository.findAll();

        for (BaristaOrderEntity order : allOrders) {
            OrderStatus nextStep = OrderStatus.findNextStep(order.getStatus());
            if (Objects.equals(nextStep, OrderStatus.PICKED_UP)) {
                removeOrder(order.getOrderNumber());
                orderGateway.finishOrder(order.getOrderNumber());
            } else {
                order.setStatus(nextStep);
                baristaOrderRepository.save(order);
            }
        }
    }

    @Override
    public void removeOrder(Integer orderNumber) {
        baristaOrderRepository.deleteByOrderNumber(orderNumber);
    }

    @Override
    public Map<Integer, String> getOrderStatus(List<Integer> orderNumbers) {
        List<BaristaOrderEntity> orders = baristaOrderRepository.findAllByOrderNumberIn(orderNumbers);
        Map<Integer, String> orderStatusMap = new HashMap<>();
        orders.forEach(order -> orderStatusMap.put(order.getOrderNumber(), order.getStatus().name()));
        return orderStatusMap;
    }
}
