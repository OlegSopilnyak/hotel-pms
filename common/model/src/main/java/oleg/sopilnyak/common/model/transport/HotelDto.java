package oleg.sopilnyak.common.model.transport;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import oleg.sopilnyak.common.model.business.Hotel;

import javax.validation.constraints.Pattern;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Transport Model type for manage hotels
 */
@Data
@EqualsAndHashCode(of = {"name", "countryCode", "street", "building", "zipCode"})
@ToString(exclude = "rooms")
public class HotelDto implements Hotel{
    private static final long serialVersionUID = 1330875924195975740L;
    private String id;
    private String name;
    private String countryCode;
    private String city;
    private String street;
    private String building;
    private String zipCode;
    @Pattern(regexp = "\\-?\\d+.\\d\\d+", message = "Latitude Should Be In Format 'd+.dd+' Or '-d+.dd+'")
    private String latitude;
    @Pattern(regexp = "\\-?\\d+.\\d\\d+", message = "Longitude Should Be In Format 'd+.dd+' Or '-d+.dd+'")
    private String longitude;
    private String webSite;
//    private Set<RoomDto> rooms = new LinkedHashSet<>();
    private Set rooms = new LinkedHashSet<>();

}
