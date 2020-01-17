package main.services;

import main.models.TagToPost;
import main.models.repositories.TagToPostRepository;
import org.springframework.stereotype.Service;

@Service
public class TagToPostService {
    TagToPostRepository tagToPostRepository;

    public TagToPostService(TagToPostRepository tagToPostRepository) {
        this.tagToPostRepository = tagToPostRepository;
    }
}
