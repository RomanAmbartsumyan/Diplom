package main.services;

import main.models.ModerationStatus;
import main.models.Post;
import main.models.repositories.PostCommentRepository;
import main.models.repositories.PostRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;

@Service
public class PostService {
    private PostCommentRepository postCommentRepository;
    private PostRepository postRepository;

    @PostConstruct
    public void init(){
        Post post = new Post();
        post.setText("HiThere!");
        post.setModerationStatus(ModerationStatus.ACCEPTED);
        postRepository.save(post);
    }

    public PostService(PostCommentRepository postCommentRepository,
                       PostRepository postRepository) {
        this.postCommentRepository = postCommentRepository;
        this.postRepository = postRepository;
    }
}
