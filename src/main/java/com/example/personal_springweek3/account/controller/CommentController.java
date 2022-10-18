package com.example.personal_springweek3.account.controller;

import com.example.personal_springweek3.account.dto.request.CommentRequestDto;
import com.example.personal_springweek3.account.dto.response.CommentResponseDto;
import com.example.personal_springweek3.account.entity.Comment;
import com.example.personal_springweek3.account.repository.CommentRepository;
import com.example.personal_springweek3.account.service.CommentService;
import com.example.personal_springweek3.global.dto.GlobalResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    //댓글 작성하기
    @PostMapping("/create/comments")
    public GlobalResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, @RequestHeader("ACCESS_TOKEN") String token) {

        return commentService.createComment(commentRequestDto, token);
    }

    //댓글 전체 조회하기
    @GetMapping("/read/comments")
    public List<Comment> findAllComment() {

        return commentRepository.findAll();
    }
    //댓글 각각 조회하기
    @GetMapping("/read/comments/{commentId}")
    public CommentResponseDto findOneComment(@PathVariable Long commentId) {

        return commentService.findDetail(commentId);
    }

    //댓글 수정하기
    @PutMapping("/update/comments/{commentId}")
    public GlobalResponseDto updateComment(
            @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto,@RequestHeader("ACCESS_TOKEN") String token)
    {
        return commentService.updateComment(commentId, commentRequestDto, token);
    }
    //댓글 삭제하기
    @DeleteMapping("/delete/comments/{commentId}")
    public GlobalResponseDto deleteComment(@PathVariable Long commentId,  @RequestHeader("ACCESS_TOKEN") String token) {

        return commentService.deleteComment(commentId, token);
    }
}
