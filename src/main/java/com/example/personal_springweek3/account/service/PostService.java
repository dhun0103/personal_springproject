package com.example.personal_springweek3.account.service;

import com.example.personal_springweek3.account.dto.request.PostRequestDto;
import com.example.personal_springweek3.account.dto.response.PostResponseDto;
import com.example.personal_springweek3.account.entity.Account;
import com.example.personal_springweek3.account.entity.Comment;
import com.example.personal_springweek3.account.entity.Like;
import com.example.personal_springweek3.account.entity.Post;
import com.example.personal_springweek3.account.repository.CommentRepository;
import com.example.personal_springweek3.account.repository.LikeRepository;
import com.example.personal_springweek3.account.repository.PostRepository;
import com.example.personal_springweek3.global.dto.GlobalResponseDto;
import com.example.personal_springweek3.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public GlobalResponseDto createPost(PostRequestDto postRequestDto, @RequestHeader("ACCESS_TOKEN") String token) {

        String nickname = jwtUtil.getNicknameFromToken(token);

        postRepository.save(new Post(postRequestDto, nickname));

        return new GlobalResponseDto("Success Save Post", HttpStatus.OK.value());
    }

    @Transactional
    public List<PostResponseDto> findAllPost() {

        List<Post> foundPosts = postRepository.findAll();
        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        for (Post post : foundPosts) {

            Optional<Comment> foundCommnets = commentRepository.findByPost(post);

            PostResponseDto postResponseDto = new PostResponseDto(post, foundCommnets);
            postResponseDtos.add(postResponseDto);
        }


        return postResponseDtos;
    }

    @Transactional
    public PostResponseDto findOnePost(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        Optional<Comment> foundCommnets = commentRepository.findByPost(post);

        return new PostResponseDto(post, foundCommnets);
    }

    @Transactional
    public GlobalResponseDto updatePost(Long postId, PostRequestDto postRequestDto, @RequestHeader("ACCESS_TOKEN") String token) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        String nickname = jwtUtil.getNicknameFromToken(token);

        if (!nickname.equals(post.getAuthor())) {
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

        String nickname = jwtUtil.getNicknameFromToken(token);

        if (!nickname.equals(post.getAuthor())) {
            throw new RuntimeException("Account Check");
        }

        postRepository.deleteById(postId);

        return new GlobalResponseDto("Success Delete Post", HttpStatus.OK.value());
    }

    //내가 쓴 글 게시글 조회하기
    @Transactional
    public List<PostResponseDto> findMyCreatePost(@RequestHeader("ACCESS_TOKEN") String token) {

        List<Post> foundPosts = postRepository.findAll();
        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        String nickname = jwtUtil.getNicknameFromToken(token);

        for (Post foundPost : foundPosts) {
            if (nickname.equals(foundPost.getAuthor())) {

                Optional<Comment> foundCommnets = commentRepository.findByPost(foundPost);

                postResponseDtos.add(new PostResponseDto(foundPost, foundCommnets));
            }
        }

        return postResponseDtos;

    }

    //내가 좋아요한 글 조회하기
    @Transactional
    public List<PostResponseDto> findMyLikePost(Account account) {

        List<Post> foundPosts = postRepository.findAll();
        List<PostResponseDto> postResponseDtos = new ArrayList<>();


        for (Post foundPost : foundPosts) {
            Optional<Like> foundLike = likeRepository.findByPostAndAccount(foundPost, account);

            if (foundLike.isPresent()) {

                Optional<Comment> foundCommnets = commentRepository.findByPost(foundPost);

                postResponseDtos.add(new PostResponseDto(foundPost, foundCommnets));
            }
        }

        return postResponseDtos;
    }
}
