package com.project.travel.controller;

import com.project.travel.domain.Image;
import com.project.travel.domain.Post;
import com.project.travel.domain.User;
import com.project.travel.service.PostService;
import com.project.travel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostContoller {

    private final PostService postService;
    private final UserService userService;


    private Post post = new Post();
    private String find = new String();
    @PostMapping(value = "/posting")
    public String createPost(@Valid PostForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "/posting";
        }

        postService.savePost(form.getUserId(),
                form.getTitle(),
                form.getSTags(),
                form.getPlaceTypes(),
                form.getScores(),
                form.getContents(),
                form.getImages(),
                form.getDays(),
                form.getSPlaces());

        return "redirect:/";
    }

    @PostMapping(value = "/post/id")
    public void selectPost(Long postId){
        post = postService.viewPost(postId);
    }
    @GetMapping(value = "/post")
    public Post viewPost(Long postId){
        return post;
    }

    @GetMapping(value = "/post/user")
    public User getUserIdByPost(Long userId){
        return userService.findOne(userId);
    }

    @GetMapping(value = "/search/default")
    public List<Post> getDefaultPosts(){
        return postService.defaultPosts();
    }

    @PostMapping(value = "search")
    public void searchPost(String find){
        this.find = find;
    }

    @GetMapping(value = "search/{find}")
    public List<Post> searchPost(){
        return postService.searchPosts(find);
    }

    @PostMapping(value = "search/like/{postid}")
    public void likePost(Long userId, Long postId){
        postService.likePost(userId, postId);
    }

}
