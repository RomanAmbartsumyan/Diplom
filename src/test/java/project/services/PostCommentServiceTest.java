package project.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import project.models.Post;
import project.repositories.PostCommentRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
public class PostCommentServiceTest {

    @MockBean
    private PostCommentRepository repository;

    @Autowired
    private PostCommentService service;

    @Test
    public void allPostComments() {
        Post post = new Post();
        service.allPostComments(post);

        verify(repository, times(1)).findAllByPostId(post);
    }
}