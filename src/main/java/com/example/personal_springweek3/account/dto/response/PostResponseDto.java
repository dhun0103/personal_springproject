package com.example.personal_springweek3.account.dto.response;

import com.example.personal_springweek3.account.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {

    private String title;

    private String contents;

    private int likeCount;

    private boolean likeState;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.likeCount = post.getLikeCount();
        this.likeState = post.isLikeState();

    }
}
