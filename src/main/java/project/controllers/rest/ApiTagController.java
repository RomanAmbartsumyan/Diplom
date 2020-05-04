package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.dto.TagDto;
import project.dto.TagsDto;
import project.models.Tag;
import project.models.enums.ModerationStatus;
import project.services.PostService;
import project.services.TagService;
import project.services.TagToPostService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * Контроллер тэгов
 */
@RestController
@RequestMapping("/api/tag")
@AllArgsConstructor
public class ApiTagController {
    private final TagService tagService;
    private final PostService postService;
    private final TagToPostService tagToPostService;
    /**
     * Выдает тэги
     */
    @GetMapping
    public ResponseEntity<TagsDto> getTagByName(@RequestParam(required = false) String query){
        List<TagDto> tagsDto = new ArrayList<>();
        Integer countPostsActiveAndModerationAccept =
                postService.countPostsByActiveAndModerationStatus((byte) 1, ModerationStatus.ACCEPTED);

        List<Integer> tagIds = tagToPostService.getTagIdsWithActivePosts();

        List<Tag> tags = tagIds.stream().map(tagService::getTagById).collect(toList());

        Map<String, Integer> tagsAngPosts = new HashMap<>();

        tags.forEach(tag -> {
            Integer countPosts = tagToPostService.countPostsWithTag(tag.getId());
            tagsAngPosts.put(tag.getName(), countPosts);
        });

        tagsAngPosts.forEach((k,v) -> {
            Float weight = (float) v / countPostsActiveAndModerationAccept;
            TagDto tagDto = new TagDto(k,weight);
            tagsDto.add(tagDto);
        });

        tagsDto.sort(comparing(TagDto::getWeight).reversed());

        return ResponseEntity.ok(new TagsDto(tagsDto));
    }
}
