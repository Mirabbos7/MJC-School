package com.mjc.school.repository.impl;

import com.mjc.school.repository.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepositoryJpa extends JpaRepository<Tag, Long> {
}
