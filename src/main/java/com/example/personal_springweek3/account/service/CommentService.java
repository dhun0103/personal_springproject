package com.example.personal_springweek3.account.service;

import com.example.personal_springweek3.account.dto.request.CommentRequestDto;
import com.example.personal_springweek3.account.dto.response.CommentResponseDto;
import com.example.personal_springweek3.account.dto.response.PostResponseDto;
import com.example.personal_springweek3.account.entity.Comment;
import com.example.personal_springweek3.account.entity.Like;
import com.example.personal_springweek3.account.entity.Post;
import com.example.personal_springweek3.account.repository.CommentRepository;
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

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;
    private final CommentRepository commentRepository;


    @Transactional
    public GlobalResponseDto createComment(CommentRequestDto commentRequestDto, @RequestHeader("ACCESS_TOKEN") String token) {

        Post post = postRepository.findById(commentRequestDto.getPostId()).orElseThrow(() ->
                new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        String nickname = jwtUtil.getNicknameFromToken(token);

        Comment comment = new Comment(commentRequestDto, post, nickname);
        commentRepository.save(comment);

        return new GlobalResponseDto("Success Save Comment", HttpStatus.OK.value());
    }

    @Transactional
    public List<CommentResponseDto> findAllComment() {
        List<Comment> foundCommnets = commentRepository.findAll();
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();


        for (Comment fountComment  : foundCommnets) {

            commentResponseDtos.add(new CommentResponseDto(fountComment));
        }

        return commentResponseDtos;
    }

    @Transactional
    public CommentResponseDto findOneComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );

        return new CommentResponseDto(comment);
    }

    @Transactional
    public GlobalResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, String token) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );

        String nickname = jwtUtil.getNicknameFromToken(token);

        if(!nickname.equals(comment.getAuthor())){
            throw new RuntimeException("Account Check");
        }

        comment.update(commentRequestDto);

        return new GlobalResponseDto("Success Update Comment", HttpStatus.OK.value());
    }

    @Transactional
    public GlobalResponseDto deleteComment(Long commentId, String token) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        String nickname = jwtUtil.getNicknameFromToken(token);

        if(!nickname.equals(comment.getAuthor())){
            throw new RuntimeException("Account Check");
        }

        commentRepository.deleteById(commentId);

        return new GlobalResponseDto("Success Delete Comment", HttpStatus.OK.value());
    }

    //내가 작성한 댓글 조회하기
    @Transactional
    public List<CommentResponseDto> findMyCreateComment(@RequestHeader("ACCESS_TOKEN") String token) {

        List<Comment> foundCommnets = commentRepository.findAll();
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();

        String nickname = jwtUtil.getNicknameFromToken(token);

        for (Comment fountComment  : foundCommnets) {
            if(nickname.equals(fountComment.getAuthor())){

                commentResponseDtos.add(new CommentResponseDto(fountComment));
            }
        }

        return commentResponseDtos;

    }
}
