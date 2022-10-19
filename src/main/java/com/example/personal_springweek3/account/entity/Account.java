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
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String phoneNumber;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();

    public Account(AccountRequestDto accountRequestDto) {
        this.email = accountRequestDto.getEmail();
        this.password = accountRequestDto.getPassword();
        this.phoneNumber = accountRequestDto.getPhoneNumber();
    }

}
