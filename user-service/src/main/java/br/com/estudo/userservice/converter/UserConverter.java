package br.com.estudo.userservice.converter;

import br.com.estudo.userservice.dto.UserDto;
import br.com.estudo.userservice.entity.UserEntity;
import java.util.Optional;

public class UserConverter {


    public static UserDto toDto(UserEntity user) {
        return new UserDto(user.getId(), user.getName(), user.getBalance());
    }

    public static UserEntity toEntity(UserDto userDto) {
        return new UserEntity(userDto.getId(), userDto.getName(), userDto.getBalance());
    }

    public static UserDto updatePartial(UserDto userBased, UserDto userUpdated) {
        Optional.ofNullable(userUpdated.getName()).ifPresent(userBased::setName);
        Optional.ofNullable(userUpdated.getBalance()).ifPresent(userBased::setBalance);
        return userBased;

    }
}
