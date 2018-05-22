package es.quark.fullstatckangularspring.converter;

import es.quark.fullstatckangularspring.entity.ReservationEntity;
import es.quark.fullstatckangularspring.model.response.ReservationResponse;
import org.springframework.core.convert.converter.Converter;


public class ReservationEntityToReservationResponseConverter
    implements Converter<ReservationEntity, ReservationResponse> {
    @Override
    public ReservationResponse convert(ReservationEntity reservationEntity) {
        ReservationResponse response = new ReservationResponse();
        response.setCheckin(reservationEntity.getCheckin());
        response.setCheckout(reservationEntity.getCheckout());
        response.setId(reservationEntity.getId());

        return response;
    }
}
