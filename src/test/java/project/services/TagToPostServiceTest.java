package project.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import project.models.TagToPost;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/data_test.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/clean.sql")
})
public class TagToPostServiceTest {

    @Autowired
    private TagToPostService tagToPostService;

    @Test
    public void saveTagToPost() {
        TagToPost tagToPost = tagToPostService.saveTagToPost(1,1);
        Integer postId = 1;
        assertEquals(postId, tagToPost.getPostId());
    }

    @Test
    public void getTagToPostByPostId() {
        List<TagToPost> tagToPostList = tagToPostService.getTagToPostByPostId(4);
        assertEquals(2, tagToPostList.size());
    }

    @Test
    public void getTagToPostByTagId() {
        List<TagToPost> tagToPostList = tagToPostService.getTagToPostByTagId(3);
        assertEquals(3, tagToPostList.size());
    }

    @Test
    public void countPostsWithTag() {
        Integer countPostsWithTag = tagToPostService.countPostsWithTag(3);
        Integer count = 3;
        assertEquals(count, countPostsWithTag);
    }

    @Test
    public void isTagToPostPresent() {
        boolean isPresent = tagToPostService.isTagToPostPresent(1,1);
        assertTrue(isPresent);
    }
}