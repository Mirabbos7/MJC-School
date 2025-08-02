package com.mjc.school.repository.impl;

import com.mjc.school.repository.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepositoryJpa extends JpaRepository<News, Long> {
}
