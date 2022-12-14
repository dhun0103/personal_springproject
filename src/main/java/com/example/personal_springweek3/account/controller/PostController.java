package com.example.personal_springweek3.account.controller;

import com.example.personal_springweek3.account.dto.request.PostRequestDto;
import com.example.personal_springweek3.account.dto.response.PostResponseDto;
import com.example.personal_springweek3.account.entity.Account;
import com.example.personal_springweek3.account.entity.Post;
import com.example.personal_springweek3.account.repository.PostRepository;
import com.example.personal_springweek3.account.service.PostService;
import com.example.personal_springweek3.global.dto.GlobalResponseDto;
import com.example.personal_springweek3.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //글 작성하기
    @PostMapping("/create/posts")
    public GlobalResponseDto createPost(@RequestBody PostRequestDto postRequestDto, @RequestHeader("ACCESS_TOKEN") String token){

        return postService.createPost(postRequestDto, token);
    }

    //글 전체 조회하기
    @GetMapping("/read/posts")
    public List<PostResponseDto> readAllPost(){

        return postService.findAllPost();
    }
    //글 각각 조회하기
    @GetMapping("/read/posts/{postId}")
    public PostResponseDto readOnePost(@PathVariable Long postId) {

        return postService.findOnePost(postId);
    }

    //글 수정하기
    @PutMapping("/update/posts/{postId}")
    public GlobalResponseDto updatePost(
            @PathVariable Long postId, @RequestBody PostRequestDto postRequestDto,  @RequestHeader("ACCESS_TOKEN") String token) {

        return postService.updatePost(postId, postRequestDto, token);
    }
    //글 삭제하기
    @DeleteMapping("/delete/posts/{postId}")
    public GlobalResponseDto deletePost(@PathVariable Long postId,  @RequestHeader("ACCESS_TOKEN") String token) {

        return postService.deletePost(postId, token);
    }


    //내가 작성한 게시글 조회하기
    @GetMapping("/read/posts/myPost")
    public List<PostResponseDto> readMyCreatePost( @RequestHeader("ACCESS_TOKEN") String token ) {

        return postService.findMyCreatePost(token);
    }
    //내가 좋아요한 게시글 조회하기
    @GetMapping("/read/posts/myLikePost")
    public List<PostResponseDto> readMyLikePost( @AuthenticationPrincipal UserDetailsImpl userDetails ) {

        Account account = userDetails.getAccount();
        return postService.findMyLikePost(account);
    }
}
