package com.voltMoney.carSchedulerService.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{


    public BadRequestException(final String message) {
        super(message);
    }
}
