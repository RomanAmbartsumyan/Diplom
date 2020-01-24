package main.services;

import main.models.PostComment;
import main.models.PostVote;
import main.models.User;
import main.models.repositories.PostVoteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с БД лайков и дизлайков
 */
@Service
public class PostVoteService {
    /**
     * Репозиторий лайков и дизлайков
     */
    PostVoteRepository postVoteRepository;

    /**
     * Конструктор лайков и дизлайков
     */
    public PostVoteService(PostVoteRepository postVoteRepository) {
        this.postVoteRepository = postVoteRepository;
    }

    /**
     * Выдает информацию о лайков и дизлайков к данному посту наденому по id
     */
    public PostVote getPostVoteById(Integer id){
        Optional<PostVote> postById = postVoteRepository.findById(id);
        return postById.orElse(null);
    }
}
