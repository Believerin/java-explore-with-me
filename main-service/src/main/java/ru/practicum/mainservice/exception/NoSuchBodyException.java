package ru.practicum.mainservice.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class NoSuchBodyException extends RuntimeException {
    private final Map<String, Object> parameter;

    public NoSuchBodyException(Map<String, Object> parameter) {
        this.parameter = parameter;
    }
}