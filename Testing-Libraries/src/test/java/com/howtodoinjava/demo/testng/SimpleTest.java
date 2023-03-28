package com.howtodoinjava.demo.testng;

import org.testng.annotations.Test;

public class SimpleTest {
  private final int param;

  public SimpleTest(int param) {
    this.param = param;
  }

  @Test
  public void testMethodOne() {
    int opValue = this.param + 1;
    System.out.println("Test method one output: " + opValue);
  }

  @Test
  public void testMethodTwo() {
    int opValue = this.param + 2;
    System.out.println("Test method two output: " + opValue);
  }
}
