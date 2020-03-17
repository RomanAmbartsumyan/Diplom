package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.responce.AllStatisticsDto;
import project.services.PostService;
import project.services.PostVoteService;

@RestController
@RequestMapping("/api/statistics/")
@AllArgsConstructor
public class ApiStatisticsController {
    private PostService postService;
    private PostVoteService postVoteService;

    @GetMapping("all")
    public ResponseEntity<AllStatisticsDto> getAllStatistics (){
        Integer countPosts = postService.getCountAllPosts();
        Integer likesCount = postVoteService.countLikes();
        Integer dislikesCount = postVoteService.countDislikes();
        Integer countViews = postService.getCountViews();
        String firstPublication = postService.dateOfFirstPublication();
        AllStatisticsDto statisticsDto = new AllStatisticsDto(countPosts, likesCount,
                dislikesCount, countViews, firstPublication);
        return ResponseEntity.ok(statisticsDto);
    }
}
