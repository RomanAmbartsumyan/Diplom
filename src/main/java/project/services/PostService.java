package project.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.dto.AddPostDto;
import project.exceptions.BadRequestException;
import project.exceptions.NotFountException;
import project.models.Post;
import project.models.User;
import project.models.enums.ModerationStatus;
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
        addOrEditPost(addPost, post, userId);
        return post;
    }

    public Post editingPost(Integer postId, User user, AddPostDto addPost) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            throw new NotFountException();
        }

        if (user.getModerator() == 0) {
            post.setModerationStatus(ModerationStatus.NEW);
        }

        addOrEditPost(addPost, post, user.getId());

        return post;
    }

    private void addOrEditPost(AddPostDto addPost, Post post, Integer userId) {
        String strTime = addPost.getTime();
        if (!strTime.isEmpty() && !strTime.equals("NaN-NaN-NaN NaN:NaN")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(strTime, formatter);
            if (dateTime.isBefore(LocalDateTime.now())) {
                post.setTime(LocalDateTime.now());
            }
        } else {
            post.setTime(LocalDateTime.now());
        }

        post.setModerationStatus(ModerationStatus.NEW);
        post.setActive(addPost.getActive());
        post.setTitle(addPost.getTitle());
        post.setText(addPost.getText());
        post.setUserId(userId);
        post.setViewCount(0);
        postRepository.save(post);
    }

    /**
     * Возвращает отсортированую коллекцию всех постов
     */
    public List<Post> findAllAndSort(Integer offset, Integer limit, String mode) {
        Pageable pageable = PageRequest.of(offset/limit, limit);
        switch (mode) {
            case "best":
                return postRepository.bestPosts(pageable);
            case "popular":
                return postRepository.mostPopularPosts(pageable);
            case "early":
                return postRepository.findAllByModerationStatusAndActiveOrderByTimeAsc(ModerationStatus.ACCEPTED,
                        (byte) 1, pageable);
            case "recent":
                return postRepository.findAllByModerationStatusAndActiveOrderByTimeDesc(ModerationStatus.ACCEPTED,
                        (byte) 1,pageable);
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
        Pageable pageable = PageRequest.of(offset/limit, limit);
        return postRepository.findAllByTimeContaining(date + "%", pageable);
    }

    /**
     * Выдает количество активных постов и принятых модератови
     */
    public Integer countPostsByActiveAndModerationStatus(Byte active, ModerationStatus moderationStatus) {
        return postRepository.countAllByActiveAndModerationStatus(active, moderationStatus);
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
        return postRepository.countAllByModeratorIdAndActiveAndModerationStatus(null, (byte) 1, ModerationStatus.NEW);
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

    public List<Post> activePostsOnModeration(Integer offset, Integer limit, String status, Integer moderatorId) {
        Pageable pageable = PageRequest.of(offset/limit, limit);
        switch (status) {
            case "declined":
                return postRepository.findAllByModeratorIdAndActiveAndModerationStatus(moderatorId, (byte) 1, ModerationStatus.DECLINED, pageable);
            case "accepted":
                return postRepository.findAllByModeratorIdAndActiveAndModerationStatus(moderatorId, (byte) 1, ModerationStatus.ACCEPTED, pageable);
            case "new":
                return postRepository.findAllByModeratorIdAndActiveAndModerationStatus(null, (byte) 1, ModerationStatus.NEW, pageable);
        }
        throw new BadRequestException();
    }

    public List<Post> getMyPosts(Integer userId, Integer offset, Integer limit, String status) {
        Pageable pageable = PageRequest.of(offset/limit, limit);
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

    public void setModeration(Integer postId, String decision){
        Post post = getPostById(postId);
        switch (decision){
            case "decline":
                post.setModerationStatus(ModerationStatus.DECLINED);
            case "accept":
                post.setModerationStatus(ModerationStatus.ACCEPTED);
        }
        postRepository.save(post);
    }
}
