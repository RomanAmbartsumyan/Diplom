package project.controllers.rest;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiGeneralControllerTest {

    @Autowired
    private MockMvc mvc;

    @SneakyThrows
    @Test
    public void mainPage() {
        mvc.perform(get("/api/init")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void postList() {
        mvc.perform(get("/api/post")
                .param("offset", "0")
                .param("limit", "10")
                .param("mode", "recent"))
                .andExpect(status().isOk());
    }
}