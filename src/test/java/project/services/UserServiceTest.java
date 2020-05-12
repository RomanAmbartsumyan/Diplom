package project.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import project.dto.ProfileDto;
import project.dto.UserDto;
import project.dto.UserWithPhotoInformationDto;
import project.models.User;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/data_test.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/clean.sql")
})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUserDtoById() {
        UserDto user = userService.getUserDtoById(1);
        assertEquals("Roman", user.getName());
    }

    @Test
    public void getUserById() {
        User user = userService.getUserById(2);
        assertEquals("Artem", user.getName());
    }

    @Test
    public void getUserWithPhotoInformationById() {
        UserWithPhotoInformationDto user = userService.getUserWithPhotoInformationById(1);
        assertEquals("Roman", user.getName());
    }

    @Test
    public void getUserByEmailAndPassword() {
        User user = userService.getUserByEmailAndPassword("r9854334307@mail.ru", "qweasdzxc");
        assertEquals("Roman", user.getName());
    }

    @Test
    public void createUser() {
        User user = userService.createUser("asd@mail.ru", "qweasd", "Hello world");
        assertEquals("asd@mail.ru", user.getEmail());
    }

    @Test
    public void isPasswordChanged() {
        boolean isPasswordChanged = userService.isPasswordChanged("r9854334307@mail.ru");
        assertTrue(isPasswordChanged);
    }

    @Test
    public void changePassword() {
    }

    @Test
    public void isUserByEmailPresent() {
        boolean isPresent = userService.isUserByEmailPresent("asdasw@mail.ru");
        assertFalse(isPresent);
    }

    @Test
    public void editUserProfile() {
        User user = userService.createUser("asd@mail.ru", "qweasd", "Hello world");
        ProfileDto dto = new ProfileDto();
        dto.setName("qwe");
        userService.editUserProfileWithPhoto(user, dto, null);

        assertEquals("qwe", user.getName());
    }
}