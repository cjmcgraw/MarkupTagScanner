package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

import org.junit.*;

import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReaderMock;

public class WhitespaceConsumerTest {
    public static final String SPACE_SEPARATOR = "    ";
    public static final String TAB_SEPARATOR = "\t\t\t";
    public static final String NEWLINE_SEPARATOR = String.format("%n%n%n%n");
    
    public static final String FIRST_VALUE = "1";
    public static final String SECOND_VALUE = "2";
    public static final String THIRD_VALUE = "3";
    
    public static final String MIXED_DATA = SPACE_SEPARATOR + FIRST_VALUE + TAB_SEPARATOR + SECOND_VALUE + NEWLINE_SEPARATOR + THIRD_VALUE;
    
    private PushbackAndPositionReaderMock input;
    private WhitespaceConsumer consumer = new WhitespaceConsumer();
    
    @Test
    public void testConsume_WithLeadingSpaces_ExpectedDataRemains() throws IOException {
        // Arrange
        this.setState(SPACE_SEPARATOR + FIRST_VALUE);
        
        String expData = FIRST_VALUE;
        String data;
        
        // Apply
        this.consumer.consume(input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testConsume_WithLeadingTabs_ExpectedDataRemains() throws IOException {
        // Arrange
        this.setState(TAB_SEPARATOR + FIRST_VALUE);
        
        String expData = FIRST_VALUE;
        String data;
        
        // Apply
        this.consumer.consume(input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testConsume_WithLeadingNewline_ExpectedDataRemains() throws IOException {
        // Arrange
        this.setState(NEWLINE_SEPARATOR + FIRST_VALUE);
        
        String expData = FIRST_VALUE;
        String data;
        
        // Apply
        this.consumer.consume(input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testConsume_ConsecutiveConsumes_FirstConsumeFirstRead_DataIsFirstValue() throws IOException {
        // Arrange
        this.setState(MIXED_DATA);
        
        String expData = FIRST_VALUE;
        String data;
        
        // Apply
        this.consumer.consume(input);
        data = "" + (char) this.input.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testConsume_ConsecutiveConsumes_SecondConsumeSecondRead_DataIsSecondValue() throws IOException {
        // Arrange
        this.setState(MIXED_DATA);
        
        String expData = SECOND_VALUE;
        String data;
        
        this.consumer.consume(input);
        this.input.read();
        
        // Apply
        this.consumer.consume(input);
        data = "" + (char) this.input.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testConsume_ConsecutiveConsumes_ThirdConsumeThirdRead_DataIsThirdValue() throws IOException {
        // Arrange
        this.setState(MIXED_DATA);
        
        String expData = THIRD_VALUE;
        String data;
        
        this.consumer.consume(input);
        this.input.read();
        this.consumer.consume(input);
        this.input.read();
        
        // Apply
        this.consumer.consume(input);
        data = "" + (char) this.input.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    public void setState(String s) {
        LinkedList<Character> data = new LinkedList<>();
        
        for(int i = 0; i < s.length(); i++)
            data.add(s.charAt(i));
        
        this.input = new PushbackAndPositionReaderMock(data);
    }
    
    @After
    public void tearDown() {
        this.input = null;
    }
}
