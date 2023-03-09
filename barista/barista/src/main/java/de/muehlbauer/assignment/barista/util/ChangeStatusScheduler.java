package de.muehlbauer.assignment.barista.util;

import de.muehlbauer.assignment.barista.exception.BaristaServiceException;
import de.muehlbauer.assignment.barista.service.BaristaService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Configuration
@EnableScheduling
public class ChangeStatusScheduler {

    private final BaristaService baristaService;

    @Scheduled(fixedDelayString = "${barista.forward.order.status.delay}")
    void nextStatus() {
        try {
            baristaService.forwardOrderStatus();
        } catch (BaristaServiceException e) {
            //just log
        }
    }
}
