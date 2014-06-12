package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReaderMock;

public class HtmlClosingParserTest {
    private static final List<Character> defaultData = Arrays.asList('/', 'A');
    private static final List<Character> additionalData = Arrays.asList('A', 'B');
    
    private HtmlClosingParser parser = new HtmlClosingParser();
    private PushbackAndPositionReaderMock input;
    private LinkedList<Character> inputData;
    
    @Before
    public void setUp() {
        this.setState(defaultData);
    }
    
    @Test
    public void testParse_WithClosingTagAsFirst_ReturnsTrue() throws IOException {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert 
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_WithClosingTagAsFirst_ConsumesClosingtagFromInput() throws IOException {
        // Arrange
        char expData = 'A';
        char data; 
        
        // Apply
        this.parser.parse(this.input);
        data = this.inputData.remove();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_WithClosingTagNotPresent_ReturnsFalse() throws IOException {
        // Arrange
        boolean expData = false;
        boolean data;
        this.setState(additionalData);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_WithClosingTagNotPresent_ReplacesInputValue() throws IOException {
        // Arrange
        char expData = 'A';
        char data;
        this.setState(additionalData);
        
        // Apply
        this.parser.parse(this.input);
        data = this.inputData.remove();
        
        // Assert
        assertEquals(expData, data);
    }
    
    private void setState(List<Character> data) {
        this.inputData = new LinkedList<>(data);
        this.input = new PushbackAndPositionReaderMock(this.inputData);
    }
    
    @After
    public void clearState() {
        this.inputData = null;
        this.input = null;
    }
    
}