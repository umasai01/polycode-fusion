package com.polycodefusion.util;

public class CodeFormatter {

    public static String format(String code) {

        return code
                .replaceAll("\\s+\\(", "(")
                .replaceAll("\\{\\s+", "{\n")
                .replaceAll(";\\s*", ";\n")
                .replaceAll("\\n+", "\n")
                .trim();
    }
}