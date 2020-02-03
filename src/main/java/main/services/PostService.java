package main.services;

import lombok.AllArgsConstructor;
import main.models.ModerationStatus;
import main.models.Post;
import main.models.repositories.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

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
//        postVotes.add(postVote);
//
//        post.setPostVotes(postVotes);
//
//        post.setActive((byte) 1);
//        post.setText("asdw");
//        post.setTitle("qwew4sdf");
//        post.setTime(LocalDateTime.now());
//        post.setModerationStatus(ModerationStatus.DECLINED);
//        postRepository.save(post);

//        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "postVotes"));
//        postRepository.findDistinctByActiveAndModerationStatus((byte)1,ModerationStatus.ACCEPTED,pageable);
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

        return postRepository.findDistinctByActiveAndModerationStatus((byte)1,ModerationStatus.ACCEPTED,pageable);
    }

    public List<Post> findBySearch(Integer offset, Integer limit, String query){
        Pageable pageable = PageRequest.of(offset, limit);
        List<Post> distinctByActiveAndModerationStatus = postRepository
                .findDistinctByActiveAndModerationStatus((byte) 1, ModerationStatus.ACCEPTED, pageable);

        return distinctByActiveAndModerationStatus.stream().filter(post -> {
            String[] stringsTitle = post.getTitle().split("\\s+");
            for (String s : stringsTitle) {
                if (s.equals(query)) {
                    return true;
                }
            }
            return false;
        }).collect(toList());
    }


}
