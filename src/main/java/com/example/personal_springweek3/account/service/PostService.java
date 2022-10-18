package com.example.personal_springweek3.account.service;

import com.example.personal_springweek3.account.dto.request.PostRequestDto;
import com.example.personal_springweek3.account.dto.response.PostResponseDto;
import com.example.personal_springweek3.account.entity.Post;
import com.example.personal_springweek3.account.repository.PostRepository;
import com.example.personal_springweek3.global.dto.GlobalResponseDto;
import com.example.personal_springweek3.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public GlobalResponseDto createPost(PostRequestDto postRequestDto, @RequestHeader("ACCESS_TOKEN") String token) {

        String email = jwtUtil.getEmailFromToken(token);

        postRepository.save(new Post(postRequestDto, email));

        return new GlobalResponseDto("Success Save Post", HttpStatus.OK.value());
    }

    @Transactional
    public PostResponseDto findDetail(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        return new PostResponseDto(post);
    }

    @Transactional
    public GlobalResponseDto updatePost(Long postId, PostRequestDto postRequestDto, @RequestHeader("ACCESS_TOKEN") String token) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        String email = jwtUtil.getEmailFromToken(token);

        if(!email.equals(post.getUserEmail())){
            throw new RuntimeException("Account Check");
        }

        post.update(postRequestDto);

        return new GlobalResponseDto("Success Update Post", HttpStatus.OK.value());
    }

    @Transactional
    public GlobalResponseDto deletePost(Long postId, @RequestHeader("ACCESS_TOKEN") String token) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        String email = jwtUtil.getEmailFromToken(token);

        if(!email.equals(post.getUserEmail())){
            throw new RuntimeException("Account Check");
        }

        postRepository.deleteById(postId);

        return new GlobalResponseDto("Success Delete Post", HttpStatus.OK.value());
    }
}
