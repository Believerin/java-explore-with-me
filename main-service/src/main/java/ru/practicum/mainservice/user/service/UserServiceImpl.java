package ru.practicum.mainservice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.exception.NoSuchBodyException;
import ru.practicum.mainservice.user.UserMapper;
import ru.practicum.mainservice.user.dto.*;
import ru.practicum.mainservice.user.model.User;
import ru.practicum.mainservice.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Collection<UserDto> getByIds(Collection<Integer> ids, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        Iterable<User> users;
        if (ids != null) {
            users = userRepository.findByIdIn(ids, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }
        Collection<UserDto> usersDto = new ArrayList<>();
        users.forEach(user -> usersDto.add(userMapper.toUserDto(user)));
        return usersDto;
    }

    @Transactional
    @Override
    public ResponseEntity<UserDto> add(NewUserRequest newUserRequest) {
        User user = userRepository.save(userMapper.toUser(newUserRequest));
        return new ResponseEntity<>(userMapper.toUserDto(user), HttpStatus.CREATED);
    }

    @Transactional
    @Override
    public ResponseEntity<Object> delete(int userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                     "message", "Пользователь с id = " + userId + " отсутствует",
                     "reason", "Указанный пользователь не найден",
                     "status",  HttpStatus.NOT_FOUND.name(),
                     "timeStamp", LocalDateTime.now())));
        userRepository.deleteById(userId);
        return ResponseEntity.status(204).build();
    }
}