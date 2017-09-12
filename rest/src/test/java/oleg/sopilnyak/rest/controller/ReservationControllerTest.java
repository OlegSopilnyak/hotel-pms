package oleg.sopilnyak.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import oleg.sopilnyak.common.facade.CustomerFacade;
import oleg.sopilnyak.common.model.business.ConfirmReservation;
import oleg.sopilnyak.common.model.business.Guest;
import oleg.sopilnyak.common.model.business.Room;
import oleg.sopilnyak.common.model.configuration.ModelConfiguration;
import oleg.sopilnyak.common.model.transport.*;
import oleg.sopilnyak.common.service.exception.CannotChangeReservationException;
import oleg.sopilnyak.common.service.exception.CannotReserveException;
import oleg.sopilnyak.common.service.exception.ResourceNotFoundException;
import oleg.sopilnyak.rest.configuration.RestConfiguration;
import oleg.sopilnyak.rest.model.dto.ChangeBookingRequest;
import oleg.sopilnyak.rest.model.dto.InitBookingRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ReservationControllerTest.class,
        ModelConfiguration.class, RestConfiguration.class
})
@WebAppConfiguration
@Configuration
public class ReservationControllerTest {

    private static final String BASE_URL = "/api/rooms-reservation";
    @Autowired
    private WebApplicationContext context;

