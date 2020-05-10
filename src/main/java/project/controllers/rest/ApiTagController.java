package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.TagDto;
import project.dto.TagsDto;
import project.models.Tag;
import project.models.enums.ModerationStatus;
import project.services.PostService;
import project.services.TagService;
import project.services.TagToPostService;

import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

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
    public ResponseEntity<TagsDto> getTagByName() {
        Integer countPostsActiveAndModerationAccept =
                postService.countPostsByActiveAndModerationStatus((byte) 1, ModerationStatus.ACCEPTED);

        List<Integer> tagIds = tagToPostService.getTagIdsWithActivePosts();

        List<Tag> tags = tagIds.stream().map(tagService::getTagById).collect(toList());

        Map<String, Integer> tagsAngPosts = tags.stream()
                .collect(toMap(Tag::getName, tag -> tagToPostService.countPostsWithTag(tag.getId())));

        List<TagDto> tagsDto = tagsAngPosts.entrySet().stream().map(pair -> {
            Float weight = (float) pair.getValue() / countPostsActiveAndModerationAccept;
            return new TagDto(pair.getKey(), weight);
        }).sorted(comparing(TagDto::getWeight).reversed()).collect(toList());

        return ResponseEntity.ok(new TagsDto(tagsDto));
    }
}
