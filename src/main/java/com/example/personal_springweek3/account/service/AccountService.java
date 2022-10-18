package com.example.personal_springweek3.account.service;

import com.example.personal_springweek3.account.dto.request.AccountRequestDto;
import com.example.personal_springweek3.account.dto.request.LoginRequestDto;
import com.example.personal_springweek3.account.entity.Account;
import com.example.personal_springweek3.account.entity.RefreshToken;
import com.example.personal_springweek3.account.repository.AccountRepository;
import com.example.personal_springweek3.account.repository.RefreshTokenRepository;
import com.example.personal_springweek3.global.dto.GlobalResponseDto;
import com.example.personal_springweek3.jwt.dto.TokenDto;
import com.example.personal_springweek3.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public GlobalResponseDto signup(AccountRequestDto accountRequestDto) {
        // email 중복 검사
        if(accountRepository.findByEmail(accountRequestDto.getEmail()).isPresent()){
            throw new RuntimeException("Overlap Check");
        }


        accountRequestDto.setEncodePwd(passwordEncoder.encode(accountRequestDto.getPassword()));
        Account account = new Account(accountRequestDto);

        accountRepository.save(account);
        return new GlobalResponseDto("Success signup", HttpStatus.OK.value());
    }

    @Transactional
    public GlobalResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        Account account = accountRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                () -> new RuntimeException("Not found Account")
        );
        //비밀번호 맞는지 확인
        if(!passwordEncoder.matches(loginRequestDto.getPassword(), account.getPassword())) {
            throw new RuntimeException("Not matches Password");
        }
        //토큰 발급해주기
        TokenDto tokenDto = jwtUtil.createAllToken(loginRequestDto.getEmail());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountEmail(loginRequestDto.getEmail());

        if(refreshToken.isPresent()) {//로그아웃한 후 로그인을 다시하는건가
            RefreshToken refreshToken1 = refreshToken.get().updateToken(tokenDto.getRefreshToken());
            refreshTokenRepository.save(refreshToken1);

        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginRequestDto.getEmail());
            refreshTokenRepository.save(newToken);
        }
        //토큰 header에 넣어줘서 클라이언트에 전달하기
        setHeader(response, tokenDto);

        return new GlobalResponseDto("Success Login", HttpStatus.OK.value());

    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }
}

