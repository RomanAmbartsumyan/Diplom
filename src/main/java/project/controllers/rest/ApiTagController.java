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
import project.services.PostService;
import project.services.TagService;
import project.services.TagToPostService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Контроллер тэгов
 */
@RestController
@RequestMapping("/api/tag")
@AllArgsConstructor
public class ApiTagController {
    private TagService tagService;
    private PostService postService;
    private TagToPostService tagToPost;
    /**
     * Выдает посты по поиску или все если поиск пустой
     */
    @GetMapping
    public ResponseEntity<TagsDto> getTagByName(@RequestParam String query){
        List<TagDto> tagsDto = new ArrayList<>();
        List<Tag> tags = tagService.getAllTagsOrFindByName(query);
        Integer countPostsActiveAndModerationAccept = postService.countPostsActiveAndAccessModerator();
        Map<String, Integer> tagsAngPosts = new HashMap<>();
        tags.forEach(tag -> {
            Integer countPosts = tagToPost.countPostsWithTag(tag.getId());
            tagsAngPosts.put(tag.getName(), countPosts);
        });

        tagsAngPosts.forEach((k,v) -> {
            Short weight = (short) (v / countPostsActiveAndModerationAccept);
            TagDto tagDto = new TagDto(k,weight);
            tagsDto.add(tagDto);
        });

        return ResponseEntity.ok(new TagsDto(tagsDto));
    }
}
