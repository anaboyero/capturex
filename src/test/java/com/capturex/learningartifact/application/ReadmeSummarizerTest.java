package com.capturex.learningartifact.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("ReadmeSummarizer Tests")
class ReadmeSummarizerTest {

    private final ReadmeSummarizer summarizer = new ReadmeSummarizer();

    @Test
    @DisplayName("should return empty when readme is null")
    void should_return_empty_when_readme_is_null() {
        String result = summarizer.summarize(null, 500);
        assertEquals("", result);
    }

    @Test
    @DisplayName("should return empty when readme is blank")
    void should_return_empty_when_readme_is_blank() {
        String result = summarizer.summarize("   \n\t", 500);
        assertEquals("", result);
    }

    @Test
    @DisplayName("should return empty when max chars is zero or negative")
    void should_return_empty_when_max_chars_is_zero_or_negative() {
        assertEquals("", summarizer.summarize("# Title", 0));
        assertEquals("", summarizer.summarize("# Title", -1));
    }

    @Test
    @DisplayName("should clean markdown, links, inline code and emphasis")
    void should_clean_markdown_links_inline_code_and_emphasis() {
        String readme = """
                # Project Title
                A **simple** project with `Java`.
                Learn from [guide](https://example.com/guide) quickly.
                __Focused__ on learning.
                """;

        String result = summarizer.summarize(readme, 500);

        assertEquals(
                "Project Title A simple project with Java. Learn from guide quickly. Focused on learning.",
                result
        );
    }

    @Test
    @DisplayName("should ignore code blocks, images and badge links")
    void should_ignore_code_blocks_images_and_badges() {
        String readme = """
                ![badge](https://img.shields.io/badge/test-green)
                # CaptureX
                Build artifacts fast.
                ```java
                System.out.println("ignore me");
                ```
                More docs here.
                """;

        String result = summarizer.summarize(readme, 500);

        assertEquals("CaptureX Build artifacts fast. More docs here.", result);
        assertTrue(!result.contains("ignore me"));
        assertTrue(!result.contains("shields.io"));
    }

    @Test
    @DisplayName("should truncate at last space before max chars")
    void should_truncate_at_last_space_before_max_chars() {
        String readme = "Alpha beta gamma delta epsilon";

        String result = summarizer.summarize(readme, 15);

        assertEquals("Alpha beta", result);
    }

    @Test
    @DisplayName("should truncate hard when no space exists")
    void should_truncate_hard_when_no_space_exists() {
        String readme = "abcdefghij";

        String result = summarizer.summarize(readme, 5);

        assertEquals("abcde", result);
    }
}
