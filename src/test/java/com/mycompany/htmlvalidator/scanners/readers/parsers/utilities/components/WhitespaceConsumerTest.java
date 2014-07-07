package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import static org.junit.Assert.*;

import java.io.*;
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
        this.consumer.parse(input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testConsume_WithLeaderSpaces_ExpectedNumberReturnedRepresentingSpacesParsed() throws IOException {
        // Arrange
        this.setState(SPACE_SEPARATOR + FIRST_VALUE);
        
        int expData = SPACE_SEPARATOR.length();
        int data;
        
        // Apply
        data = this.consumer.parse(input);
        
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
        this.consumer.parse(input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testConsume_WithLeadingTabs_ExpectedNumberReturnedRepresentingSpacesParsed() throws IOException {
        // Arrange
        this.setState(TAB_SEPARATOR + FIRST_VALUE);
        
         int expData = TAB_SEPARATOR.length();
         int data;
         
         // Apply
         data = this.consumer.parse(input);
         
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
        this.consumer.parse(input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testConsume_WithLeadingNewline_ExpectedNumberReturnedRepresentingSpacesParsed() throws IOException {
        // Arrange
        this.setState(NEWLINE_SEPARATOR + FIRST_VALUE);
        
        int expData = NEWLINE_SEPARATOR.length();
        int data;
        
        // Apply
        data = this.consumer.parse(input);
        
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
        this.consumer.parse(input);
        data = "" + (char) this.input.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testConsume_ConsecutiveConsumes_FirstConsume_ExpectedNumberReturnedRepresentingSpacesParsed() throws IOException {
        // Arrange
        this.setState(MIXED_DATA);
        
        int expData = SPACE_SEPARATOR.length();
        int data;
        
        // Apply
        data = this.consumer.parse(input);
        
        // assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testConsume_ConsecutiveConsumes_SecondConsumeSecondRead_DataIsSecondValue() throws IOException {
        // Arrange
        this.setState(MIXED_DATA);
        
        String expData = SECOND_VALUE;
        String data;
        
        this.consumer.parse(input);
        this.input.read();
        
        // Apply
        this.consumer.parse(input);
        data = "" + (char) this.input.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testConsume_ConsecutiveConsumes_SecondConsume_ExpectedNumberReturnedRepresentingSpacesParsed() throws IOException {
        // Arrange
        this.setState(MIXED_DATA);
        
        int expData = TAB_SEPARATOR.length();
        int data;
        
        this.consumer.parse(input);
        this.input.read();
        
        // Apply
        data = this.consumer.parse(input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testConsume_ConsecutiveConsumes_ThirdConsumeThirdRead_DataIsThirdValue() throws IOException {
        // Arrange
        this.setState(MIXED_DATA);
        
        String expData = THIRD_VALUE;
        String data;
        
        this.consumer.parse(input);
        this.input.read();
        this.consumer.parse(input);
        this.input.read();
        
        // Apply
        this.consumer.parse(input);
        data = "" + (char) this.input.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testConsume_ConsecutiveConsumes_ThirdConsume_ExpectedNumberReturnedRepresentingSpacesParsed() throws IOException {
        // Arrange
        this.setState(MIXED_DATA);
        
        int expData = NEWLINE_SEPARATOR.length();
        int data;
        
        this.consumer.parse(input);
        this.input.read();
        this.consumer.parse(input);
        this.input.read();
        
        // Apply
        data = this.consumer.parse(input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testConsume_ContainsNoWhitespace_DataRemainsUnchanged() throws IOException {
        // Arrange
        String expData = FIRST_VALUE + SECOND_VALUE + THIRD_VALUE;
        String data;
        
        this.setState(expData);
        
        // Apply
        this.consumer.parse(input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testConsume_ContainsNoWhitespace_ExpectedNumberReturnedRepresentsSpacesParsed() throws IOException {
        // Arrange
        this.setState(FIRST_VALUE + SECOND_VALUE + THIRD_VALUE);
        
        int expData = 0;
        int data;
        
        // Apply
        data = this.consumer.parse(input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EOFException.class)
    public void testConsume_InputContainsOnlyWhitespace_ThrowsEOFException() throws IOException {
        // Arrange
        this.setState(SPACE_SEPARATOR + TAB_SEPARATOR + NEWLINE_SEPARATOR);
        
        // Apply + Assert
        this.consumer.parse(input);
    }
    
    @Test(expected=EOFException.class)
    public void testConsume_EmptyInput_ThrowsEOFException() throws IOException {
        // Arrange
        this.setState("");
        
        // Apply + Assert
        this.consumer.parse(input);
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