package com.taskmanagement.taskmanager.validation;

import com.fasterxml.jackson.databind.util.StdConverter;

public class NormalizeTextConverter extends StdConverter<String, String> {

    @Override
    public String convert(String value) {
        if (value == null) {
            return null;
        }

        return value.strip();
    }
}
