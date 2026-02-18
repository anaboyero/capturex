package com.capturex.learningartifact.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TagTest {

    @Test
    void should_create_normalized_tag() {
        Tag tag = new Tag("  ARTIFICIALintelligence  ");
        assertThat(tag.value()).isEqualTo("artificialintelligence");
    }

    @Test
    void should_throw_exception_when_empty() {
        assertThatThrownBy(() -> new Tag("   "))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("no puede estar vacía");
    }

    @Test
    void should_throw_exception_when_too_long() {
        String longTag = "a".repeat(51);
        assertThatThrownBy(() -> new Tag(longTag))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test 
    void should_have_underscore_instead_of_spaces() {
        String input = "machine learning";
        Tag tag = new Tag(input);
        assertThat(tag.value()).isEqualTo("machine_learning");
    }


}
