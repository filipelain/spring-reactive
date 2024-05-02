package br.com.estudo.userservice.service;

import br.com.estudo.userservice.converter.UserConverter;
import br.com.estudo.userservice.dto.UserDto;
import br.com.estudo.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public Flux<UserDto> finAll() {
        return userRepository.findAll()
                .map(UserConverter::toDto);
    }

    public Mono<UserDto> findById(Integer id) {
        return userRepository.findById(id)
                .map(UserConverter::toDto);
    }

    public Mono<UserDto> create(Mono<UserDto> userDto) {
        return userDto.flatMap(this::save);
    }

    public Mono<UserDto> update(Integer id, Mono<UserDto> userDtoMono) {
        return findById(id)
                .flatMap(user -> userDtoMono.doOnNext(e -> e.setId(id)))
                .flatMap(this::save);
    }

    public Mono<UserDto> updatePartial(Integer id, Mono<UserDto> userDtoMono) {
        return userDtoMono.flatMap(userDto -> findById(id)
                .map(user -> UserConverter.updatePartial(user, userDto))
                .flatMap(this::save));
    }

    public Mono<Void> delete(Integer id) {
        return userRepository.findById(id)
                .flatMap(userRepository::delete);
    }

    private Mono<UserDto> save(UserDto userDto) {
        return userRepository.save(UserConverter.toEntity(userDto))
                .map(UserConverter::toDto);
    }

}
