package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.dto.PostsByYearDto;
import project.models.Post;
import project.services.PostService;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.reverseOrder;

/**
 * Контроллер тэгов
 */
@RestController
@RequestMapping("/api/calendar")
@AllArgsConstructor
public class ApiCalendarController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<PostsByYearDto> getPostsByDate(@RequestParam String year){
        List<String> years = postService.getYears();
        List<Post> posts = postService.findPostsByYear(year);
        Map<String, Integer> postsAndCount = new HashMap<>();
        posts.forEach(post ->{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String postDate = post.getTime().format(formatter);
            Integer countPostsByDate = postService.countPostsByDate(postDate);
            postsAndCount.put(postDate, countPostsByDate);
        });
        Map<String, Integer> sortedPostsAndCountByValue = postsAndCount
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (k, v) -> k, LinkedHashMap::new
                ));
        return ResponseEntity.ok(new PostsByYearDto(years, sortedPostsAndCountByValue));
    }
}
