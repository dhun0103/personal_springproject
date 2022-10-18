package com.example.springweek3.account.entity;

import com.example.springweek3.account.dto.request.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String userEmail;

    //FetchType.EAGER는 기본이고 즉시로딩을 의미, CascadeType.REMOVE는 글 삭제시 댓글도 삭제
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments = new ArrayList<>();


    public Post(PostRequestDto postRequestDto, String userEmail) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
        this.userEmail = userEmail;
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }
}
