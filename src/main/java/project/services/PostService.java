package project.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.dto.AddPostDto;
import project.exceptions.BadRequestException;
import project.exceptions.NotFountException;
import project.models.ModerationStatus;
import project.models.Post;
import project.repositories.PostRepository;

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

    public Post createPost(Integer userId, AddPostDto addPost) {
        Post post = new Post();
        String strTime = addPost.getTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(strTime, formatter);
        post.setModerationStatus(ModerationStatus.NEW);
        if (dateTime.isBefore(LocalDateTime.now())) {
            dateTime = LocalDateTime.now();
        }
        post.setTime(dateTime);
        post.setActive(addPost.getActive());
        post.setTitle(addPost.getTitle());
        post.setText(addPost.getText());
        post.setUserId(userId);
        post.setViewCount(0);
        postRepository.save(post);
        return post;
    }

    /**
     * Возвращает отсортированую коллекцию всех постов
     */
    public List<Post> findAllAndSort(Integer offset, Integer limit, String mode) {
        Pageable pageableWhitOutSort = PageRequest.of(offset, limit);
        switch (mode) {
            case "best":
                return postRepository.bestPosts(pageableWhitOutSort);
            case "popular":
                return postRepository.mostPopularPosts(pageableWhitOutSort);
            case "early":
                return postRepository.findAllByOrderByTimeAsc();
            case "recent":
                return postRepository.findAllByOrderByTimeDesc();
        }
        throw new BadRequestException();
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
     * Выдает конкретный пост по id
     */
    public Post getPostById(Integer id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            post.get().setViewCount(post.get().getViewCount() + 1);
            postRepository.save(post.get());
            return post.get();
        }
        throw new NotFountException();
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
        switch (status) {
            case "declined":
                return postRepository.findDistinctByActiveAndModerationStatus((byte) 1, ModerationStatus.DECLINED, pageable);
            case "accepted":
                return postRepository.findDistinctByActiveAndModerationStatus((byte) 1, ModerationStatus.ACCEPTED, pageable);
            case "new":
                return postRepository.findDistinctByActiveAndModerationStatus((byte) 1, ModerationStatus.NEW, pageable);
        }
        throw new BadRequestException();
    }

    public List<Post> getMyPosts(Integer userId, Integer offset, Integer limit, String status) {
        Pageable pageable = PageRequest.of(offset, limit);
        switch (status) {
            case "inactive":
                return postRepository.findAllByUserIdAndActive(userId, (byte) 0, pageable);
            case "pending":
                return postRepository.findAllByUserIdAndActiveAndModerationStatus(userId, (byte) 1,
                        ModerationStatus.NEW, pageable);
            case "declined":
                return postRepository.findAllByUserIdAndActiveAndModerationStatus(userId, (byte) 1,
                        ModerationStatus.DECLINED, pageable);
            case "published ":
                return postRepository.findAllByUserIdAndActiveAndModerationStatus(userId, (byte) 1,
                        ModerationStatus.ACCEPTED, pageable);
        }
        throw new BadRequestException();
    }
}
