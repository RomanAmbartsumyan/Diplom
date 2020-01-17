package main.services;

import main.models.repositories.PostVoteRepository;
import org.springframework.stereotype.Service;

@Service
public class PostVoteService {
    PostVoteRepository postVoteRepository;

    public PostVoteService(PostVoteRepository postVoteRepository) {
        this.postVoteRepository = postVoteRepository;
    }
}
