package com.howtodoinjava.demo.testng;

import org.testng.annotations.Test;
import java.util.stream.LongStream;
import static org.testng.Assert.assertEquals;

public class HelloTestNg {
    @Test
    public void testAddOperation_withValidArgs_thenCorrect() {
        assertEquals(Calculator.add(1, 2, 3), 6);
    }
}

class Calculator {
    public static long add(long... nums){
        return LongStream.of(nums).sum();
    }
}
