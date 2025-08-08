package com.mjc.school.service.validators;

import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.exceptions.ContentLException;
import com.mjc.school.service.exceptions.DataFormatException;
import com.mjc.school.service.exceptions.TitleLException;

public interface Validate {
    boolean validate(NewsDTO newsDTO) throws TitleLException, ContentLException, DataFormatException;
}
