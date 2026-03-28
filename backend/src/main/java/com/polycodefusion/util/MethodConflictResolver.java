package com.polycodefusion.util;

import java.util.*;

public class MethodConflictResolver {

    public static List<String> resolveConflicts(List<String> methods) {

        Map<String, Integer> nameCount = new HashMap<>();
        List<String> updatedMethods = new ArrayList<>();

        for (String method : methods) {

            String methodName = extractMethodName(method);

            if (methodName == null) {
                updatedMethods.add(method);
                continue;
            }

            int count = nameCount.getOrDefault(methodName, 0);

            if (count > 0) {
                String newName = methodName + "_" + count;
                method = renameMethod(method, methodName, newName);
            }

            nameCount.put(methodName, count + 1);
            updatedMethods.add(method);
        }

        return updatedMethods;
    }

    private static String extractMethodName(String method) {
        try {
            String header = method.substring(0, method.indexOf("("));
            String[] parts = header.trim().split(" ");
            return parts[parts.length - 1];
        } catch (Exception e) {
            return null;
        }
    }

    private static String renameMethod(String method, String oldName, String newName) {
        return method.replaceFirst(oldName + "\\(", newName + "(");
    }
}