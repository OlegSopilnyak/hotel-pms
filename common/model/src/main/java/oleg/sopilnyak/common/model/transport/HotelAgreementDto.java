package oleg.sopilnyak.common.model.transport;

import lombok.*;
import oleg.sopilnyak.common.model.business.Guest;
import oleg.sopilnyak.common.model.business.HotelAgreement;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Transport Model type for manage agreements with guests
 */
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "createdAt"})
public class HotelAgreementDto implements HotelAgreement{
    private static final long serialVersionUID = 727687912955790115L;
    private String id;
    private LocalDateTime createdAt;
    private ReservationDto reservation = new ReservationDto();
    private HabitationDto habitation = new HabitationDto();
    private CreditCardDto card = new CreditCardDto();

    @Getter(AccessLevel.NONE)
    private Set guestSet = new LinkedHashSet<>();
    //    private Set<GuestDto> guestSet = new LinkedHashSet<>();
    @SuppressWarnings("unchecked")
    public Set<Guest> getGuestSet(){
        return guestSet;
    }
}
