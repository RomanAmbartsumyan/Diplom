package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.models.Post;
import project.models.PostVote;
import project.repositories.PostVoteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с БД лайков и дизлайков
 */
@Service
@AllArgsConstructor
public class PostVoteService {
    /**
     * Репозиторий лайков и дизлайков
     */
    private final PostVoteRepository postVoteRepository;

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

    public boolean addLike(Post postId, Integer userId){
        Optional<PostVote> like = postVoteRepository.findByPostIdAndUserIdAndValue(postId, userId, (byte) 1);
        Optional<PostVote> dislike = postVoteRepository.findByPostIdAndUserIdAndValue(postId, userId, (byte) -1);
        if(like.isPresent()){
            return false;
        }

        if(dislike.isPresent()){
            dislike.get().setValue((byte) 1);
            postVoteRepository.save(dislike.get());
            return true;
        }

        savePostVote(postId, userId, (byte) 1);
        return true;
    }

    public boolean addDislike(Post postId, Integer userId){
        Optional<PostVote> like = postVoteRepository.findByPostIdAndUserIdAndValue(postId, userId, (byte) 1);
        Optional<PostVote> dislike = postVoteRepository.findByPostIdAndUserIdAndValue(postId, userId, (byte) -1);
        if(dislike.isPresent()){
            return false;
        }

        if(like.isPresent()){
            like.get().setValue((byte) -1);
            postVoteRepository.save(like.get());
            return true;
        }

        savePostVote(postId, userId, (byte) -1);
        return true;
    }

    public void savePostVote(Post postId, Integer userId, byte i) {
        PostVote postVote = new PostVote();
        postVote.setPostId(postId);
        postVote.setUserId(userId);
        postVote.setTime(LocalDateTime.now().plusHours(3));
        postVote.setValue(i);
        postVoteRepository.save(postVote);
    }

    public Integer getCountMyLikes(Integer userId){
        return postVoteRepository.countAllByUserIdAndValue(userId, 1);
    }

    public Integer getCountMyDislikes(Integer userId){
        return postVoteRepository.countAllByUserIdAndValue(userId, -1);
    }
}
