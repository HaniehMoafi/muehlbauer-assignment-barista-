package de.muehlbauer.assignment.barista.api.v1;


import de.muehlbauer.assignment.barista.api.request.AddOrderRequest;
import de.muehlbauer.assignment.barista.api.response.GeneralBaristaApiResponse;
import de.muehlbauer.assignment.barista.api.response.GetOrderStatus;
import de.muehlbauer.assignment.barista.exception.BaristaServiceException;
import de.muehlbauer.assignment.barista.service.BaristaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("v1/barista-order")
@RequiredArgsConstructor
public class BaristaRestController {

    private final BaristaService baristaService;

    @PostMapping
    public ResponseEntity<GeneralBaristaApiResponse> addOrder(@RequestBody AddOrderRequest request) throws BaristaServiceException {

        baristaService.addOrder(request.getOrderNumber());
        return new ResponseEntity<>(new GeneralBaristaApiResponse(HttpStatus.OK.value(),
                "order was added successfully"), CREATED);

    }

    @DeleteMapping
    public ResponseEntity<GeneralBaristaApiResponse> cancelOrder(@RequestParam Integer orderNumber) throws BaristaServiceException {

        baristaService.cancelOrder(orderNumber);
        return new ResponseEntity<>(new GeneralBaristaApiResponse(HttpStatus.ACCEPTED.value(),
                "order was canceled successfully"), ACCEPTED);
    }


    @GetMapping
    public ResponseEntity<GetOrderStatus> getOrderStatus(@RequestParam List<Integer> orderNumbers) {
        GetOrderStatus response = new GetOrderStatus();
        response.setOrderStatus(baristaService.getOrderStatus(orderNumbers));
        return ResponseEntity.ok().body(response);
    }
}
