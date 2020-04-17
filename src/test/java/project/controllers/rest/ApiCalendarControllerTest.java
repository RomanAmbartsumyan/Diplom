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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiCalendarControllerTest {


    @Autowired
    private MockMvc mvc;

    @SneakyThrows
    @Test
    public void getPostsByDate() {
        mvc.perform(get("/api/calendar")
                .contentType(MediaType.APPLICATION_JSON)
                .param("year", "2020"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}