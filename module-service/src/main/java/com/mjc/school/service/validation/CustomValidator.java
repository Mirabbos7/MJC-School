package com.mjc.school.service.validation;

import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.exceptions.ErrorCodes;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;

@Component
public class CustomValidator {
    public static final int TITLE_MAX_LENGTH = 30;
    public static final int TITLE_MIN_LENGTH = 5;
    public static final int NEWS_CONTENT_MAX_LENGTH = 255;
    public static final int NEWS_CONTENT_MIN_LENGTH = 5;
    public static final int COMMENTS_CONTENT_MAX_LENGTH = 255;
    public static final int COMMENTS_CONTENT_MIN_LENGTH = 3;
    public static final int AUTHOR_NAME_MAX_LENGTH = 15;
    public static final int AUTHOR_NAME_MIN_LENGTH = 3;
    public static final int TAG_NAME_MAX_LENGTH = 15;
    public static final int TAG_NAME_MIN_LENGTH = 3;


    public void validateLength(String param, int minLength, int maxLength, String error) {
        if (param.length() < minLength || param.length() > maxLength) {
            throw new ValidationException(String.format(ErrorCodes.VALIDATION.getErrorMessage(), error));
        }
    }

    public void validateNews(NewsDtoRequest news) {
        validateLength(news.title(), TITLE_MIN_LENGTH, TITLE_MAX_LENGTH, "Length of title must be between " + TITLE_MIN_LENGTH + " and " + TITLE_MAX_LENGTH);
        validateLength(news.content(), NEWS_CONTENT_MIN_LENGTH, NEWS_CONTENT_MAX_LENGTH, "Length of content must be between " + NEWS_CONTENT_MIN_LENGTH + " and " + NEWS_CONTENT_MAX_LENGTH);
    }

    public void validateAuthor(AuthorDtoRequest author) {
        validateLength(author.name(), AUTHOR_NAME_MIN_LENGTH, AUTHOR_NAME_MAX_LENGTH, "Length of author`s name must be between " + AUTHOR_NAME_MIN_LENGTH + " and " + AUTHOR_NAME_MAX_LENGTH);
    }

    public void validateTag(TagDtoRequest tag) {
        validateLength(tag.name(), TAG_NAME_MIN_LENGTH, TAG_NAME_MAX_LENGTH, "Length of tag`s name must be between " + TAG_NAME_MIN_LENGTH + " and " + TAG_NAME_MAX_LENGTH);
    }

    public void validateComment(CommentDtoRequest comment) {
        validateLength(comment.content(), COMMENTS_CONTENT_MIN_LENGTH, COMMENTS_CONTENT_MAX_LENGTH, "Length of comment`s content must be between " + COMMENTS_CONTENT_MIN_LENGTH + " and " + COMMENTS_CONTENT_MAX_LENGTH);
    }


}

