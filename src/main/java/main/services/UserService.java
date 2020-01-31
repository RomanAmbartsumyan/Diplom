package main.services;

import main.models.User;
import main.models.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис для работы с БД пользователей
 */
@Service
public class UserService {
    /**
     * Репозиторий пользователей
     */
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Выдает пользователя по id
     */
    public User getUserById(Integer id) {
        Optional<User> postById = userRepository.findById(id);
        return postById.orElse(null);
    }
}
