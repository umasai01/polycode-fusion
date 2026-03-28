package com.polycodefusion.model;

import java.util.List;

public class ConversionResponse {

    private String targetLanguage;
    private List<String> mergedClasses;
    private String generatedCode;

    // 🔥 NEW FIELDS
    private List<String> sourceFiles;
    private int totalMethods;
    private int totalClasses;

    public ConversionResponse(String targetLanguage,
                              List<String> mergedClasses,
                              String generatedCode,
                              List<String> sourceFiles,
                              int totalMethods,
                              int totalClasses) {

        this.targetLanguage = targetLanguage;
        this.mergedClasses = mergedClasses;
        this.generatedCode = generatedCode;
        this.sourceFiles = sourceFiles;
        this.totalMethods = totalMethods;
        this.totalClasses = totalClasses;
    }

    // Getters
    public String getTargetLanguage() { return targetLanguage; }
    public List<String> getMergedClasses() { return mergedClasses; }
    public String getGeneratedCode() { return generatedCode; }
    public List<String> getSourceFiles() { return sourceFiles; }
    public int getTotalMethods() { return totalMethods; }
    public int getTotalClasses() { return totalClasses; }
}