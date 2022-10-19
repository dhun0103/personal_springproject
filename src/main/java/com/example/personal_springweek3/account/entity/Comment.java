package com.example.personal_springweek3.account.entity;

import com.example.personal_springweek3.account.dto.request.CommentRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String comments;

    @Column(nullable = false)
    private String userEmail;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    public Comment(CommentRequestDto commentRequestDto, Post post, String userEmail) {
        this.comments = commentRequestDto.getComments();
        this.post = post;
        this.userEmail=userEmail;
    }

    public void update(CommentRequestDto commentRequestDto) {

        this.comments = commentRequestDto.getComments();
    }
}
