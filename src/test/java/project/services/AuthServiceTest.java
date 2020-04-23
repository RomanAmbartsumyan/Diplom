package project.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.exceptions.UnauthorizedException;

import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private  AuthService authService;

    @Mock
    private Map<String, Integer> authUsers;

    @Before
    public void setUp(){
        authService = new AuthService(authUsers);
    }

    @Test
    public void saveSession() {
        authService.saveSession("12", 123);
        authService = new AuthService(authUsers);
        verify(authUsers, times(1)).put(any(), any());
    }

    @Test
    public void getUserId() {
        authService.getUserId();
        verify(authUsers, times(1)).get("2");
    }

    @Test(expected = UnauthorizedException.class)
    public void checkSession() {
        authService.checkSession();
    }

    @Test
    public void logout() {
        authService.logout();
        verify(authUsers, times(1)).remove("1");
    }
}