package com.service.serviceapp.service.impl;

import com.service.serviceapp.config.Exception.BusinessException;
import com.service.serviceapp.dao.ReservationDao;
import com.service.serviceapp.dao.UserDao;
import com.service.serviceapp.model.Reservation;
import com.service.serviceapp.model.ReservationDto;

import com.service.serviceapp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service(value = "reservationService")
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ReservationDao reservationDao;

    @Override
    public Reservation addReservation(ReservationDto reservationDto) throws BusinessException {

        if (Objects.isNull(userDao.findByUsername(reservationDto.getUsername()))) {
            throw new BusinessException(401, "Please use username used for registration.");
        }

        if (reservationDao.existsByMonthAndDayAndHourAndYear(reservationDto.getMonth(),
                reservationDto.getDay(),
                reservationDto.getHour(),
                reservationDto.getYear())) {
            throw new BusinessException(401, "Already booked.Please pick another date");
        }

        if (!Objects.isNull(reservationDto.getYear())) {
            String regex = "[0-9]+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(reservationDto.getYear());
            if (!matcher.matches()) {
                throw new BusinessException(401, "Year should be in correct form");
            }
        }

        if (!Objects.isNull(reservationDto.getMonth())) {
            String regex = "[0-9]+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(reservationDto.getMonth());
            if (!matcher.matches()) {
                throw new BusinessException(401, "Month should be in correct form");
            }
        }

        if (!Objects.isNull(reservationDto.getDay())) {
            String regex = "[0-9]+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(reservationDto.getDay());
            if (!matcher.matches()) {
                throw new BusinessException(401, "Day should be in correct form");
            }
        }

        if (!Objects.isNull(reservationDto.getHour())) {
            String regex = "[0-9]+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(reservationDto.getHour());
            if (!matcher.matches()) {
                throw new BusinessException(401, "Hour should be in correct form");
            }
        }

        return reservationDao.save(new Reservation(
                reservationDto.getYear(),
                reservationDto.getMonth(),
                reservationDto.getDay(),
                reservationDto.getHour(),
                userDao.findByUsername(reservationDto.getUsername())));
    }

    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        reservationDao.findAll().forEach(reservations::add);
        return reservations;
    }

    @Override
    public void deleteById(Long id) {
        reservationDao.deleteById(id);
    }

    @Override
    public Reservation update(Reservation reservation, Long id) {
        Reservation reservationUpdate = reservationDao.getById(id);
        if (!(reservation.getMonth() == null)) {
            reservationUpdate.setMonth(reservation.getMonth());
        }
        if (!(reservation.getDay() == null)) {
            reservationUpdate.setDay(reservation.getDay());
        }
        if (!(reservation.getHour() == null)) {
            reservationUpdate.setHour(reservation.getHour());
        }
        if (!(reservation.getYear() == null)) {
            reservationUpdate.setYear(reservation.getYear());
        }

        return reservationDao.save(reservationUpdate);
    }
}
