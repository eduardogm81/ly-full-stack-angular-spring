package es.quark.fullstatckangularspring.converter;

import es.quark.fullstatckangularspring.entity.ReservationEntity;
import es.quark.fullstatckangularspring.model.request.ReservationRequest;
import org.springframework.core.convert.converter.Converter;


public class ReservationRequestToReservationEntityConverter
    implements Converter<ReservationRequest, ReservationEntity> {


    @Override
    public ReservationEntity convert(ReservationRequest reservationRequest) {
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCheckin(reservationRequest.getCheckin());
        reservationEntity.setCheckout(reservationRequest.getCheckout());
        if (reservationRequest.getId() != null) {
            reservationEntity.setId(reservationRequest.getId());
        }

        return reservationEntity;
    }
}
