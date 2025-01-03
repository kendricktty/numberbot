package org.forksmash.remotenumberbot.utility.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import org.forksmash.remotenumberbot.utility.exception.BotException;
import org.forksmash.remotenumberbot.utility.exception.InvalidInputException;
import org.forksmash.remotenumberbot.utility.exception.ResultOverflowException;
import org.forksmash.remotenumberbot.utility.exception.ZeroSmallerInputException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class GeneratorTest {
    @InjectMocks
    private RandomNumberGenerator rng;
    @InjectMocks
    private Power2Generator p2g;

    @TestFactory
    Stream<DynamicTest> dynamicTestRNG_ValidRangeOfDigits() {

        return IntStream.range(1, 10)
                .mapToObj(i -> dynamicTest("Random number generator with " + i + " digit(s)",
                        () -> testRNG_ValidRangeOfDigits(i)));
    }

    private void testRNG_ValidRangeOfDigits(int n) {
        // Arrange: No special need to arrange
        // Act
        try {
            long generated = rng.generate(n);
            int numDigits = (int) (Math.log10(generated) + 1);
            // Assert
            assertEquals(n, numDigits);
        } catch (BotException e) {
            fail(e.getClass() + " thrown");
        }
    }

    private void testRNG_OutsidePermittedRange(int n) throws BotException {
        long result = rng.generate(n);
        fail("Successfully generated random number");
    }

    @Test
    public void testRNG_AbovePermittedRange() {
        int n = 10;
        BotException e = assertThrows(ResultOverflowException.class, () -> testRNG_OutsidePermittedRange(n));
        assertEquals("The number you provided is too large. Please enter a smaller number.", e.getMessage());
    }

    @Test
    public void testRNG_BelowPermittedRange() {
        int n = -2;
        BotException e = assertThrows(ZeroSmallerInputException.class, () -> testRNG_OutsidePermittedRange(n));
        assertEquals("The number you provided is too small. Please enter a positive number (for /r) or number larger than 0 (for /p)", e.getMessage());
    }

    @Test
    public void testP2G_logMatchesPlus5() {
        try {
            for (int i = 0; i <= 20; i++) {
                long generated = p2g.generate(i);
                assertEquals(i, log2_minus5(generated));
            }
        } catch (BotException e) {
            fail();
        }
    }

    // Change of base to log 2 and subtract 5
    private int log2_minus5(long x) {
        return ((int) (Math.log(x) / Math.log(2))) - 5;
    }
}
