package com.example.personal_springweek3.account.repository;

import com.example.personal_springweek3.account.entity.Comment;
import com.example.personal_springweek3.account.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    Optional<Comment> findByPost(Post post);
}

