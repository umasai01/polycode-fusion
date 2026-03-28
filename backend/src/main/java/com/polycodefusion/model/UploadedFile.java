package com.polycodefusion.model;

import java.util.List;

public class UploadedFile {

    private String fileName;
    private String content;
    private long size;
    private String detectedLanguage;
    private List<String> classNames;
    private List<String> methodNames;

    public UploadedFile(String fileName, String content, long size,
                        String detectedLanguage,
                        List<String> classNames,
                        List<String> methodNames) {
        this.fileName = fileName;
        this.content = content;
        this.size = size;
        this.detectedLanguage = detectedLanguage;
        this.classNames = classNames;
        this.methodNames = methodNames;
    }

    public String getFileName() { return fileName; }
    public String getContent() { return content; }
    public long getSize() { return size; }
    public String getDetectedLanguage() { return detectedLanguage; }
    public List<String> getClassNames() { return classNames; }
    public List<String> getMethodNames() { return methodNames; }
}