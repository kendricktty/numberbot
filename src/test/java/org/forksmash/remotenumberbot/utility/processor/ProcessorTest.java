package org.forksmash.remotenumberbot.utility.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import org.forksmash.remotenumberbot.utility.exception.BotException;
import org.forksmash.remotenumberbot.utility.exception.InvalidInputException;
import org.forksmash.remotenumberbot.utility.generator.Generator;
import org.forksmash.remotenumberbot.utility.generator.Power2Generator;
import org.forksmash.remotenumberbot.utility.generator.RandomNumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ProcessorTest {
    @InjectMocks
    private MessageProcessor messageProcessor;
    @Mock
    private RandomNumberGenerator randomGenerator;
    @Mock
    private Power2Generator power2Generator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void processRequest_rngHappy() throws BotException {
        String request = "/r 3";
        when(randomGenerator.generate(3)).thenReturn(123L);
        String response = messageProcessor.processRequest(request);
        assertTrue(response.contains("Here's your generated random number!"));
        assertTrue(response.contains("The number returned is <b>123</b>."));
        // Each when() statement should be followed up with a verify() to verify that the mocked method was performed
        verify(randomGenerator).generate(3);
    }

    @Test
    public void processRequest_p2gHappy() throws BotException {
        String request = "/p 3";
        when(power2Generator.generate(3)).thenReturn(256L);
        String response = messageProcessor.processRequest(request);
        assertTrue(response.contains("Here's your power of 2!"));
        assertTrue(response.contains("The number returned is <b>256</b>."));
        verify(power2Generator).generate(3);
    }

    @Test
    public void processRequest_incomplete() {
        BotException e = assertThrows(InvalidInputException.class, () -> {
            messageProcessor.processRequest("/r");
        });
        assertEquals("Request incomplete. Type /r or /p, followed by a space, and then a number.", e.getMessage());
    }

    @Test
    public void processRequest_invalid() {
        BotException e = assertThrows(InvalidInputException.class, () -> {
            messageProcessor.processRequest("hello");
        });
        String invalidInputMessage = "Invalid input. Enter /r or /p, followed by a space, and then a number.";
        assertEquals(invalidInputMessage, e.getMessage());
    }
}
