package project.services;

import org.springframework.stereotype.Service;
import project.models.Post;
import project.models.PostVote;
import project.repositories.PostVoteRepository;

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
    public List<PostVote> getAllPostVotesByPostId(Post post){
        return postVoteRepository.findAllByPostId(post);
    }

    public Integer countLikes(){
        return postVoteRepository.countAllLikes();
    }

    public Integer countDislikes(){
        return postVoteRepository.countAllDislikes();
    }
}
