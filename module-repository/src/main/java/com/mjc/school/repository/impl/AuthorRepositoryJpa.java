package com.mjc.school.repository.impl;

import com.mjc.school.repository.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepositoryJpa extends JpaRepository<Author, Long> {
}
