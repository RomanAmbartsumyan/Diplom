package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.TagDto;
import project.dto.TagsDto;
import project.services.TagService;
import project.services.TagToPostService;

import java.util.List;

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
    private final TagToPostService tagToPostService;

    /**
     * Выдает тэги
     */
    @GetMapping
    public ResponseEntity<TagsDto> getTagsByName() {
        Integer countPostsActiveAndModerationAccept = tagToPostService.countPostsWithTags();

        List<TagDto> tagsDto = tagService.tagsOnActivePosts().stream().map(tag -> {
            Integer count = tagToPostService.countPostsWithTagByTagId(tag.getId());
            Float weight = (float) count / countPostsActiveAndModerationAccept;
            return new TagDto(tag.getName(), weight);
        }).sorted(comparing(TagDto::getWeight).reversed()).collect(toList());

        return ResponseEntity.ok(new TagsDto(tagsDto));
    }
}
