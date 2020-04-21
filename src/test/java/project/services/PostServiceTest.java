package project.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import project.dto.AddPostDto;
import project.models.Post;
import project.models.User;
import project.repositories.PostRepository;
import project.repositories.UserRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
public class PostServiceTest {

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserRepository userRepository;

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

        User user = new User();
        Post post = postService.createPost(user, dto);

        verify(postRepository, times(1)).save(post);
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

        Post postCreate = postService.createPost(user, dto);

        Post post = postService.editingPost(postCreate.getId(), user, dto);

        verify(postRepository, times(1)).save(postCreate);
    }

    @Test
    public void findAllAndSort() {
    }

    @Test
    public void findBySearch() {
    }

    @Test
    public void getPostById() {
    }

    @Test
    public void findPostsByDate() {
    }

    @Test
    public void countPostsByActiveAndModerationStatus() {
    }

    @Test
    public void testFindPostsByDate() {
    }

    @Test
    public void countPostsByDate() {
    }

    @Test
    public void getYears() {
    }

    @Test
    public void getCountOfNewPosts() {
    }

    @Test
    public void getCountAllPosts() {
    }

    @Test
    public void getCountViews() {
    }

    @Test
    public void dateOfFirstPublication() {
    }

    @Test
    public void activePostsOnModeration() {
    }

    @Test
    public void getMyPosts() {
    }
}