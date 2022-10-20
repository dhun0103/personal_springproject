package com.example.personal_springweek3.account.repository;

import com.example.personal_springweek3.account.entity.Account;
import com.example.personal_springweek3.account.entity.Like;
import com.example.personal_springweek3.account.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByPostAndAccount(Post post, Account account);


    List<Like> findAllByPostId(Long id);

//    boolean existsByPostAndAccount(Post post, Account account);
}
