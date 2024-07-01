package com.voltMoney.carSchedulerService.service;

import com.voltMoney.carSchedulerService.dto.AppointmentRequestDTO;
import com.voltMoney.carSchedulerService.exception.BadRequestException;
import com.voltMoney.carSchedulerService.model.Appointment;
import com.voltMoney.carSchedulerService.model.ServiceOperator;
import com.voltMoney.carSchedulerService.model.Slot;
import com.voltMoney.carSchedulerService.respository.AppointmentRepository;
import com.voltMoney.carSchedulerService.respository.ServiceOperatorRepository;
import com.voltMoney.carSchedulerService.utility.UtilHelperMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ServiceOperatorRepository serviceOperatorRepository;

    @Override
    public Appointment bookAppointment(AppointmentRequestDTO appointmentRequestDTO)  {
        // Convert LocalDateTime to milliseconds since epoch (assuming UtilHelperMethod provides this functionality

        if (!isSlotAvailable(appointmentRequestDTO.getOperatorId(), appointmentRequestDTO.getStartTime(), appointmentRequestDTO.getEndTime(), appointmentRequestDTO.getDate())) {
            throw new BadRequestException("The requested slot is already booked."); // Handle slot unavailability
        }

        ServiceOperator serviceOperator = serviceOperatorRepository.getById(appointmentRequestDTO.getOperatorId());

        Appointment appointment = Appointment.builder()
                .serviceOperator(serviceOperator)
                .startTime(appointmentRequestDTO.getStartTime())
                .endTime(appointmentRequestDTO.getEndTime())
                .date(appointmentRequestDTO.getDate()) // Set the date from the DTO
                .build();

        return appointmentRepository.save(appointment);
    }

    @Override
    public void cancelAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> getAppointments(Long serviceOperatorId) {
        ServiceOperator serviceOperator = serviceOperatorRepository.getById(serviceOperatorId);
        return appointmentRepository.findByServiceOperator(serviceOperator);
    }

    public List<Slot> getOpenSlots(long operatorId, LocalDate date) {
        List<Appointment> bookedAppointments = appointmentRepository.findByServiceOperatorIdAndDate(operatorId, date);
        List<Slot> bookedSlots = bookedAppointments.stream().map(e -> Slot.builder().startHour(getHourFromEpoch(e.getStartTime())).endHour(getHourFromEpoch(e.getEndTime())).build()).collect(Collectors.toList());
        List<Slot> defaultSlots = IntStream.rangeClosed(0, 23)   // Generates integers from 0 to 23
                .mapToObj(i -> new Slot(i, i + 1))  // Maps each integer to a Slot object (i, i+1)
                .collect(Collectors.toList());

        List<Slot> filteredSlots = defaultSlots.stream()
                .filter(slot -> bookedSlots.stream().noneMatch(bookedSlot -> bookedSlot.equals(slot)))
                .collect(Collectors.toList());

        return consolidateConsecutiveSlots(filteredSlots);
    }

    public List<Slot> consolidateConsecutiveSlots(List<Slot> slots) {
        List<Slot> consolidatedSlots = new ArrayList<>();

        if (slots.isEmpty()) {
            return consolidatedSlots;
        }

        // Sort slots by start hour
        slots.sort(Comparator.comparingInt(Slot::getStartHour));

        // Initialize variables to track current consolidated slot
        int currentStartHour = slots.get(0).getStartHour();
        int currentEndHour = slots.get(0).getEndHour();

        // Iterate through sorted slots and merge consecutive slots
        for (int i = 1; i < slots.size(); i++) {
            Slot currentSlot = slots.get(i);

            // If the current slot can be merged with the current consolidated slot
            if (currentSlot.getStartHour() <= currentEndHour) {
                // Extend the current consolidated slot
                currentEndHour = Math.max(currentEndHour, currentSlot.getEndHour());
            } else {
                // Add the current consolidated slot to the result list
                consolidatedSlots.add(new Slot(currentStartHour, currentEndHour));

                // Start a new consolidated slot
                currentStartHour = currentSlot.getStartHour();
                currentEndHour = currentSlot.getEndHour();
            }
        }

        // Add the last consolidated slot
        consolidatedSlots.add(new Slot(currentStartHour, currentEndHour));

        return consolidatedSlots;
    }

    public static int getHourFromEpoch(long epochMillis) {
        // Convert epoch milliseconds to LocalDateTime in UTC
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(epochMillis), ZoneOffset.UTC);

        // Extract hour part from LocalDateTime in UTC
        return dateTime.getHour();

    }


    @Override
    public Optional<Appointment> rescheduleAppointment(Long id, Long newStartTime, Long newEndTime) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setStartTime(newStartTime);
            appointment.setEndTime(newEndTime);
            appointmentRepository.save(appointment);
            return Optional.of(appointment);
        }
        return Optional.empty();
    }

    public boolean isSlotAvailable(Long operatorId, Long startTime, Long endTime, LocalDate date) {
        List<Appointment> bookedAppointments = appointmentRepository.findByServiceOperatorIdAndDate(operatorId, date);

        LocalDateTime slotStartDateTime = UtilHelperMethod.convertLongToDateTime(startTime);
        LocalDateTime slotEndDateTime = UtilHelperMethod.convertLongToDateTime(endTime);

        for (Appointment bookedAppointment : bookedAppointments) {
            LocalDateTime bookedStartDateTime = UtilHelperMethod.convertLongToDateTime(bookedAppointment.getStartTime());
            LocalDateTime bookedEndDateTime = UtilHelperMethod.convertLongToDateTime(bookedAppointment.getEndTime());

            // Check for overlap
            if (bookedAppointment.getDate().equals(date) &&
                    !(slotEndDateTime.isBefore(bookedStartDateTime) || slotStartDateTime.isAfter(bookedEndDateTime))) {
                return false; // Slot overlaps with booked appointment
            }
        }

        return true; // Slot does not overlap with any booked appointment
    }
}

