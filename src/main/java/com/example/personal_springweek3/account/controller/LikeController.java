package com.example.personal_springweek3.account.controller;

import com.example.personal_springweek3.account.dto.request.LikeRequestDto;
import com.example.personal_springweek3.account.entity.Account;
import com.example.personal_springweek3.account.service.LikeService;
import com.example.personal_springweek3.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;
    @PostMapping("/like")
    public boolean addLikeOrDeleteLike(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody LikeRequestDto likeRequestDto
    ) {
        Account account = userDetails.getAccount();
        return likeService.addLikeOrDeleteLike(account, likeRequestDto);
    }
}
