package com.example.personal_springweek3.account.dto.response;

import com.example.personal_springweek3.account.entity.Comment;
import com.example.personal_springweek3.account.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Long id;
    private String title;
    private String contents;
    private String author;
    private int likeCount;

    private Optional<Comment> comments;


    public PostResponseDto(Post post, Optional<Comment> foundCommnets) {
        this.createdAt = post.getCreateAt();
        this.modifiedAt = post.getModifiedAt();
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.author = post.getAuthor();
        this.likeCount = post.getLikeCount();
        this.comments = foundCommnets;

    }
}
