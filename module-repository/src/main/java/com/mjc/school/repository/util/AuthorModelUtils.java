package com.mjc.school.repository.util;

import com.mjc.school.repository.model.AuthorModel;

import java.lang.reflect.Field;

public class AuthorModelUtils {
    public static void resetAutoId() throws Exception {
        Field autoIdField = AuthorModel.class.getDeclaredField("autoId");
        autoIdField.setAccessible(true);
        autoIdField.set(null, 1L);
    }
}