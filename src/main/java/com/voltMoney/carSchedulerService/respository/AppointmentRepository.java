package com.voltMoney.carSchedulerService.respository;

import com.voltMoney.carSchedulerService.model.Appointment;
import com.voltMoney.carSchedulerService.model.ServiceOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByServiceOperator(ServiceOperator serviceOperator);

    List<Appointment> findByServiceOperatorIdAndDate(long operatorId, LocalDate date);
}

