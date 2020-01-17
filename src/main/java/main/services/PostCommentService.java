package main.services;

import main.models.repositories.PostCommentRepository;
import org.springframework.stereotype.Service;

@Service
public class PostCommentService {
    PostCommentRepository postCommentRepository;

    public PostCommentService(PostCommentRepository postCommentRepository) {
        this.postCommentRepository = postCommentRepository;
    }
}
