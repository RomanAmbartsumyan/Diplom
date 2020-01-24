package main.services;

import main.models.repositories.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
}