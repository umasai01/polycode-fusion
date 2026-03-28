package com.polycodefusion.parser;

import java.util.*;
import java.util.regex.*;

public class ClassExtractor {

    public static List<String> extractClasses(String content, String language) {

        List<String> classes = new ArrayList<>();

        Pattern pattern = null;

        switch (language) {
            case "JAVA":
                pattern = Pattern.compile("class\\s+(\\w+)");
                break;

            case "PYTHON":
                pattern = Pattern.compile("class\\s+(\\w+)");
                break;

            case "JAVASCRIPT":
                pattern = Pattern.compile("class\\s+(\\w+)");
                break;

            default:
                return classes;
        }

        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            classes.add(matcher.group(1));
        }

        return classes;
    }
}