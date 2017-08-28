package oleg.sopilnyak.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import oleg.sopilnyak.common.model.business.Hotel;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Persistence Model type for hotels
 */
@Data
@Entity
@Table(name = "hotels")
@EqualsAndHashCode(callSuper = false)
public class HotelEntity extends AbstractEntity implements Hotel {
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
    @OneToMany(fetch = FetchType.EAGER, targetEntity = RoomEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id")
    private Set rooms = new LinkedHashSet<>();
}
