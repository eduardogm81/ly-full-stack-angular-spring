package es.quark.fullstatckangularspring.converter;

import es.quark.fullstatckangularspring.entity.RoomEntity;
import es.quark.fullstatckangularspring.model.Links;
import es.quark.fullstatckangularspring.model.Self;
import es.quark.fullstatckangularspring.model.response.ReservableRoomResponse;
import es.quark.fullstatckangularspring.rest.ResourceConstants;
import org.springframework.core.convert.converter.Converter;

public class RoomEntityToReservableRoomResponseConverter
        implements Converter<RoomEntity, ReservableRoomResponse> {

    @Override
    public ReservableRoomResponse convert(RoomEntity roomEntity) {

        ReservableRoomResponse response = new ReservableRoomResponse();
        response.setId(roomEntity.getId());
        response.setRoomNumber(roomEntity.getRoomNumber());
        response.setPrice(Integer.valueOf(roomEntity.getPrice()));
        Links links = new Links();
        Self self = new Self();
        self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/" + roomEntity.getId());
        links.setSelf(self);
        response.setLinks(links);
        return response;
    }
}
