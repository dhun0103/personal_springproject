package com.example.personal_springweek3.account.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    private Long postId;

    @NotBlank
    private String comments;

}
