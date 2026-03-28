package com.polycodefusion.merge;

import com.polycodefusion.model.UploadedFile;

import java.util.*;

public class ClassMerger {

    public static Map<String, List<UploadedFile>> groupByClass(List<UploadedFile> files) {

        Map<String, List<UploadedFile>> classMap = new HashMap<>();

        for (UploadedFile file : files) {

            for (String className : file.getClassNames()) {

                classMap.putIfAbsent(className, new ArrayList<>());
                classMap.get(className).add(file);
            }
        }

        return classMap;
    }
}