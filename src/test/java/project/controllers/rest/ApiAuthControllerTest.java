package project.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import project.dto.PasswordRecoveryDto;
import project.dto.RegisterDto;
import project.dto.UnauthorizedUserDTO;
import project.repositories.CaptchaCodeRepository;
import project.services.CaptchaCodeService;
import project.services.MailSender;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiAuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MailSender mailSender;

    @Autowired
    private CaptchaCodeService captchaCodeService;

    @MockBean
    private CaptchaCodeRepository captchaCodeRepository;

    @SneakyThrows
    @Test
    public void loginUser() {
        UnauthorizedUserDTO user = new UnauthorizedUserDTO();
        user.setEmail("asd@mail.ru");
        user.setPassword("qweasdzxc");

        String userJson = createJson(user);
        mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(31));
    }

    @SneakyThrows
    @Test
    public void checkUser() {
        mvc.perform(get("/api/auth/check")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(31));
    }

    @SneakyThrows
    @Test
    public void passwordRecovery() {
        PasswordRecoveryDto passwordRecoveryDto = new PasswordRecoveryDto();
        passwordRecoveryDto.setEmail("asd@mail.ru");

        String json = createJson(passwordRecoveryDto);
        mvc.perform(post("/api/auth/restore")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void getCaptcha() {
        mvc.perform(get("/api/auth/captcha")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    /**
     * Пустое тело ответа
     */
    @SneakyThrows
    @Test
    public void register() {
        RegisterDto register = new RegisterDto();

        register.setCaptcha("8968");
        register.setCaptchaSecret("asdwdasdweqwsda");
        register.setEmail("asdw@mail.ru");
        register.setPassword("qweasdzxc");

        String json = createJson(register);

        mvc.perform(get("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }



    @SneakyThrows
    public String createJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }
}