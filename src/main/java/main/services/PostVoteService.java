package main.services;

import main.models.PostVote;
import main.repositories.PostVoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * Выдает информацию о лайков и дизлайков к данному посту найденому по id
     */
    public List<PostVote> getAllPostVotesByPostId(Integer id){
        return postVoteRepository.findAllByPostId(id);
    }
}
