package ru.practicum.mainservice.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ReachedLimitException extends RuntimeException {

    private final Map<String, Object> parameter;

    public ReachedLimitException(Map<String, Object> parameter) {
        this.parameter = parameter;
    }
}