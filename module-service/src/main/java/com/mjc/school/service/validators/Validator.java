package com.mjc.school.service.validators;

import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.exceptions.ContentLException;
import com.mjc.school.service.exceptions.DataFormatException;
import com.mjc.school.service.exceptions.TitleLException;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Validator implements Validate{
    private static volatile Validator instance = null;//NOSONAR

    public static Validator getInstance() {
        if (instance == null) {
            synchronized (Validator.class) {
                if (instance == null) {
                    instance = new Validator();
                }
            }
        }
        return instance;
    }

    private Validator() {

    }

    @Override
    public boolean validate(NewsDTO newsDTO) throws TitleLException, ContentLException, DataFormatException {
        if (newsDTO.getTitle().length() <=5 || newsDTO.getTitle().length() > 30) {
            throw new TitleLException("Title length must be between 5 and 30 characters");
        } else if (newsDTO.getContent().length() <=5 || newsDTO.getContent().length() > 255) {
            throw new ContentLException("Content length must be between 5 and 255 characters");
        } else if (!isValidISO8601Date(newsDTO.getCreateDate()) || !isValidISO8601Date(newsDTO.getLastUpdateDate())) {
            throw new DataFormatException("Dates must be in ISO 8601 format");
        } else {
            return true;
        }
    }

    private boolean isValidISO8601Date(LocalDateTime date) {
        if (date == null) return false;
        try {
            DateTimeFormatter.ISO_DATE_TIME.format(date);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

}
