package project.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.dto.responce.UserDto;
import project.dto.responce.UserWithPhotoInformationDto;
import project.models.User;
import project.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

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
     * Отправка почты
     */
    private MailSender mailSender;


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
    public UserWithPhotoInformationDto getFullInformationById(Integer id) {
        Optional<User> userById = userRepository.findById(id);
        return userById.map(user -> new UserWithPhotoInformationDto(user.getId(), user.getName(), user.getPhoto()))
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

    public void createUser(String email, String name, String passwordFromUser){
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(passwordFromUser);
        user.setPassword(password);
        userRepository.save(user);
    }

    /**
     * Проверка пользователя в базе для восстановления пароля
     * true - пользователь найден, сгенирирован код и отправлено письмо на почту
     * false - пользователь не найден
     */
    public boolean isPasswordChanged(String email){
        User userFromDb = userRepository.findByEmail(email).orElse(null);
        if(userFromDb != null){
            String hashCode = UUID.randomUUID().toString();
            userFromDb.setCode(hashCode);
            String passwordRecovery = "<a href=\"url\">http://localhost:8080/login/change-password/"
                    + hashCode + "</a>";
            mailSender.send(userFromDb.getEmail(), "Восстановление пароля", passwordRecovery);
            return true;
        }
        return false;
    }

    public void changePassword(String code, String password){
        User user = userRepository.findByCode(code);
        user.setPassword(password);
        userRepository.save(user);
    }

}
