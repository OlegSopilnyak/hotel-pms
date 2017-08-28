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
        CreditCardDto dto = new CreditCardDto();
        return dto;
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
        EmployeeDto dto = new EmployeeDto();
        return dto;
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
        GuestDto dto = new GuestDto();
        return dto;
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
        HabitationDto dto = new HabitationDto();
        return dto;
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
        HabitationOccupiedDto dto = new HabitationOccupiedDto();
        return dto;
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
        HotelAgreementDto dto = new HotelAgreementDto();
        return dto;
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
        HotelDto dto = new HotelDto();
        return dto;
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
        PhotoDto dto = new PhotoDto();
        return dto;
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
        ReservationDto dto = new ReservationDto();
        return dto;
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
        RoomDto dto = new RoomDto();
        return dto;
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
        ServiceProvidedDto dto = new ServiceProvidedDto();
        return dto;
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
        PreBookingResultDto dto = new PreBookingResultDto();
        return dto;
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
        RoomActivityDto dto = new RoomActivityDto();
        return dto;
    }
}
