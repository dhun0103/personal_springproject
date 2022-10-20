package com.example.personal_springweek3.account.dto.response;

import com.example.personal_springweek3.account.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Long id;
    private String comments;
    private String author;

    public CommentResponseDto(Comment comment){

        this.createdAt=comment.getCreateAt();
        this.modifiedAt=comment.getModifiedAt();
        this.id=comment.getId();
        this.comments=comment.getComments();
        this.author=comment.getAuthor();
    }

}
