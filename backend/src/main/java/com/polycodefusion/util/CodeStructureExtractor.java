package com.polycodefusion.util;

import java.util.*;

public class CodeStructureExtractor {

    public static List<String> extractFields(String code) {

        List<String> fields = new ArrayList<>();

        String[] lines = code.split("\n");

        for (String line : lines) {
            String trimmed = line.trim();

            if (trimmed.matches("(private|public|protected).*;")
                    && !trimmed.contains("(")) {
                fields.add(trimmed);
            }
        }

        return fields;
    }

    public static List<String> extractConstructors(String code, String className) {

        List<String> constructors = new ArrayList<>();

        String[] parts = code.split("}");

        for (String part : parts) {
            if (part.contains(className + "(")) {
                constructors.add(part.trim() + "}");
            }
        }

        return constructors;
    }
}