package oleg.sopilnyak.common.model.business;



import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

/**
 * Business Model type for manage hotels
 */
public interface Hotel extends HaveId,Serializable {
    /**
     * To get name of the hotel
     *
     * @return value
     */
    String getName();

    /**
     * To get country code of hotel location
     *
     * @return country code
     */
    String getCountryCode();

    /**
     * To get city of hotel location
     *
     * @return city
     */
    String getCity();

    /**
     * To get street of hotel location
     *
     * @return street
     */
    String getStreet();

    /**
     * To get building oh hotel
     *
     * @return building
     */
    String getBuilding();

    /**
     * To get Zip code of hotel
     *
     * @return zip-code
     */
    String getZipCode();

    /**
     * To get latitude of hotel location
     *
     * @return value
     */
    @SuppressWarnings("Annotator")
    @Pattern(regexp = "\\-?\\d+.\\d\\d+", message = "Latitude Should Be In Format 'd+.dd+' Or '-d+.dd+'")
    String getLatitude();

    /**
     * To get longitude of Hotel location
     *
     * @return value
     */
    @SuppressWarnings("Annotator")
    @Pattern(regexp = "\\-?\\d+.\\d\\d+", message = "Lon Should Be In Format 'd+.dd+' Or '-d+.dd+'")
    String getLongitude();

    /**
     * To get url to hotel site
     *
     * @return url to site
     */
    String getWebSite();

    /**
     * To get all rooms of hotel
     *
     * @return rooms
     */
    Set<Room> getRooms();
}
