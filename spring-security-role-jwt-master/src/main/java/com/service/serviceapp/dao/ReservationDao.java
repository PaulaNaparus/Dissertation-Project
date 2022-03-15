package com.service.serviceapp.dao;

import com.service.serviceapp.model.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationDao  extends CrudRepository<Reservation,Long> {

        Reservation save(Reservation reservation);
        boolean existsByMonthAndDayAndHourAndYear(String month,String day,String hour,String year);
        Reservation getById(final Long id);


}
