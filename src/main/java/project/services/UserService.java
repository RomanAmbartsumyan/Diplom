package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.dto.responce.UserDto;
import project.dto.responce.UserFullInformation;
import project.models.User;
import project.repositories.UserRepository;

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
