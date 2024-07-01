package com.voltMoney.carSchedulerService.utility;


import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.time.LocalTime.ofInstant;

@Component
public class UtilHelperMethod {


    public static Long convertLocalDateTimeToEpoch(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime convertLongToDateTime (long epochMillis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), ZoneId.systemDefault());
    }

}
