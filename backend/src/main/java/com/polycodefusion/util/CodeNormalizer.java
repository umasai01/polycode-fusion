package com.polycodefusion.util;

public class CodeNormalizer {

    public static String normalize(String code) {
        return code
                .replaceAll("\\s+", "")      // remove spaces
                .replaceAll("\r", "")
                .replaceAll("\n", "")
                .toLowerCase();
    }
}