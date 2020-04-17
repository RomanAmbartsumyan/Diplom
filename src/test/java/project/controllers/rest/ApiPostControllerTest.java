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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import project.dto.AddPostDto;
import project.dto.UnauthorizedUserDTO;
import project.repositories.PostRepository;
import project.services.AuthService;
import project.services.PostService;

import javax.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = "/application-test.properties")
public class ApiPostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private AuthService authService;


    @Autowired
    private WebApplicationContext wac;

    protected MockHttpSession session;

    protected MockHttpServletRequest request;



    @SneakyThrows
    @Test
    public void postList() {
        mvc.perform(get("/api/post").contentType(MediaType.APPLICATION_JSON)
                .param("offset", "0")
                .param("limit", "10")
                .param("mode", "recent"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @SneakyThrows
    @Test
    public void addPost() {
        UnauthorizedUserDTO user = new UnauthorizedUserDTO();
        user.setEmail("asd@mail.ru");
        user.setPassword("qweasdzxc");

        String userJson = createJson(user);
        HttpSession session = mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getRequest()
                .getSession();

        AddPostDto addPostDto = new AddPostDto();
        addPostDto.setText("asdwdasdawdwadasscwdacawawdawd");
        addPostDto.setTitle("akwdwhdkjwakdjhjawhd");
        addPostDto.setTime("2020-03-15 15:34");


        String addPostJson = createJson(addPostDto);


        mvc.perform(post("/api/post")
                .contentType(MediaType.APPLICATION_JSON).content(addPostJson))
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