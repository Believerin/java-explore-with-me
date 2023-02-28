package ru.practicum.mainservice.user;

import org.mapstruct.Mapper;
import ru.practicum.mainservice.user.dto.NewUserRequest;
import ru.practicum.mainservice.user.dto.UserDto;
import ru.practicum.mainservice.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserDto source);

    User toUser(NewUserRequest source);

    UserDto toUserDto(User destination);
}