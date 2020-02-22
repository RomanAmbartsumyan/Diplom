package project.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.models.ModerationStatus;
import project.models.Post;
import project.repositories.PostRepository;

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
     * Возвращает отсортированую коллекцию всех постов
     */
    public List<Post> findAllAndSort(Integer offset, Integer limit, String mode) {
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
        return findAllAndSort(offset, limit, "");
    }


    /**
     * Выдает посты найденые по id
     */
    public List<Post> getAllPostsById(List<Integer> id) {
        return postRepository.findByIdIn(id);
    }

    /**
     * Выдает конкретный пост по id
     */
    public Post getPostById(Integer id){
        Optional<Post> post = postRepository.findByIdAndActiveAndModerationStatus(id, (byte) 1, ModerationStatus.ACCEPTED);
        return post.orElse(null);
    }

    /**
     * Выдает посты за конкретную дату
     */
    public List<Post> findPostsByDate(Integer offset, Integer limit, String date){
        Pageable pageable = PageRequest.of(offset, limit);
        return postRepository.findAllByTimeContaining(date + "%", pageable);
    }

    /**
     * Выдает количество активных постов  и принятых модератови
     */
    public Integer countPosts(){
        return postRepository.countByActiveAndModerationStatus();
    }
}
