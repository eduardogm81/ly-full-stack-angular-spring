package es.quark.fullstatckangularspring.rest;

import es.quark.fullstatckangularspring.converter.RoomEntityToReservableRoomResponseConverter;
import es.quark.fullstatckangularspring.entity.ReservationEntity;
import es.quark.fullstatckangularspring.entity.RoomEntity;
import es.quark.fullstatckangularspring.model.request.ReservationRequest;
import es.quark.fullstatckangularspring.model.response.ReservableRoomResponse;
import es.quark.fullstatckangularspring.model.response.ReservationResponse;
import es.quark.fullstatckangularspring.repository.PageableRoomRepository;
import es.quark.fullstatckangularspring.repository.ReservationRepository;
import es.quark.fullstatckangularspring.repository.RoomRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(ResourceConstants.ROOM_RESERVATION_V1)
@CrossOrigin
public class ReservationResource {

    private PageableRoomRepository pageableRoomRepository;
    private RoomRepository roomRepository;
    private ReservationRepository reservationRepository;
    private ConversionService conversionService;

    public ReservationResource(PageableRoomRepository pageableRoomRepository, RoomRepository roomRepository,
                               ReservationRepository reservationRepository, ConversionService conversionService) {
        this.pageableRoomRepository = pageableRoomRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.conversionService = conversionService;
    }

    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<ReservableRoomResponse> getAvailableRooms(
            @RequestParam(value = "checkin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
            @RequestParam(value = "checkout") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout,
            Pageable pageable) {
        Page<RoomEntity> roomEntityList = pageableRoomRepository.findAll(pageable);
        // return new ResponseEntity<>(new ReservableRoomResponse(), HttpStatus.OK);
        return roomEntityList.map(new RoomEntityToReservableRoomResponseConverter());
    }

    @RequestMapping(path = "/{roomId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RoomEntity> getRoomById(@PathVariable Long roomId) {
        return new ResponseEntity<>(roomRepository.findById(roomId), HttpStatus.OK);
    }


    @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        ReservationEntity reservationEntity = conversionService.convert(reservationRequest, ReservationEntity.class);
        reservationRepository.save(reservationEntity);
        RoomEntity roomEntity = roomRepository.findById(reservationRequest.getRoomId());
        roomEntity.addReservationEntity(reservationEntity);
        roomRepository.save(roomEntity);
        reservationEntity.setRoomEntity(roomEntity);
        ReservationResponse reservationResponse = conversionService.convert(reservationEntity, ReservationResponse.class);

        return new ResponseEntity<>(reservationResponse, HttpStatus.CREATED);
    }

    @RequestMapping(path = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReservableRoomResponse> updateReservation(@RequestBody ReservationRequest reservationRequest) {

        return new ResponseEntity<>(new ReservableRoomResponse(), HttpStatus.OK);
    }

    @RequestMapping(path = "/{reservationId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteReservation(@PathVariable long reservationId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
