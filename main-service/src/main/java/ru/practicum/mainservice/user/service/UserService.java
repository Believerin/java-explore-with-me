package ru.practicum.mainservice.user.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.mainservice.user.dto.*;

import java.util.Collection;

public interface UserService {
    Collection<UserDto> getByIds(Collection<Integer> ids, int from, int size);

    ResponseEntity<UserDto> add(NewUserRequest newUserRequest);

    ResponseEntity<Object> delete(int userId);
}