package com.example.personal_springweek3.account.repository;

import com.example.personal_springweek3.account.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
