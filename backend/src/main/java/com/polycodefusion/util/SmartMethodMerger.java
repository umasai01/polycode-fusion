package com.polycodefusion.util;

import java.util.*;

public class SmartMethodMerger {

    public static List<String> mergeMethods(List<String> methods) {

        List<String> result = new ArrayList<>();

        for (String method : methods) {

            boolean isDuplicate = false;

            for (String existing : result) {

                if (areMethodsSame(method, existing)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (!isDuplicate) {
                result.add(method);
            }
        }

        return result;
    }

    private static boolean areMethodsSame(String m1, String m2) {

        String norm1 = normalize(m1);
        String norm2 = normalize(m2);

        return norm1.equals(norm2);
    }

    private static String normalize(String method) {
        return method
                .replaceAll("\\s+", "")
                .replaceAll("//.*", "")
                .replaceAll("\".*?\"", "")
                .toLowerCase();
    }
}