package com.mjc.school.repository.impl;

import com.mjc.school.repository.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepositoryJpa extends JpaRepository<Comment, Long> {
}
