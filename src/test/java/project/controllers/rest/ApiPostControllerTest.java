package project.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import project.dto.AddPostDto;
import project.dto.PostVoteDto;
import project.dto.UnauthorizedUserDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = "/application-test.properties")
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/data_test.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/clean.sql")
})
public class ApiPostControllerTest {

    @Autowired
    private MockMvc mvc;

    private final MockHttpSession session = new MockHttpSession();

    @SneakyThrows
    @Before
    public void setUp() {
        UnauthorizedUserDTO user = new UnauthorizedUserDTO();
        user.setEmail("r9854334307@mail.ru");
        user.setPassword("qweasdzxc");

        String userJson = createJson(user);
        mvc.perform(post("/api/auth/login")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

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
        String[] tags = new String[]{"qwe", "asd"};
        AddPostDto addPostDto = new AddPostDto();
        addPostDto.setText("asdwdasdawdwadasscwdacawawdawd");
        addPostDto.setTitle("akwdwhdkjwakdjhjawhd");
        addPostDto.setTime("2020-03-15 15:34");
        addPostDto.setTags(tags);

        String addPostJson = createJson(addPostDto);

        mvc.perform(post("/api/post")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON).content(addPostJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void postsBySearch() {
        mvc.perform(get("/api/post/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("offset", "0")
                .param("limit", "10")
                .param("query", "magna"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void getPostById() {
        mvc.perform(get("/api/post/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void editPost() {
        String[] tags = new String[]{"hello", "world"};
        AddPostDto addPostDto = new AddPostDto();
        addPostDto.setText("asdwdasdawdwadasscwdacawawdawd");
        addPostDto.setTitle("akwdwhdkjwakdjhjawhd");
        addPostDto.setTime("2020-03-15 15:34");
        addPostDto.setTags(tags);

        String addPostJson = createJson(addPostDto);

        mvc.perform(put("/api/post/1")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(addPostJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void getPostsByDate() {
        mvc.perform(get("/api/post/byDate")
                .contentType(MediaType.APPLICATION_JSON)
                .param("offset", "0")
                .param("limit", "10")
                .param("date", "2019-07-17 21:32:39"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void getPostsByTagName() {
        mvc.perform(get("/api/post/byTag")
                .contentType(MediaType.APPLICATION_JSON)
                .param("offset", "0")
                .param("limit", "10")
                .param("tag", "HELLO"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void getPostsListOnModeration() {
        mvc.perform(get("/api/post/moderation")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .param("offset", "0")
                .param("limit", "10")
                .param("status", "new"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void getMyPosts() {
        mvc.perform(get("/api/post/my")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .param("offset", "0")
                .param("limit", "10")
                .param("status", "inactive"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void addLike() {
        PostVoteDto dto = new PostVoteDto();
        dto.setPostId(1);

        String json = createJson(dto);

        mvc.perform(post("/api/post/like")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void addDislike() {
        PostVoteDto dto = new PostVoteDto();
        dto.setPostId(2);

        String json = createJson(dto);

        mvc.perform(post("/api/post/dislike")
                .session(session)
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