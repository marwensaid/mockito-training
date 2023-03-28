package com.howtodoinjava.demo.mockito;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mockStatic;

class MockStaticMethods {
  @Test
  void testGetVal() {
    assertEquals("foo", ClassWithStaticMethod.getVal());
    try (MockedStatic<ClassWithStaticMethod> mockStatic = mockStatic(ClassWithStaticMethod.class)) {
      mockStatic.when(ClassWithStaticMethod::getVal).thenReturn("bar");
      assertEquals("bar", ClassWithStaticMethod.getVal());
      mockStatic.verify(ClassWithStaticMethod::getVal);
    }
    assertEquals("foo", ClassWithStaticMethod.getVal());
  }

  @Test
  void testAdd() {
    assertEquals(3, ClassWithStaticMethod.add(1, 2));
    try (MockedStatic mockStatic = mockStatic(ClassWithStaticMethod.class)) {
      mockStatic.when(() -> ClassWithStaticMethod.add(anyInt(), anyInt())).thenReturn(10);
      assertEquals(10, ClassWithStaticMethod.add(1, 2));
      mockStatic.verify(() -> ClassWithStaticMethod.add(1, 2));
    }
    assertEquals(3, ClassWithStaticMethod.add(1, 2));
  }
}

class ClassWithStaticMethod {
  public static String getVal() {
    return "foo";
  }

  public static int add(int... args) {
    return IntStream.of(args).sum();
  }
}
