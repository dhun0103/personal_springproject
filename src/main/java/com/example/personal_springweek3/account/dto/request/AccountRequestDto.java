package com.example.personal_springweek3.account.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class AccountRequestDto {

    @NotBlank(message = "nickname은 공백일 수 없습니다.")
    @Size(min = 4, max = 12, message = "nickname은 4~12 개의 문자만 허용합니다.")
    @Pattern(regexp = "[a-z\\d]*${4,12}", message = "nickname은 소문자와 숫자의 조합만 허용합니다.")
    private String nickname;
    @NotBlank(message = "password는 공백일 수 없습니다.")
    @Size(min = 8, max = 16, message = "password는 8~!6 개의 문자만 허용합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$"
            , message = "password는 무조건 영문, 숫자, 특수문자를 각각 1글자 이상 포함해야 합니다.")
    private String password;
    @NotBlank
    private String passwordConfirm;

    public AccountRequestDto(String nickname, String password, String passwordConfirm) {
        this.nickname = nickname;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public void setEncodePwd(String encodePwd) {

        this.password = encodePwd;
    }

}
