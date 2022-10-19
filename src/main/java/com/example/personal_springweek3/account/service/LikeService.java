package com.example.personal_springweek3.account.service;

import com.example.personal_springweek3.account.dto.request.LikeRequestDto;
import com.example.personal_springweek3.account.entity.Account;
import com.example.personal_springweek3.account.entity.Like;
import com.example.personal_springweek3.account.entity.Post;
import com.example.personal_springweek3.account.repository.LikeRepository;
import com.example.personal_springweek3.account.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService
{
    private final PostRepository postRepository;

    private final LikeRepository likeRepository;

    @Transactional
    public boolean addLikeOrDeleteLike(Account account, LikeRequestDto likeRequestDto) {

        Post post = postRepository.findById(likeRequestDto.getPostId()).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        // 좋아요를 누른 상태인지, 누르지 않은 상태인지 확인
        Optional<Like> foundLike = likeRepository.findByPostAndAccount(post, account);

        if (foundLike.isPresent()) {
            likeRepository.delete(foundLike.get());

            // 게시글의 좋아요 수 변경
            List<Like> likes = post.getLikes();
            post.updateLikeCount(likes.size());

            return false;
        } else {
            Like like = new Like(account, post);
            likeRepository.save(like);

            // 게시글의 좋아요 수 변경
            List<Like> likes = post.getLikes();
            post.updateLikeCount(likes.size());

            return true;
        }
    }
}
