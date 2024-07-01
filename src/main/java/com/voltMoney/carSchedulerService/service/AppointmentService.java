package com.voltMoney.carSchedulerService.service;


import com.voltMoney.carSchedulerService.dto.AppointmentRequestDTO;
import com.voltMoney.carSchedulerService.model.Appointment;
import com.voltMoney.carSchedulerService.model.Slot;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    Appointment bookAppointment(AppointmentRequestDTO appointmentRequestDTO);

    void cancelAppointment(Long id);
    List<Appointment> getAppointments(Long serviceOperatorId);

    Optional<Appointment> rescheduleAppointment(Long id, Long newStartTime, Long newEndTime);
    List<Slot> getOpenSlots(long operatorId, LocalDate date);

    boolean isSlotAvailable(Long operatorId, Long startTime, Long endTime, LocalDate date);
}
