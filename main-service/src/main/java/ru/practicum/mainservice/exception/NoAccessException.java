package ru.practicum.mainservice.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class NoAccessException extends RuntimeException {

    private final Map<String, Object> parameter;

    public NoAccessException(Map<String, Object> parameter) {
        this.parameter = parameter;
    }
}