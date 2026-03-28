package com.polycodefusion.util;

import java.util.*;

public class MethodExtractor {

    public static List<String> extractMethods(String code) {

        List<String> methods = new ArrayList<>();

        String[] parts = code.split("}");

        for (String part : parts) {

            if (part.contains("(") && part.contains(")")) {
                methods.add(part.trim() + "}");
            }
        }

        return methods;
    }
}