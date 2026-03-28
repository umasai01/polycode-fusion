package com.polycodefusion.util;

public class CodeCleaner {

    public static String removeClassWrapper(String code, String className) {

        // remove "public class User {"
        code = code.replaceAll("public\\s+class\\s+" + className + "\\s*\\{", "");

        // remove closing }
        if (code.trim().endsWith("}")) {
            code = code.substring(0, code.lastIndexOf("}"));
        }

        return code.trim();
    }
    public static String removeWrapperClass(String code) {

        int firstBrace = code.indexOf("{");
        int lastBrace = code.lastIndexOf("}");

        if (firstBrace != -1 && lastBrace != -1) {
            return code.substring(firstBrace + 1, lastBrace).trim();
        }

        return code;
    }
}