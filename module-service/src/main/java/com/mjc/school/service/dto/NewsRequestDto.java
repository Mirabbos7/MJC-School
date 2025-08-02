package com.mjc.school.service.dto;

import lombok.Builder;

public record NewsRequestDto(
        String title,
        String content,
        Long authorId
) {
}
