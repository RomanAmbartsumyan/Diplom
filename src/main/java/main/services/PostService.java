package main.services;

import lombok.AllArgsConstructor;
import main.models.Post;
import main.models.repositories.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
//        PostVote postVote = new PostVote();
//        postVote.setPostId(post);
//        postVote.setValue((byte) 1);
//        postVote.setTime(LocalDateTime.now());
//
//        List<PostVote> postVotes = new ArrayList<>();
//
//
//        post.setPostVotes(postVotes);
//
//        post.setActive((byte) 1);
//        post.setText("asdw");
//        post.setTitle("qwew4sdf");
//        post.setTime(LocalDateTime.now());
//        post.setModerationStatus(ModerationStatus.ACCEPTED);
//        postRepository.save(post);
//
//        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "time"));
//        postRepository.findAllBy(pageable);
//        System.out.println();
//
//    }

    /**
     * Возвращает коллекцию всех постов
     */
    public Set<Post> findAll(Integer offset, Integer limit, String mode) {
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

        return postRepository.findAllBy(pageable);
    }
}
