package project.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.dto.ProfileDto;
import project.dto.UserDto;
import project.dto.UserWithPhotoInformationDto;
import project.exceptions.BadRequestException;
import project.exceptions.NotFountException;
import project.models.User;
import project.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
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
    private final UserRepository userRepository;
    /**
     * Отправка почты
     */
    private final MailSender mailSender;

    @PostConstruct
    public void init(){
        User createUser = new User();
        createUser.setEmail("r9854334307@mail.ru");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("qweasdzxc");
        createUser.setPhoto("img/default.c66f8640.jpg");
        createUser.setPassword(password);
        createUser.setModerator((byte) 1);
        createUser.setName("Roman");
        createUser.setRegTime(LocalDateTime.now());
        userRepository.save(createUser);
    }


    /**
     * Выдает пользователя по id в формате UserDto
     */
    public UserDto getUserDtoById(Integer id) {
        Optional<User> userDtoById = userRepository.findById(id);
        return userDtoById.map(user -> new UserDto(user.getId(), user.getName()))
                .orElseThrow(NotFountException::new);
    }

    /**
     * Выдает пользователя по id
     */
    public User getUserById(Integer id) {
        if (id != null) {
            Optional<User> userById = userRepository.findById(id);
            return userById.orElseThrow(NotFountException::new);
        }
        return null;
    }


    /**
     * Выдает пользователя по id автора
     */
    public UserWithPhotoInformationDto getUserWithPhotoInformationById(Integer id) {
        Optional<User> userById = userRepository.findById(id);
        return userById.map(user -> new UserWithPhotoInformationDto(user.getId(), user.getName(), user.getPhoto()))
                .orElseThrow(NotFountException::new);
    }

    /**
     * Выдает пользователя по email и поролю
     */
    public User getUserByEmailAndPassword(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            boolean auth = new BCryptPasswordEncoder().matches(password, optionalUser.get().getPassword());
            if (auth) {
                return optionalUser.get();
            }
        }
        throw new BadRequestException();
    }

    public User createUser(String email, String passwordFromUser, String name) {
        User createUser = new User();
        createUser.setEmail(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(passwordFromUser);
        createUser.setPhoto("img/default.c66f8640.jpg");
        createUser.setPassword(password);
        createUser.setModerator((byte) 0);
        createUser.setName(name);
        createUser.setRegTime(LocalDateTime.now());
        userRepository.save(createUser);
        return createUser;
    }

    public void editUserProfile(User user, ProfileDto dto, String img) {
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        if (dto.getPassword() != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String editPassword = passwordEncoder.encode(dto.getPassword());
            user.setPassword(editPassword);
        }
        if(img != null){
            user.setPhoto(img);
        }
        userRepository.save(user);
    }

    /**
     * Проверка пользователя в базе для восстановления пароля
     * true - пользователь найден, сгенирирован код и отправлено письмо на почту
     * false - пользователь не найден
     */
    public boolean isPasswordChanged(String email) {
        User userFromDb = userRepository.findByEmail(email).orElse(null);
        if (userFromDb != null) {
            String hashCode = UUID.randomUUID().toString();
            userFromDb.setCode(hashCode);
            String url = "http://localhost:8080/login/change-password/" + hashCode;
            String message = "<a href=\"" + url + "\">Восстановить пароль</a>";
            mailSender.send(userFromDb.getEmail(), "Восстановление пароля", message);
            userRepository.save(userFromDb);
            return true;
        }
        return false;
    }

    public User changePassword(String code, String password) {
        User user = userRepository.findByCode(code);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(password);
        user.setPassword(encodePassword);
        userRepository.save(user);
        return user;
    }

    public boolean isUserByEmailPresent(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
