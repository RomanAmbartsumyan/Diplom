package main.controllers.rest;

import lombok.AllArgsConstructor;
import main.dto.responce.BlogGeneralInfiDto;
import main.dto.responce.TagDto;
import main.dto.responce.TagsDto;
import main.models.Tag;
import main.services.PostService;
import main.services.TagService;
import main.services.TagToPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Контроллер прочих запросов
 */
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class ApiGeneralController {

    private TagService tagService;
    private PostService postService;
    private TagToPostService tagToPost;
    /**
     * Запись в футер сайта
     */
    @GetMapping("api/init")
    public ResponseEntity<BlogGeneralInfiDto> mainPage() {
        return ResponseEntity.ok(new BlogGeneralInfiDto("DevPub", "Рассказы разботчиков"
                , "+7 903 666-44-55", "mail@mail.ru", "Дмитрий Сергеев", "2005"));
    }

    /**
     * Выдает посты по поиску или все если поиск пустой
     */
    @GetMapping("api/tag")
    public ResponseEntity<TagsDto> getTags(@RequestParam String query){
        List<TagDto> tagDtos = new ArrayList<>();
        List<Tag> tags = tagService.getAllTagsOrFindByName(query);
        Integer countPostsActiveAndModerationAccept = postService.findAll().size();
        Map<String, Integer> tagsAngPosts = new HashMap<>();
        tags.forEach(tag -> {
            Integer countPostWithTag = tagToPost.getTagtoPostByTagId(tag.getId()).size();
            Tag optionalTag = tagService.getTagById(tag.getId());
            tagsAngPosts.put(optionalTag.getName(), countPostWithTag);
        });

        tagsAngPosts.forEach((k,v) -> {
            Short weight = (short) (v / countPostsActiveAndModerationAccept);
            TagDto tagDto = new TagDto(k,weight);
            tagDtos.add(tagDto);
        });

        return ResponseEntity.ok(new TagsDto(tagDtos));
    }
}
