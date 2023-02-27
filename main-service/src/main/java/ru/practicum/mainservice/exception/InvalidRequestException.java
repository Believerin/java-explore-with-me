package ru.practicum.mainservice.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class InvalidRequestException extends RuntimeException {
    private final Map<String, Object> parameter;

    public InvalidRequestException(Map<String, Object> parameter) {
        this.parameter = parameter;
    }
}