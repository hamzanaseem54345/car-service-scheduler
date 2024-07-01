package com.voltMoney.carSchedulerService.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AppointmentRequestDTO {

    @NotNull(message = "Operator id should not be null")
    Long operatorId;
    Long startTime;
    Long endTime;
    String customerName;
    LocalDate date;
}
