package oleg.sopilnyak.rest.controller;

import lombok.extern.slf4j.Slf4j;
import oleg.sopilnyak.common.facade.CustomerFacade;
import oleg.sopilnyak.common.model.business.HotelAgreement;
import oleg.sopilnyak.common.model.business.PreBookingResult;
import oleg.sopilnyak.common.model.transport.ConfirmedReservationDto;
import oleg.sopilnyak.rest.model.dto.ChangeBookingRequest;
import oleg.sopilnyak.rest.model.dto.InitBookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

/**
 * Controller for REST request
 */
@Slf4j
@RestController
@RequestMapping("/api/rooms-reservation")
public class ReservationController {

    private final CustomerFacade facade;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public ReservationController(CustomerFacade facade) {
        this.facade = facade;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<PreBookingResult> preBookRooms(@RequestBody InitBookingRequest request) {
        log.info("Init booking process.");
        PreBookingResult response =
                facade.book(request.getFrom(), request.getTo(), request.getGuests(), request.getRooms(), request.getFeatures());
        log.debug("Returning data {}", response);
        return ResponseEntity.ok(response);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<PreBookingResult> preBookRooms(@RequestBody ChangeBookingRequest request) {
        log.info("Change booking.");
        PreBookingResult response =
                facade.change(request.getId(), request.getFrom(), request.getTo(), request.getGuests(), request.getRooms());
        log.debug("Returning data {}", response);
        return ResponseEntity.ok(response);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/confirm", method = RequestMethod.PUT)
    public ResponseEntity<String> confirmBooking(@RequestBody ConfirmedReservationDto confirmation) {
        log.info("Confirming booking for {}", confirmation.getAgreementId());
        return ResponseEntity.ok(facade.confirm(confirmation));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/agreement/{id}", method = RequestMethod.GET)
    public ResponseEntity<HotelAgreement> getAgreement(@PathVariable String id) {
        log.info("Looking for information about agreement #{}", id);
        return ResponseEntity.ok(facade.retrieve(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/agreement/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> cancelAgreement(@PathVariable String id) {
        log.info("Looking for information about agreement #{}", id);
        facade.cancelBooking(id);
        log.debug("Returning data {}", "Success");
        return ResponseEntity.ok("Success");
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/reserved/from/{from}/till/{till}", method = RequestMethod.GET)
    public ResponseEntity<Set<HotelAgreement>> findReserved(
            @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable LocalDate from,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable LocalDate till
    ) {
        log.info("Looking for reservation from {} till {}", from, till);
        final Set<HotelAgreement> response = facade.findReserved(from, till);
        log.debug("Returning data {}", response);
        return ResponseEntity.ok(response);
    }
}
