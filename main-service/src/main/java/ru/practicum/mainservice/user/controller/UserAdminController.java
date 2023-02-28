package ru.practicum.mainservice.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.user.dto.*;
import ru.practicum.mainservice.user.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserAdminController {

    private final UserService userService;

    @GetMapping
    public Collection<UserDto> get(@RequestParam (required = false) Collection<Integer> ids,
                                   @RequestParam (defaultValue = "0") int from,
                                   @RequestParam (defaultValue = "10") int size) {
        return userService.getByIds(ids, from, size);
    }

    @PostMapping
    public ResponseEntity<UserDto> add(@RequestBody @Valid NewUserRequest newUserRequest) {
        return userService.add(newUserRequest);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Object> delete(@PathVariable int userId) {
        return userService.delete(userId);
    }
}