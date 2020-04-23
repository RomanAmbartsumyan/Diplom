package project.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import project.dto.AddPostDto;
import project.models.Post;
import project.models.User;
import project.models.enums.ModerationStatus;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/data_test.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/clean.sql")
})
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Test
    public void createPost() {
        String[] tags = new String[]{"Hello", "world!"};
        AddPostDto dto = new AddPostDto();
        dto.setTime("1990-06-11 10:34");
        dto.setTitle("Hello Hello Hello Hello");
        dto.setActive((byte) 1);
        dto.setText("world world world world world world");
        dto.setTags(tags);

        String email = "asd@mail.ru";
        String password = "asdqwe";
        String name = "World";
        User user = userService.createUser(email, password, name);

        Post post = postService.createPost(user, dto);

        assertEquals("world world world world world world", post.getText());
    }

    @Test
    public void editingPost() {
        String[] tags = new String[]{"Hello", "world!"};
        AddPostDto dto = new AddPostDto();
        dto.setTime("1990-06-11 10:34");
        dto.setTitle("Hello Hello Hello Hello");
        dto.setActive((byte) 1);
        dto.setText("world world world world world world");
        dto.setTags(tags);

        String email = "asd@mail.ru";
        String password = "asdqwe";
        String name = "World";
        User user = userService.createUser(email, password, name);

        Post editingPost = postService.editingPost(1, user, dto);

        assertEquals("Hello Hello Hello Hello", editingPost.getTitle());
    }

    @Test
    public void findAllAndSort() {
        List<Post> post = postService.findAllAndSort(0, 10, "popular");
        assertEquals(5, post.size());
    }

    @Test
    public void findBySearch() {
        List<Post> post = postService.findBySearch(0, 10, "nulla");
        assertEquals(1, post.size());
    }

    @Test
    public void getPostById() {
        Post post = postService.getPostById(1);
        assertEquals("nulla ut erat id mauris", post.getTitle());
    }

    @Test
    public void findPostsByDate() {
        List<Post> posts = postService.findPostsByDate(0, 10, "2020-03-18");
        assertEquals(1, posts.size());
    }

    @Test
    public void countPostsByActiveAndModerationStatus() {
        Integer countPosts = postService.countPostsByActiveAndModerationStatus((byte) 1, ModerationStatus.NEW);
        Integer count = 1;
        assertEquals(count, countPosts);
    }

    @Test
    public void testFindPostsByYear() {
        List<Post> posts = postService.findPostsByDate(0, 10, "2020");
        assertEquals(1, posts.size());
    }

    @Test
    public void countPostsByDate() {
        Integer countPosts = postService.countPostsByDate("2020");
        Integer count = 2;
        assertEquals(count, countPosts);
    }

    @Test
    public void getYears() {
        List<String> years = postService.getYears();
        assertEquals(2, years.size());
    }

    @Test
    public void getCountOfNewPosts() {
        Integer countPosts = postService.getCountOfNewPosts();
        Integer count = 1;
        assertEquals(count, countPosts);
    }

    @Test
    public void getCountAllPosts() {
        Integer countPosts = postService.getCountAllPosts();
        Integer count = 5;
        assertEquals(count, countPosts);
    }

    @Test
    public void getCountViews() {
        Integer countViews = postService.getCountViews();
        Integer count = 230;
        assertEquals(count, countViews);
    }

    @Test
    public void dateOfFirstPublication() {
        String date = postService.dateOfFirstPublication();
        assertEquals("2019-06-14 03:44", date);
    }

    @Test
    public void activePostsOnModeration() {
        List<Post> posts = postService.activePostsOnModeration(0, 10, "new", null);
        assertEquals(1, posts.size());
    }

    @Test
    public void getMyPosts() {
        List<Post> posts = postService.getMyPosts(1, 0, 10, "published");
        assertEquals(1, posts.size());
    }

    @Test
    public void setModeration() {
        Post post = postService.setModeration(1, "accept", 1);
        assertEquals(ModerationStatus.ACCEPTED, post.getModerationStatus());
    }

    @Test
    public void getCountMyViews() {
        Integer countViews = postService.getCountMyViews(1);
        Integer views = 90;
        assertEquals(views, countViews);
    }

    @Test
    public void getDateMyFirstPublication() {
        String date = postService.getDateMyFirstPublication(1);
        assertEquals("2020-02-27 15:30", date);
    }
}