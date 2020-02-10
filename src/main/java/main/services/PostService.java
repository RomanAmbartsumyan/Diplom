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
//        LocalDateTime localDateTime = LocalDateTime.now();
//
//        post.setActive((byte) 1);
//        post.setText("541613216");
//        post.setTitle("poi kfgj");
//        post.setTime(localDateTime);
//        post.setModerationStatus(ModerationStatus.ACCEPTED);
//
//        postRepository.save(post);
//
//        Pageable pageable = PageRequest.of(0, 10);
//
//        String date = "2020-02-06%";
//        postRepository.findAllByTimeContaining(date, pageable);
//        System.out.println();
//    }

    /**
     * Возвращает коллекцию всех постов
     */
    public List<Post> findAll(Integer offset, Integer limit, String mode) {
        Sort sort;

        switch (mode) {
            case "best":
                sort = Sort.by(Sort.Direction.ASC, "postVotes");
                break;
            case "popular":
                sort = Sort.by(Sort.Direction.ASC, "postComment");
                break;
            case "early":
                sort = Sort.by(Sort.Direction.ASC, "time");
                break;
            default:
                sort = Sort.by(Sort.Direction.DESC, "time");
                break;
        }

        Pageable pageable = PageRequest.of(offset, limit, sort);

        return postRepository.findDistinctByActiveAndModerationStatus((byte) 1, ModerationStatus.ACCEPTED, pageable);
    }

    /**
     * Выдает коллекцию постов найденых по слову в заголовке
     */
    public List<Post> findBySearch(Integer offset, Integer limit, String query) {
        if (!query.isEmpty()) {
            Pageable pageable = PageRequest.of(offset, limit);
            return postRepository.findAllByActiveAndModerationStatusAndTitleContaining(
                    (byte) 1, ModerationStatus.ACCEPTED, query, pageable);
        }
        return findAll(offset, limit, "");
    }


    /**
     * Выдает посты найденые по id
     */
    public List<Post> getPostsById(List<Integer> id) {
        return postRepository.findAllByIdAndActiveAndModerationStatus(id, (byte) 1, ModerationStatus.ACCEPTED);
    }

    /**
     * Выдает конкретный пост по id
     */
    public Post getPostById(Integer id){
        Optional<Post> post = postRepository.findByIdAndActiveAndModerationStatus(id, (byte) 1, ModerationStatus.ACCEPTED);
        return post.orElse(null);
    }

    public List<Post> findPostsByDate(Integer offset, Integer limit, String date){
        Pageable pageable = PageRequest.of(offset, limit);
        return postRepository.findAllByTimeContaining(date + "%", pageable);
    }


}
