package project.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import project.models.Post;
import project.models.PostVote;

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
public class PostVoteServiceTest {
    @Autowired
    private PostVoteService postVoteService;

    @Autowired
    private PostService postService;

    @Test
    public void getAllPostVotesByPostId() {
        Post post = postService.getPostById(1);
        List<PostVote> postVotes = postVoteService.getAllPostVotesByPostId(post);
        assertEquals(2, postVotes.size());
    }

    @Test
    public void countLikes() {
        Integer countLikes = postVoteService.countLikes();
        Integer count = 2;
        assertEquals(count,countLikes);
    }

    @Test
    public void countDislikes() {
        Integer countLikes = postVoteService.countDislikes();
        Integer count = 1;
        assertEquals(count,countLikes);
    }

    @Test
    public void addLike() {
        Post post = postService.getPostById(5);
        boolean isAddLike = postVoteService.addLike(post, 1);
        assertTrue(isAddLike);
    }

    @Test
    public void addDislike() {
        Post post = postService.getPostById(5);
        boolean isAddDislike = postVoteService.addDislike(post, 1);
        assertTrue(isAddDislike);
    }

    @Test
    public void getCountMyLikes() {
        Integer countMyLikes = postVoteService.getCountMyLikes(1);
        Integer count = 0;
        assertEquals(count, countMyLikes);
    }

    @Test
    public void getCountMyDislikes() {
        Integer countMyDislikes = postVoteService.getCountMyDislikes(1);
        Integer count = 1;
        assertEquals(count, countMyDislikes);
    }
}