package main.services;

import lombok.AllArgsConstructor;
import main.models.ModerationStatus;
import main.models.Post;
import main.repositories.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с БД постов
 */
@Service
@AllArgsConstructor
public class PostService {
    /**
     * Репозиторий постов
     */
    private PostRepository postRepository;

//    @PostConstruct
//    public void init() {
//        Post post = new Post();
//
//        post.setActive((byte) 1);
//        post.setText("541613216");
//        post.setTitle("poi kfgj");
//        post.setTime(LocalDateTime.now());
//        post.setModerationStatus(ModerationStatus.ACCEPTED);
//
//        postRepository.save(post);

//        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "postVotes"));
//        postRepository.findDistinctByActiveAndModerationStatus((byte)1,ModerationStatus.ACCEPTED,pageable);
//        postRepository.findAllByActiveAndModerationStatusAndTitleContaining(
//                (byte)1,ModerationStatus.ACCEPTED, "asd", pageable);
//    }

    /**
     * Возвращает коллекцию всех постов
     */
    public List<Post> findAll(Integer offset, Integer limit, String mode) {
        Sort sort = Sort.by(Sort.Direction.DESC, "time");

        switch (mode) {
            case "recent":
                sort = Sort.by(Sort.Direction.DESC, "time");
                break;
            case "best":
                sort = Sort.by(Sort.Direction.ASC, "postVotes");
                break;
            case "popular":
                sort = Sort.by(Sort.Direction.ASC, "postComment");
                break;
            case "early":
                sort = Sort.by(Sort.Direction.ASC, "time");
                break;
        }

        Pageable pageable = PageRequest.of(offset, limit, sort);

        return postRepository.findDistinctByActiveAndModerationStatus((byte) 1, ModerationStatus.ACCEPTED, pageable);
    }

    public List<Post> findBySearch(Integer offset, Integer limit, String query) {
        if (!query.isEmpty()) {
            Pageable pageable = PageRequest.of(offset, limit);
            return postRepository.findAllByActiveAndModerationStatusAndTitleContaining(
                            (byte) 1, ModerationStatus.ACCEPTED, query, pageable);
        }
        return findAll(offset, limit, "");
    }


    public Post getPostFromRepositoryById(Integer id) {
        Optional<Post> optionalPost = postRepository
                .findByIdAndActiveAndModerationStatus(id, (byte) 1, ModerationStatus.ACCEPTED);
        return optionalPost.orElse(null);
    }

}
