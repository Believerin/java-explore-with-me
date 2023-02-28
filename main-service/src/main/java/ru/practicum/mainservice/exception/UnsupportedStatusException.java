package ru.practicum.mainservice.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class UnsupportedStatusException extends RuntimeException {

    private final Map<String, Object> parameter;

    public UnsupportedStatusException(Map<String, Object> parameter) {
        this.parameter = parameter;
    }
}