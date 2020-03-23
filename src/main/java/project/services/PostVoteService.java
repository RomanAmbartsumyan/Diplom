package project.services;

import org.springframework.stereotype.Service;
import project.models.Post;
import project.models.PostVote;
import project.repositories.PostVoteRepository;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void init(){
        for (int i = 0; i < 5; i++) {
            PostVote vote = new PostVote();
            vote.setValue((byte) 1);
            postVoteRepository.save(vote);
            Post post = new Post();
            vote.setPostId(post);
        }
        System.out.println();
    }


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

    public Integer countLikes(){
        return postVoteRepository.countAllLikes();
    }

    public Integer countDislikes(){
        return postVoteRepository.countAllDislikes();
    }
}
