package com.example.personal_springweek3.account.controller;

import com.example.personal_springweek3.account.dto.request.AccountRequestDto;
import com.example.personal_springweek3.account.dto.request.LoginRequestDto;
import com.example.personal_springweek3.account.service.AccountService;
import com.example.personal_springweek3.global.dto.GlobalResponseDto;
import com.example.personal_springweek3.jwt.util.JwtUtil;
import com.example.personal_springweek3.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final JwtUtil jwtUtil;
    private final AccountService accountService;

    //회원가입
    @PostMapping("/account/signup")
    public GlobalResponseDto signup(@RequestBody @Valid AccountRequestDto accountRequestDto) {
        return accountService.signup(accountRequestDto);
    }
    //로그인
    @PostMapping("/account/login")
    public GlobalResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return accountService.login(loginRequestDto, response);
    }
    //access token 재발급
    @GetMapping("/issue/token") //access token이 만료됐을 경우
    public GlobalResponseDto issuedToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        response.addHeader(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(userDetails.getAccount().getEmail(), "Access"));

        return new GlobalResponseDto("Success IssuedToken", HttpStatus.OK.value());
    }

}

