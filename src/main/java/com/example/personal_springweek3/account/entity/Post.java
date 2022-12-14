package com.example.personal_springweek3.account.entity;

import com.example.personal_springweek3.account.dto.request.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

    @NotBlank
    private String author;

    //FetchType.EAGER는 기본이고 즉시로딩을 의미, CascadeType.REMOVE는 글 삭제시 댓글도 삭제
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();

    private int likeCount;



    public Post(PostRequestDto postRequestDto, String nickname) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
        this.author = nickname;
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }

    public void updateLikeCount(int size) {

        this.likeCount = size;
    }
}
