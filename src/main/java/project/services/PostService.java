package project.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.models.ModerationStatus;
import project.models.Post;
import project.repositories.PostRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @PostConstruct
    public void init() {
        for (int i = 0; i < 3; i++) {
            Post post = new Post();
            LocalDateTime time = LocalDateTime.now();
            post.setTime(time);
            postRepository.save(post);
        }
        for (int i = 0; i < 2; i++) {
            Post post = new Post();
            LocalDateTime time = LocalDateTime.now().minusHours(1);
            post.setTime(time);
            postRepository.save(post);
        }
        postRepository.firstPublication();
        System.out.println();
    }

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
    public Post getPostById(Integer id) {
        Optional<Post> post = postRepository.findByIdAndActiveAndModerationStatus(id, (byte) 1,
                ModerationStatus.ACCEPTED);
        return post.orElse(null);
    }

    /**
     * Выдает посты за конкретную дату
     */
    public List<Post> findPostsByDate(Integer offset, Integer limit, String date) {
        Pageable pageable = PageRequest.of(offset, limit);
        return postRepository.findAllByTimeContaining(date + "%", pageable);
    }

    /**
     * Выдает количество активных постов и принятых модератови
     */
    public Integer countPostsActiveAndAccessModerator() {
        return postRepository.countByActiveAndModerationStatus();
    }

    /**
     * Выдает посты за конкретный год
     */
    public List<Post> findPostsByDate(String year) {
        List<Post> posts = postRepository.findAllByTimeContaining(year + "%");
        if (posts.isEmpty()) {
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
            return postRepository.findAllByTimeContaining(localDate.format(formatter) + "%");
        }
        return posts;
    }

    /**
     * Количество постов за конкретную дату
     */
    public Integer countPostsByDate(String date) {
        return postRepository.countAllByTimeContaining(date + "%");
    }

    /**
     * Выдает список готов в которых были посты
     */
    public List<String> getYears() {
        return postRepository.findAllYear();
    }

    /**
     * Выдает кол-во новых постов
     */
    public Integer getCountOfNewPosts() {
        return postRepository.countALLByModerationStatusIsNew();
    }

    /**
     * Выдает кол-во всех постов
     */
    public Integer getCountAllPosts() {
        return postRepository.countAll();
    }

    /**
     * Выдает кол-во просмотров постов
     */
    public Integer getCountViews() {
        return postRepository.countViews();
    }

    public String dateOfFirstPublication() {
        Optional<Post> post = postRepository.firstPublication();
        if (post.isPresent()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return post.get().getTime().format(formatter);
        }
        return null;
    }

    public Integer countActivePosts() {
        return postRepository.countAllByActive((byte) 1);
    }

    public List<Post> activePostsOnModeration(Integer offset, Integer limit, String status) {
        Pageable pageable = PageRequest.of(offset, limit);
        ModerationStatus moderationStatus;
        switch (status) {
            case "declined":
                moderationStatus = ModerationStatus.DECLINED;
                break;
            case "accepted":
                moderationStatus = ModerationStatus.ACCEPTED;
                break;
            default:
                moderationStatus = ModerationStatus.NEW;
        }
        return postRepository.findDistinctByActiveAndModerationStatus((byte) 1, moderationStatus, pageable);
    }
}
