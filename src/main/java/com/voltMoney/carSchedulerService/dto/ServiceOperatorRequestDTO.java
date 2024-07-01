package com.voltMoney.carSchedulerService.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ServiceOperatorRequestDTO {

    private String name;
}
