package project.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import project.models.Tag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/data_test.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/clean.sql")
})
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Test
    public void saveTag() {
        Tag tag = tagService.saveTag("qwe");
        assertEquals("qwe", tag.getName());
    }

    @Test
    public void getTagById() {
        Tag tag = tagService.getTagById(1);
        assertEquals("JAVA", tag.getName());
    }

    @Test
    public void getByName() {
        Tag tag = tagService.getByName("aldwkldlkaw");
        assertNull(tag);
    }

}