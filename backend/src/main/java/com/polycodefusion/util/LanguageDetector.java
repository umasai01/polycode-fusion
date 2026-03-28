package com.polycodefusion.util;

public class LanguageDetector {

    public static String detect(String fileName) {

        if (fileName == null) return "UNKNOWN";

        if (fileName.endsWith(".java")) return "JAVA";
        if (fileName.endsWith(".py")) return "PYTHON";
        if (fileName.endsWith(".js")) return "JAVASCRIPT";
        if (fileName.endsWith(".cpp") || fileName.endsWith(".cc")) return "CPP";
        if (fileName.endsWith(".cs")) return "C_SHARP";

        return "UNKNOWN";
    }
}