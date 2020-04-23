package project.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import project.models.CaptchaCode;
import project.repositories.CaptchaCodeRepository;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CaptchaCodeServiceTest {

    @Autowired
    private CaptchaCodeService service;

    @MockBean
    private CaptchaCodeRepository repository;

    @Test
    public void createCaptcha() {
        CaptchaCode captchaCode = service.createCaptcha();
        verify(repository, times(1)).save(captchaCode);
    }

    @Test
    public void isValid() {
        boolean isValid = service.isValid("sdakwdlwdk", "kwjdkawjdwaoid");
        assertFalse(isValid);
    }

    @Test
    public void getCaptchaDto() {
        service.getCaptchaDto();
        verify(repository, times(1)).save(any());
    }

    @Test
    public void deleteCaptcha() {
        LocalDateTime now = LocalDateTime.now();
        service.deleteCaptcha(now);
        verify(repository, times(1)).deleteAllByTimeBefore(now);
    }
}