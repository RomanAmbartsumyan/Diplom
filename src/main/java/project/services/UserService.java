package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.dto.responce.UserDto;
import project.dto.responce.UserWithPhotoInformation;
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

//    @PostConstruct
//    public void init(){
//        User user = new User();
//        user.setPassword("u");
//        user.setModerator((byte) 1);
//        user.setEmail("qwe@mail.ru");
//        user.setPassword("u");
//        userRepository.save(user);
//    }
    /**
     * Выдает пользователя по id
     */
    public UserDto getUserById(Integer id) {
        Optional<User> userById = userRepository.findById(id);
        return userById.map(user -> new UserDto(user.getId(), user.getName()))
                .orElse(null);
    }

    /**
     * Выдает пользователя по id автора
     */
    public UserWithPhotoInformation getFullInformationById(Integer id) {
        Optional<User> userById = userRepository.findById(id);
        return userById.map(user -> new UserWithPhotoInformation(user.getId(), user.getName(), user.getPhoto()))
                .orElse(null);
    }

    /**
     * Выдает пользователя по email и поролю
     */
    public User getUserByEmailAndPassword(String email, String password){
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, password);
        return optionalUser.orElse(null);
    }

}
