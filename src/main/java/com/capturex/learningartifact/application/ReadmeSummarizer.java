package com.capturex.learningartifact.application;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReadmeSummarizer {
    public String summarize(String readme, int maxChars) {
        if (readme == null || readme.isBlank() || maxChars <= 0) {
            return "";
        }

        List<String> cleanedLines = new ArrayList<>();
        boolean inCodeBlock = false;

        String[] lines = readme.split("\\R");
        for (String rawLine : lines) {
            String line = rawLine.trim();
            if (line.startsWith("```")) {
                inCodeBlock = !inCodeBlock;
                continue;
            }
            if (inCodeBlock || line.isBlank() || line.startsWith("![")) {
                continue;
            }

            line = line.replaceAll("^#+\\s*", "");
            line = line.replaceAll("\\[(.+?)]\\((.+?)\\)", "$1");
            line = line.replaceAll("`([^`]+)`", "$1");
            line = line.replaceAll("\\*\\*(.+?)\\*\\*", "$1");
            line = line.replaceAll("__(.+?)__", "$1");
            line = line.replaceAll("\\s+", " ").trim();

            if (!line.isBlank() && !line.contains("shields.io")) {
                cleanedLines.add(line);
            }
        }

        String merged = String.join(" ", cleanedLines).trim();
        if (merged.isBlank()) {
            return "";
        }

        if (merged.length() <= maxChars) {
            return merged;
        }

        int cut = merged.lastIndexOf(' ', maxChars);
        if (cut <= 0) {
            return merged.substring(0, maxChars);
        }
        return merged.substring(0, cut).trim();
    }
}
