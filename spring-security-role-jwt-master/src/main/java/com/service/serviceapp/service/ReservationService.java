package com.service.serviceapp.service;

import com.service.serviceapp.config.Exception.BusinessException;
import com.service.serviceapp.model.Reservation;
import com.service.serviceapp.model.ReservationDto;

import java.util.List;

public interface ReservationService {

    Reservation addReservation(final ReservationDto reservationDto) throws BusinessException;

    List<Reservation> getAllReservations();

    void deleteById(final Long id);

    Reservation update(Reservation reservation, Long id);
}
