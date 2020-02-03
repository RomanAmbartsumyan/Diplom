package main.services;

import lombok.AllArgsConstructor;
import main.dto.responce.UserDto;
import main.dto.responce.UserFullInformation;
import main.models.User;
import main.models.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис для работы с БД пользователей
 */
@Service
@AllArgsConstructor
public class UserService {
    /**
     * Репозиторий пользователей
     */
    private UserRepository userRepository;

    /**
     * Выдает пользователя по id
     */
    public UserDto getUserById(Integer id) {
        Optional<User> postById = userRepository.findById(id);
        return postById.map(user -> new UserDto(user.getId(), user.getName()))
                .orElse(null);
    }

    /**
     * Выдает пользователя по id аватаром
     */
    public UserFullInformation getFullInformationById(Integer id) {
        Optional<User> postById = userRepository.findById(id);
        return postById.map(user -> new UserFullInformation(user.getId(), user.getName(), user.getPhoto()))
                .orElse(null);
    }
}
