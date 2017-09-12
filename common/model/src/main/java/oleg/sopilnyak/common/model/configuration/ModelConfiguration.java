package oleg.sopilnyak.common.model.configuration;

import oleg.sopilnyak.common.model.business.*;
import oleg.sopilnyak.common.model.transport.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * Configuration for transport model
 */
@Configuration
public class ModelConfiguration {

    /**
     * Define CreditCard transport bean as prototype
     *
     * @return prototype
     * @see CreditCard
     */
    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.NO)
    public CreditCardDto buildCreditCardDto(){
        return new CreditCardDto();
    }

    /**
     * Define Employee transport bean as prototype
     *
     * @return prototype
     * @see Employee
     */
    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.NO)
    public EmployeeDto buildEmployeeDto(){
        return new EmployeeDto();
    }

    /**
     * Define Guest transport bean as prototype
     *
     * @return prototype
     * @see Guest
     */
    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.NO)
    public GuestDto buildGuestDto(){
        return new GuestDto();
    }

    /**
     * Define Habitation transport bean as prototype
     *
     * @return prototype
     * @see Habitation
     */
    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.NO)
    public HabitationDto buildHabitationDto(){
        return new HabitationDto();
    }

    /**
     * Define Habitation.OccupiedRoom transport bean as prototype
     *
     * @return prototype
     * @see Habitation.OccupiedRoom
     */
    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.NO)
    public HabitationOccupiedDto buildOccupiedDto(){
        return new HabitationOccupiedDto();
    }

    /**
     * Define HotelAgreement transport bean as prototype
     *
     * @return prototype
     * @see HotelAgreement
     */
    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.NO)
    public HotelAgreementDto buildHotelAgreementDto(){
        return new HotelAgreementDto();
    }

    /**
     * Define Hotel transport bean as prototype
     *
     * @return prototype
     * @see Hotel
     */
    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.NO)
    public HotelDto buildHotelDto(){
        return new HotelDto();
    }

    /**
     * Define Photo transport bean as prototype
     *
     * @return prototype
     * @see Photo
     */
    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.NO)
    public PhotoDto buildPhotoDto(){
        return new PhotoDto();
    }

    /**
     * Define Reservation transport bean as prototype
     *
     * @return prototype
     * @see Reservation
     */
    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.NO)
    public ReservationDto buildReservationDto(){
        return new ReservationDto();
    }

    /**
     * Define Room transport bean as prototype
     *
     * @return prototype
     * @see Room
     */
    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.NO)
    public RoomDto buildRoomDto(){
        return new RoomDto();
    }

    /**
     * Define ServiceProvided transport bean as prototype
     *
     * @return prototype
     * @see ServiceProvided
     */
    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.NO)
    public ServiceProvidedDto buildServiceProvidedDto(){
        return new ServiceProvidedDto();
    }

    /**
     * Define PreBookingResult transport bean as prototype
     *
     * @return prototype
     * @see PreBookingResult
     */
    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.NO)
    public PreBookingResultDto buildPreBookingResultDto(){
        return new PreBookingResultDto();
    }

    /**
     * Define Room.Activity transport bean as prototype
     *
     * @return prototype
     * @see Room.Activity
     */
    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.NO)
    public RoomActivityDto buildRootActivityDto(){
        return new RoomActivityDto();
    }
}
