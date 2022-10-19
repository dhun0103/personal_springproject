package com.example.personal_springweek3.account.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @NotBlank
    private String nickname;
    @NotBlank
    private String password;

    public LoginRequestDto(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

}
