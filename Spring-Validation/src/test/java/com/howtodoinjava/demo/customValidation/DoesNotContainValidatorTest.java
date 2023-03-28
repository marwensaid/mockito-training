package com.howtodoinjava.demo.customValidation;

import com.howtodoinjava.demo.validation.DoesNotContainValidator;
import com.howtodoinjava.demo.validation.annotation.DoesNotContain;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
class DoesNotContainValidatorTest {

  private DoesNotContainValidator validator;
  private ConstraintValidatorContext context;

  @BeforeEach
  public void setUp() {
    this.validator = new DoesNotContainValidator();
    this.context = mock(ConstraintValidatorContext.class);
  }

  @Test
  void testIsValidWithValidValue() {
    DoesNotContain doesNotContain = mock(DoesNotContain.class);
    when(doesNotContain.chars()).thenReturn(new String[]{"@", "#"});
    this.validator.initialize(doesNotContain);

    String value = "abcde";
    boolean result = this.validator.isValid(value, this.context);

    assertTrue(result);
  }

  @Test
  void testIsValidWithInvalidValue() {
    DoesNotContain doesNotContain = mock(DoesNotContain.class);
    when(doesNotContain.chars()).thenReturn(new String[]{"@", "#"});
    this.validator.initialize(doesNotContain);

    String value = "abc@de";
    boolean result = this.validator.isValid(value, this.context);

    assertFalse(result);
  }

  @Test
  void testIsValidWithNullValue() {
    DoesNotContain doesNotContain = mock(DoesNotContain.class);
    when(doesNotContain.chars()).thenReturn(new String[]{"@", "#"});
    this.validator.initialize(doesNotContain);

    String value = null;
    boolean result = this.validator.isValid(null, this.context);

    assertTrue(result);
  }

  @Test
  void testIsValidWithEmptyValue() {
    DoesNotContain doesNotContain = mock(DoesNotContain.class);
    when(doesNotContain.chars()).thenReturn(new String[]{"@", "#"});
    this.validator.initialize(doesNotContain);

    String value = "";
    boolean result = this.validator.isValid(value, this.context);

    assertTrue(result);
  }
}
