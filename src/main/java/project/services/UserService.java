package project.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
     * Выдает пользователя по id в формате UserDto
     */
    public UserDto getUserDtoById(Integer id) {
        Optional<User> userDtoById = userRepository.findById(id);
        return userDtoById.map(user -> new UserDto(user.getId(), user.getName()))
                .orElse(null);
    }

    /**
     * Выдает пользователя по id
     */
    public User getUserById(Integer id){
        if(id != null){
            Optional<User> userById = userRepository.findById(id);
            return userById.orElse(null);
        }
        return null;
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
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            boolean auth = new BCryptPasswordEncoder().matches(password, optionalUser.get().getPassword());
            if(auth){
                return optionalUser.get();
            }
            return null;
        }
        return null;
    }

}
