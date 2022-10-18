package com.example.springweek3.account.dto.response;

import com.example.springweek3.account.entity.Comment;
import com.example.springweek3.account.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private String comments;

    public CommentResponseDto(Comment comment){
        this.comments=comment.getComments();
    }

}