    @Autowired
    protected CustomerFacade customerFacade;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        objectMapper = new ObjectMapper();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        reset(customerFacade);
    }

    @Test
    public void preBookRooms() throws Exception {
        InitBookingRequest request = new InitBookingRequest();
        RoomFeatureDto feature = new RoomFeatureDto();
        feature.setCode("1234");
        feature.setName("TV");
        feature.setDescription("TV Set with 100 channels.");
        feature.setDailyCost(BigDecimal.ZERO);
        feature.setTotalCost(BigDecimal.ONE);
        request.setFeatures(Stream.of(feature).collect(Collectors.toSet()));
        GuestDto guest = new GuestDto();
        guest.setFirstName("First");
        guest.setLastName("Last");
        guest.setId("12344-4265-999");
        request.setGuests(Stream.of(guest).collect(Collectors.toSet()));
        request.setFrom(LocalDate.now());
        request.setTo(LocalDate.now());
        request.setRooms(1);

        PreBookingResultDto response = new PreBookingResultDto();
        response.setHotelAgreementId("12345678-2345678");
        response.setGuests(Stream.of(guest).collect(Collectors.toSet()));
        RoomDto room = new RoomDto();
        room.setAvailableFeatures(Stream.of(feature).collect(Collectors.toSet()));
        room.setId("HT-234");
        room.setCapacity(2);
        room.setFloor(2);
        room.setType(Room.Type.BUSINESS);
        room.setWindows(1);
        room.setDailyCost(BigDecimal.valueOf(140));
        response.setDedicatedRoom(Stream.of(room).collect(Collectors.toSet()));
        when(customerFacade.book(any(LocalDate.class), any(LocalDate.class), anySetOf(Guest.class), anyInt(), anySetOf(Room.Feature.class))).thenReturn(response);

        String content = objectMapper.writeValueAsString(request);
        MvcResult result =

                mockMvc.perform(post(BASE_URL + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isOk()).andReturn();

        String json = result.getResponse().getContentAsString();
        assertFalse(StringUtils.isEmpty(json));
        PreBookingResultDto booking = objectMapper.readValue(json, PreBookingResultDto.class);
        assertNotNull(booking);
        assertEquals(response.getHotelAgreementId(), booking.getHotelAgreementId());
        assertEquals(response.getDedicatedRoom(), booking.getDedicatedRoom());
        assertEquals(response.getGuests(), booking.getGuests());
    }

    @Test
    public void preBookRoomsBad() throws Exception {
        InitBookingRequest request = new InitBookingRequest();
        RoomFeatureDto feature = new RoomFeatureDto();
        feature.setCode("1234");
        feature.setName("TV");
        feature.setDescription("TV Set with 100 channels.");
        feature.setDailyCost(BigDecimal.ZERO);
        feature.setTotalCost(BigDecimal.ONE);
        request.setFeatures(Stream.of(feature).collect(Collectors.toSet()));
        GuestDto guest = new GuestDto();
        guest.setFirstName("First");
        guest.setLastName("Last");
        guest.setId("12344-4265-999");
        request.setGuests(Stream.of(guest).collect(Collectors.toSet()));
        request.setFrom(LocalDate.now());
        request.setTo(LocalDate.now());
        request.setRooms(1);

        PreBookingResultDto response = new PreBookingResultDto();
        response.setHotelAgreementId("12345678-2345678");
        response.setGuests(Stream.of(guest).collect(Collectors.toSet()));
        RoomDto room = new RoomDto();
        room.setAvailableFeatures(Stream.of(feature).collect(Collectors.toSet()));
        room.setId("HT-234");
        room.setCapacity(2);
        room.setFloor(2);
        room.setType(Room.Type.BUSINESS);
        room.setWindows(1);
        room.setDailyCost(BigDecimal.valueOf(140));
        response.setDedicatedRoom(Stream.of(room).collect(Collectors.toSet()));
        when(customerFacade.book(any(LocalDate.class), any(LocalDate.class), anySetOf(Guest.class), anyInt(), anySetOf(Room.Feature.class)))
                .thenThrow(new CannotReserveException("test"))
                ;

        String content = objectMapper.writeValueAsString(request);
        MvcResult result =

                mockMvc.perform(post(BASE_URL + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isConflict()).andReturn();

        String json = result.getResponse().getContentAsString();
        assertFalse(StringUtils.isEmpty(json));
    }

    @org.junit.Test
    public void preBookRooms1() throws Exception {
        ChangeBookingRequest request = new ChangeBookingRequest();
        RoomFeatureDto feature = new RoomFeatureDto();
        feature.setCode("1234");
        feature.setName("TV");
        feature.setDescription("TV Set with 100 channels.");
        feature.setDailyCost(BigDecimal.ZERO);
        feature.setTotalCost(BigDecimal.ONE);
        GuestDto guest = new GuestDto();
        guest.setFirstName("First");
        guest.setLastName("Last");
        guest.setId("12344-4265-999");
        request.setGuests(Stream.of(guest).collect(Collectors.toSet()));
        request.setFrom(LocalDate.now());
        request.setTo(LocalDate.now());
        request.setRooms(1);

        PreBookingResultDto response = new PreBookingResultDto();
        response.setHotelAgreementId("12345678-2345678");
        response.setGuests(Stream.of(guest).collect(Collectors.toSet()));
        RoomDto room = new RoomDto();
        room.setAvailableFeatures(Stream.of(feature).collect(Collectors.toSet()));
        room.setId("HT-234");
        room.setCapacity(2);
        room.setFloor(2);
        room.setType(Room.Type.BUSINESS);
        room.setWindows(1);
        room.setDailyCost(BigDecimal.valueOf(140));
        response.setDedicatedRoom(Stream.of(room).collect(Collectors.toSet()));
        request.setId(response.getHotelAgreementId());

        when(customerFacade.change(anyString(), any(LocalDate.class), any(LocalDate.class), anySetOf(Guest.class), anyInt())).thenReturn(response);

        String content = objectMapper.writeValueAsString(request);
        MvcResult result =

                mockMvc.perform(put(BASE_URL + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isOk()).andReturn();

        String json = result.getResponse().getContentAsString();
        assertFalse(StringUtils.isEmpty(json));
        PreBookingResultDto booking = objectMapper.readValue(json, PreBookingResultDto.class);
        assertNotNull(booking);
        assertEquals(response.getHotelAgreementId(), booking.getHotelAgreementId());
        assertEquals(response.getDedicatedRoom(), booking.getDedicatedRoom());
        assertEquals(response.getGuests(), booking.getGuests());
    }


    @org.junit.Test
    public void preBookRooms1Bad() throws Exception {
        ChangeBookingRequest request = new ChangeBookingRequest();
        RoomFeatureDto feature = new RoomFeatureDto();
        feature.setCode("1234");
        feature.setName("TV");
        feature.setDescription("TV Set with 100 channels.");
        feature.setDailyCost(BigDecimal.ZERO);
        feature.setTotalCost(BigDecimal.ONE);
        GuestDto guest = new GuestDto();
        guest.setFirstName("First");
        guest.setLastName("Last");
        guest.setId("12344-4265-999");
        request.setGuests(Stream.of(guest).collect(Collectors.toSet()));
        request.setFrom(LocalDate.now());
        request.setTo(LocalDate.now());
        request.setRooms(1);

        PreBookingResultDto response = new PreBookingResultDto();
        response.setHotelAgreementId("12345678-2345678");
        response.setGuests(Stream.of(guest).collect(Collectors.toSet()));
        RoomDto room = new RoomDto();
        room.setAvailableFeatures(Stream.of(feature).collect(Collectors.toSet()));
        room.setId("HT-234");
        room.setCapacity(2);
        room.setFloor(2);
        room.setType(Room.Type.BUSINESS);
        room.setWindows(1);
        room.setDailyCost(BigDecimal.valueOf(140));
        response.setDedicatedRoom(Stream.of(room).collect(Collectors.toSet()));
        request.setId(response.getHotelAgreementId());

        when(customerFacade.change(anyString(), any(LocalDate.class), any(LocalDate.class), anySetOf(Guest.class), anyInt()))
                .thenThrow(new CannotChangeReservationException("test", "id 4566"))
        ;
        String content = objectMapper.writeValueAsString(request);
        MvcResult result =

                mockMvc.perform(put(BASE_URL + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isConflict()).andReturn();

        String json = result.getResponse().getContentAsString();
        assertFalse(StringUtils.isEmpty(json));
    }

    @org.junit.Test
    public void confirmBooking() throws Exception {
        ConfirmedReservationDto request = new ConfirmedReservationDto();
        request.setAgreementId("56789");
        ConfirmedRoomDto room = new ConfirmedRoomDto();
        request.setBookedRooms(Stream.of(room).collect(Collectors.toSet()));
        request.setCreditCard(new CreditCardDto());
        String content = objectMapper.writeValueAsString(request);
        when(customerFacade.confirm(any(ConfirmReservation.class))).thenReturn("Success");

        MvcResult result =

                mockMvc.perform(put(BASE_URL + "/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isOk()).andReturn();

        String json = result.getResponse().getContentAsString();
        assertFalse(StringUtils.isEmpty(json));
        assertEquals("Success", json);
    }

    @org.junit.Test
    public void confirmBookingBad() throws Exception {
        ConfirmedReservationDto request = new ConfirmedReservationDto();
        request.setAgreementId("56789");
        ConfirmedRoomDto room = new ConfirmedRoomDto();
        request.setBookedRooms(Stream.of(room).collect(Collectors.toSet()));
        request.setCreditCard(new CreditCardDto());
        String content = objectMapper.writeValueAsString(request);
        when(customerFacade.confirm(any(ConfirmReservation.class)))
                .thenThrow(new ResourceNotFoundException("test"))
                ;

        MvcResult result =

                mockMvc.perform(put(BASE_URL + "/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isNotFound()).andReturn();

        String json = result.getResponse().getContentAsString();
        assertFalse(StringUtils.isEmpty(json));
    }

    @org.junit.Test
    public void getAgreement() throws Exception {
        String id = "1234-4455-5567";
        HotelAgreementDto agreementDto = new HotelAgreementDto();
        agreementDto.setId(id);
        when(customerFacade.retrieve(id)).thenReturn(agreementDto);
        MvcResult result =

                mockMvc.perform(get(BASE_URL + "/agreement/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Hi"))
                        .andExpect(status().isOk()).andReturn();

        String json = result.getResponse().getContentAsString();
        assertFalse(StringUtils.isEmpty(json));
        HotelAgreementDto agreement = objectMapper.readValue(json, HotelAgreementDto.class);
        assertNotNull(agreement);

    }

    @org.junit.Test
    public void getAgreementBad() throws Exception {
        String id = "1234-4455-5567";
        HotelAgreementDto agreementDto = new HotelAgreementDto();
        agreementDto.setId(id);
        when(customerFacade.retrieve(id)).thenThrow(new IllegalStateException("test"));
        MvcResult result =

                mockMvc.perform(get(BASE_URL + "/agreement/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Hi"))
                        .andExpect(status().isServiceUnavailable()).andReturn();

        String json = result.getResponse().getContentAsString();
        assertFalse(StringUtils.isEmpty(json));

    }

    @org.junit.Test
    public void cancelAgreement() throws Exception {
        String id = "1234-4455-5567";
        MvcResult result =

                mockMvc.perform(delete(BASE_URL + "/agreement/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Hi"))
                        .andExpect(status().isOk()).andReturn();

        String json = result.getResponse().getContentAsString();
        assertEquals("Success", json);
    }

    @org.junit.Test
    public void findReserved() throws Exception {
        LocalDate from = LocalDate.now().minusDays(3);
        LocalDate till = LocalDate.now();
        HotelAgreementDto agreementDto = new HotelAgreementDto();
        agreementDto.setId("123");
        when(customerFacade.findReserved(from, till)).thenReturn(Stream.of(agreementDto).collect(Collectors.toSet()));
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        MvcResult result =

                mockMvc.perform(get(BASE_URL + "/reserved/from/" + f.format(from) + "/till/" + f.format(till))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Hi"))
                        .andExpect(status().isOk()).andReturn();

        String json = result.getResponse().getContentAsString();
        assertFalse(StringUtils.isEmpty(json));
        Set<HotelAgreementDto> agreement = objectMapper.readValue(json, new TypeReference<Set<HotelAgreementDto>>() {
        });
        assertNotNull(agreement);
    }

    @Bean
    public CustomerFacade getCustomerFacade() {
        return Mockito.mock(CustomerFacade.class);
    }
}