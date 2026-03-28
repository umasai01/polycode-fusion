package com.polycodefusion.util;

import java.util.*;

public class MethodDeduplicator {

    public static String handleDuplicateMethods(String code) {

        String[] lines = code.split("\n");
        Map<String, Integer> methodCount = new HashMap<>();

        StringBuilder updatedCode = new StringBuilder();

        for (String line : lines) {

            String trimmed = line.trim();

            // detect method signature (simple logic)
            if (trimmed.matches(".*\\s+\\w+\\(.*\\).*\\{?")) {

                String methodName = extractMethodName(trimmed);

                if (methodName != null) {

                    int count = methodCount.getOrDefault(methodName, 0);

                    if (count > 0) {
                        String newName = methodName + "_" + count;
                        line = line.replace(methodName + "(", newName + "(");
                    }

                    methodCount.put(methodName, count + 1);
                }
            }

            updatedCode.append(line).append("\n");
        }

        return updatedCode.toString();
    }

    private static String extractMethodName(String line) {
        try {
            String beforeBracket = line.substring(0, line.indexOf("("));
            String[] parts = beforeBracket.split(" ");
            return parts[parts.length - 1];
        } catch (Exception e) {
            return null;
        }
    }
}