package com.example.personal_springweek3.account.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

}
