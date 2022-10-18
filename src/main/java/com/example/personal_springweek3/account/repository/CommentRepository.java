package com.example.personal_springweek3.account.repository;

import com.example.personal_springweek3.account.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
