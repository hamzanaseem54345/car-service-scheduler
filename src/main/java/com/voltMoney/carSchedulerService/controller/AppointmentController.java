package com.voltMoney.carSchedulerService.controller;


import com.voltMoney.carSchedulerService.dto.AppointmentRequestDTO;
import com.voltMoney.carSchedulerService.model.Appointment;
import com.voltMoney.carSchedulerService.model.Slot;
import com.voltMoney.carSchedulerService.service.AppointmentService;
import com.voltMoney.carSchedulerService.service.ServiceOperatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ServiceOperatorService serviceOperatorService;

    @PostMapping("/book")
    public Appointment bookAppointment(@Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO){
        return appointmentService.bookAppointment(appointmentRequestDTO);
    }

    @PostMapping("/reschedule/{id}")
    public Optional<Appointment> rescheduleAppointment(@PathVariable Long id,
                                                       @RequestParam String newStartTime,
                                                       @RequestParam String newEndTime) {
        Long newStartTimeL = Long.parseLong(newStartTime);
        Long newEndTimeL= Long.parseLong(newEndTime);
        return  appointmentService.rescheduleAppointment(id, newStartTimeL, newEndTimeL);
    }

    @DeleteMapping("/cancel/{id}")
    public void cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
    }

    @GetMapping("/operator/{operatorId}")
    public List<Appointment> getAppointments(@PathVariable Long operatorId) {
        return appointmentService.getAppointments(operatorId);
    }

    @GetMapping("/available-slots")
    public List<Slot> getAvailableSlots(@RequestParam long operatorId, @RequestParam String date) {
        LocalDate appointmentDate = LocalDate.parse(date);

        return appointmentService.getOpenSlots(operatorId, appointmentDate);
    }
}
