package de.muehlbauer.assignment.barista.service;


import de.muehlbauer.assignment.barista.api.enumeration.OrderStatus;
import de.muehlbauer.assignment.barista.entity.BaristaOrderEntity;
import de.muehlbauer.assignment.barista.exception.BaristaServiceException;
import de.muehlbauer.assignment.barista.repository.BaristaOrderRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaristaServiceTest {

    @Autowired
    private BaristaOrderRepository repository;
    @Autowired
    private BaristaService baristaService;


    @BeforeAll
    void setUp() {
        repository.save(creatBaristaOrder(1));
    }
    @AfterAll
    void clear() {
        repository.deleteAll();
    }
    private BaristaOrderEntity creatBaristaOrder(Integer orderNumber) {
        BaristaOrderEntity entity = new BaristaOrderEntity();
        entity.setOrderNumber(orderNumber);
        entity.setStatus(OrderStatus.WAITING);
        return entity;
    }

    @Test
    void barista_order_status_should_be(){
        List<BaristaOrderEntity> all = repository.findAll();
        assertEquals(all.get(0).getStatus(),  OrderStatus.IN_PREPARATION);
    }


    @Test
    void barista_order_should_not_save_duplicate_order(){
        BaristaServiceException thrown = assertThrows(
                BaristaServiceException.class,
                () -> baristaService.addOrder(1),
                "Expected do not allow to save duplicate order"
        );
        assertTrue(thrown.getMessage().contains("add.order.failed"));
    }

    @ParameterizedTest
    @CsvSource({"IN_PREPARATION,FINISHED,PICKED_UP"})
    void find_next_step(String step2,String step3,String step4){

        OrderStatus should_be_finished = OrderStatus.findNextStep(OrderStatus.valueOf(step2));
        OrderStatus should_be_picked_up = OrderStatus.findNextStep(OrderStatus.valueOf(step3));
        OrderStatus should_remain_picked_up = OrderStatus.findNextStep(OrderStatus.valueOf(step4));

        assertEquals(OrderStatus.FINISHED, should_be_finished);
        assertEquals(OrderStatus.PICKED_UP, should_be_picked_up);
        assertEquals(OrderStatus.PICKED_UP, should_remain_picked_up);
    }

    @Test
    void order_can_be_cancel_in_waiting_step() throws BaristaServiceException {
        repository.save(creatBaristaOrder(3));
        baristaService.cancelOrder(3);
        Optional<BaristaOrderEntity> byOrderNumber = repository.findByOrderNumber(3);
        assertTrue(byOrderNumber.isEmpty());
    }

    @Test
    void order_can_not_be_cancel_in_other_step(){

        BaristaServiceException thrown = assertThrows(
                BaristaServiceException.class,
                () ->  baristaService.cancelOrder(1),
                "Expected do not allow to cancel order in other steps"
        );
        assertTrue(thrown.getMessage().contentEquals("barista.cancel.order.failed"));

    }
}
