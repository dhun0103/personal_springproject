package com.example.personal_springweek3.account.entity;

import com.example.personal_springweek3.account.dto.request.AccountRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordConfirm;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();

    public Account(AccountRequestDto accountRequestDto) {
        this.nickname = accountRequestDto.getNickname();
        this.password = accountRequestDto.getPassword();
        this.passwordConfirm = accountRequestDto.getPasswordConfirm();
    }

}
