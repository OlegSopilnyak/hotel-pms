package oleg.sopilnyak.common.util;

import oleg.sopilnyak.common.model.business.Guest;
import oleg.sopilnyak.common.model.transport.GuestDto;
import oleg.sopilnyak.common.model.transport.PersonDto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class utility for tests
 */
public final class Utility {
    private Utility() {}

    /**
     * To make sample of guests set
     *
     * @return guests set
     */
    public static Set<Guest> makeOneGuest() {
        GuestDto guest = new GuestDto();
        guest.setId("12");
        guest.setFirstName("First");
        guest.setLastName("Last");
        guest.setDocument(PersonDto.ID.builder().type("PASSPORT").citizenship("UA").number("123456").expired(LocalDate.now().plusYears(5)).build());
        return Arrays.asList(guest).stream().collect(Collectors.toSet());
    }

}
