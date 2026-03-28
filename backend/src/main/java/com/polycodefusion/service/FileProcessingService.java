package com.polycodefusion.service;

import com.polycodefusion.model.UploadedFile;
import com.polycodefusion.model.ConversionResponse;
import com.polycodefusion.util.LanguageDetector;
import com.polycodefusion.parser.ClassExtractor;
import com.polycodefusion.util.CodeCleaner;
import com.polycodefusion.merge.ClassMerger;
import com.polycodefusion.util.CodeConverter;
import com.polycodefusion.util.CodeStructureExtractor;
import com.polycodefusion.util.MethodExtractor;
import com.polycodefusion.util.SmartMethodMerger;
import com.polycodefusion.util.MethodConflictResolver;
import com.polycodefusion.util.CodeNormalizer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.polycodefusion.util.CodeFormatter;
import java.io.IOException;
import java.util.*;

@Service

public class FileProcessingService {

    public List<UploadedFile> processFiles(MultipartFile[] files) {

        List<UploadedFile> uploadedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String content = new String(file.getBytes());
                String language = LanguageDetector.detect(file.getOriginalFilename());

                List<String> classNames =
                        ClassExtractor.extractClasses(content, language);

                List<String> methodNames =
                        MethodExtractor.extractMethods(content);

                UploadedFile uploadedFile = new UploadedFile(
                        file.getOriginalFilename(),
                        content,
                        file.getSize(),
                        language,
                        classNames,
                        methodNames
                );

                uploadedFiles.add(uploadedFile);

            } catch (IOException e) {
                throw new RuntimeException("Error reading file: " + file.getOriginalFilename());
            }
        }

        return uploadedFiles;
    }

    public ConversionResponse convertToTargetLanguage(
            List<UploadedFile> files,
            String targetLanguage) {

        Map<String, List<UploadedFile>> classGroups =
                ClassMerger.groupByClass(files);

        StringBuilder finalCode = new StringBuilder();
        List<String> mergedClasses = new ArrayList<>();

        finalCode.append("// Converted to ").append(targetLanguage).append("\n\n");

        for (String className : classGroups.keySet()) {

            mergedClasses.add(className);

            finalCode.append("public class ").append(className).append(" {\n\n");

            List<UploadedFile> groupedFiles = classGroups.get(className);

            // 🔥 COLLECT ALL
            List<String> allMethods = new ArrayList<>();
            List<String> allFields = new ArrayList<>();
            List<String> allConstructors = new ArrayList<>();

            for (UploadedFile file : groupedFiles) {

                String convertedCode = CodeConverter.convertToTarget(
                        file.getContent(),
                        file.getDetectedLanguage(),
                        targetLanguage
                );

                // remove outer class
                convertedCode = CodeCleaner.removeWrapperClass(convertedCode);

                // extract
                allMethods.addAll(MethodExtractor.extractMethods(convertedCode));
                allFields.addAll(CodeStructureExtractor.extractFields(convertedCode));
                allConstructors.addAll(
                        CodeStructureExtractor.extractConstructors(convertedCode, className)
                );
            }

            // ================= METHODS =================
            List<String> uniqueMethods = SmartMethodMerger.mergeMethods(allMethods);
            uniqueMethods = MethodConflictResolver.resolveConflicts(uniqueMethods);

            List<String> cleanedMethods = new ArrayList<>();
            for (String m : uniqueMethods) {
                if (m != null && !m.isBlank()) {
                    cleanedMethods.add(m.trim());
                }
            }
            uniqueMethods = cleanedMethods;

            // ================= FIELDS =================
            Set<String> fieldSet = new LinkedHashSet<>();

            for (String field : allFields) {
                if (field == null || field.isBlank()) continue;

                String normalized = field.replaceAll("\\s+", "")
                                         .replace(";", "")
                                         .toLowerCase();

                fieldSet.add(normalized + "|" + field.trim());
            }

            List<String> uniqueFields = new ArrayList<>();
            for (String f : fieldSet) {
                uniqueFields.add(f.split("\\|")[1]);
            }

            // ================= CONSTRUCTORS =================
            Set<String> constructorSet = new LinkedHashSet<>();

            for (String cons : allConstructors) {
                if (cons == null || cons.isBlank()) continue;

                String norm = cons.replaceAll("\\s+", "").toLowerCase();
                constructorSet.add(norm + "|" + cons.trim());
            }

            List<String> uniqueConstructors = new ArrayList<>();
            for (String c : constructorSet) {
                uniqueConstructors.add(c.split("\\|")[1]);
            }

            // ================= BUILD OUTPUT =================

            // Fields
            for (String field : uniqueFields) {
                finalCode.append("    ").append(field).append("\n");
            }

            finalCode.append("\n");

            // Constructors
            for (String cons : uniqueConstructors) {
                String[] lines = cons.split("\n");
                for (String line : lines) {
                    finalCode.append("    ").append(line.trim()).append("\n");
                }
                finalCode.append("\n");
            }

            // Methods
            for (String method : uniqueMethods) {
                String[] lines = method.split("\n");
                for (String line : lines) {
                    finalCode.append("    ").append(line.trim()).append("\n");
                }
                finalCode.append("\n");
            }

            finalCode.append("}\n\n");
        }

     // 🔥 collect file names
        List<String> fileNames = new ArrayList<>();
        for (UploadedFile f : files) {
            fileNames.add(f.getFileName());
        }

        // 🔥 total methods count
        int totalMethods = 0;
        for (UploadedFile f : files) {
            if (f.getMethodNames() != null)
                totalMethods += f.getMethodNames().size();
        }

        return new ConversionResponse(
                targetLanguage,
                mergedClasses,
                CodeFormatter.format(finalCode.toString()),
                fileNames,
                totalMethods,
                mergedClasses.size()
        );
    }
}