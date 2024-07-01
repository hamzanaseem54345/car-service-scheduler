package com.voltMoney.carSchedulerService.model;


import lombok.*;
import org.springframework.stereotype.Component;


@Data

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class Slot {
        private Integer startHour;
        private Integer endHour;
    }
