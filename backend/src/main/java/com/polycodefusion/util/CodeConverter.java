package com.polycodefusion.util;

public class CodeConverter {

    public static String convertToTarget(
            String content,
            String sourceLanguage,
            String targetLanguage) {

        if (targetLanguage == null) return content;

        targetLanguage = targetLanguage.toUpperCase();
        sourceLanguage = sourceLanguage.toUpperCase();

        // SAME LANGUAGE → return directly
        if (sourceLanguage.equals(targetLanguage)) {
            return content;
        }

        switch (targetLanguage) {

            case "JAVA":
                return convertToJava(content, sourceLanguage);

            case "PYTHON":
                return convertToPython(content, sourceLanguage);

            case "JAVASCRIPT":
                return convertToJS(content, sourceLanguage);

            default:
                return content;
        }
    }

    // ================= TARGET: JAVA =================
    private static String convertToJava(String content, String sourceLanguage) {

        switch (sourceLanguage) {

            case "PYTHON":
                return pythonToJava(content);

            case "JAVASCRIPT":
                return jsToJava(content);

            case "C":
            case "CPP":
                return cToJava(content);

            default:
                return content;
        }
    }

    // ================= TARGET: PYTHON =================
    private static String convertToPython(String content, String sourceLanguage) {

        StringBuilder py = new StringBuilder();

        py.append("# Converted Code\n\n");

        String[] lines = content.split("\n");

        for (String line : lines) {
            py.append("# ").append(line).append("\n");
        }

        return py.toString();
    }

    // ================= TARGET: JS =================
    private static String convertToJS(String content, String sourceLanguage) {

        StringBuilder js = new StringBuilder();

        js.append("// Converted Code\n\n");

        String[] lines = content.split("\n");

        for (String line : lines) {
            js.append("// ").append(line).append("\n");
        }

        return js.toString();
    }

    // ================= PYTHON → JAVA =================
    private static String pythonToJava(String content) {

        StringBuilder javaCode = new StringBuilder();
        javaCode.append("public class ConvertedPython {\n\n");

        String[] lines = content.split("\n");

        for (String line : lines) {

            line = line.trim();

            if (line.startsWith("class")) continue;

            if (line.startsWith("def")) {
                String methodName = line.split(" ")[1].split("\\(")[0];
                javaCode.append("    public void ").append(methodName).append("() {\n");
            } else {
                javaCode.append("        // ").append(line).append("\n");
            }
        }

        javaCode.append("    }\n}");
        return javaCode.toString();
    }

    // ================= JS → JAVA =================
    private static String jsToJava(String content) {

        StringBuilder javaCode = new StringBuilder();
        javaCode.append("public class ConvertedJS {\n\n");

        String[] lines = content.split("\n");

        for (String line : lines) {

            line = line.trim();

            if (line.contains("(") && line.contains(")") && line.contains("{")) {

                String methodName = line.split("\\(")[0].replace("function", "").trim();
                javaCode.append("    public void ").append(methodName).append("() {\n");
            } else {
                javaCode.append("        // ").append(line).append("\n");
            }
        }

        javaCode.append("    }\n}");
        return javaCode.toString();
    }

    // ================= C → JAVA =================
    private static String cToJava(String content) {

        StringBuilder javaCode = new StringBuilder();
        javaCode.append("public class ConvertedC {\n\n");

        String[] lines = content.split("\n");

        for (String line : lines) {

            line = line.trim();

            if (line.contains("(") && line.contains(")") && line.contains("{")) {

                String[] parts = line.split("\\(")[0].split(" ");
                String methodName = parts[parts.length - 1];

                javaCode.append("    public void ").append(methodName).append("() {\n");
            } else {
                javaCode.append("        // ").append(line).append("\n");
            }
        }

        javaCode.append("    }\n}");
        return javaCode.toString();
    }
}