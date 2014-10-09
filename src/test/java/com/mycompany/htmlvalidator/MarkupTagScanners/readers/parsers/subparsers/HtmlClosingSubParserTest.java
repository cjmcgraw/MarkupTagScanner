package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mycompany.htmlvalidator.MarkupTagScanners.enums.MarkupTag;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.errors.*;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReaderMock;

public class HtmlClosingSubParserTest {
    private static final char CLOSING_ATTR = MarkupTag.CLOSING_ATTRIBUTE.toChar();
    private static final char OPENING_TAG = MarkupTag.OPENING_TAG.toChar();
    private static final char CLOSING_TAG = MarkupTag.CLOSING_TAG.toChar();
    
    private static final List<Character> defaultData = Arrays.asList(CLOSING_ATTR, 'A');
    private static final List<Character> additionalData = Arrays.asList('A', 'B');
    
    private HtmlClosingSubParser parser = new HtmlClosingSubParser();
    private PushbackAndPositionReaderMock input;
    private LinkedList<Character> inputData;
    private HtmlData result;
    
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
        data = this.parser.parse(this.input, this.result).isClosing();
        
        // Assert 
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_WithClosingTagAsFirst_ConsumesClosingtagFromInput() throws IOException {
        // Arrange
        char expData = 'A';
        char data; 
        
        // Apply
        this.parser.parse(this.input, this.result);
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
        data = this.parser.parse(this.input, this.result).isClosing();
        
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
        this.parser.parse(this.input, this.result);
        data = this.inputData.remove();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testParse_WithEmptyData() throws IOException {
        // Arrange
        this.setState(new ArrayList<Character>());
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test
    public void testParse_WithEmptyData_ExceptionHtmlDataMatchesInitialData() throws IOException {
        // Arrange
        this.setState(new ArrayList<Character>());
        
        HtmlData expData = this.result;
        HtmlData data = null;
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (EndOfInputParsingError e) {
            data = e.getHtmlData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=UnexpectedCloseTagParsingError.class)
    public void testParse_WithClosingAngleBracket() throws IOException {
        // Arrange
        this.setState(Arrays.asList(CLOSING_TAG, 'A', 'B', 'C'));
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test
    public void testParse_WithClosingAngleBracket_ExceptionHtmlDataMatchesInitialData() throws IOException {
        // Arrange
        this.setState(Arrays.asList(CLOSING_TAG, 'A', 'B', 'C'));
        
        HtmlData expData = this.result;
        HtmlData data = null;
        
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (UnexpectedCloseTagParsingError e) {
            data = e.getHtmlData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_WithClosingAngleBracket_ConsumesNoElementsAndUnreadsClosingAngleBracket() throws IOException {
        // Arrange
        this.setState(Arrays.asList(CLOSING_TAG, 'A', 'B', 'C'));
        
        String expData = CLOSING_TAG + "ABC";
        String data;
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (UnexpectedCloseTagParsingError e) {}
        
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=UnclosedTagParsingError.class)
    public void testParse_WithOpeningAngleBracket() throws IOException {
        // Arrange
        this.setState(Arrays.asList(OPENING_TAG, 'A', 'B', 'C'));
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test
    public void testParse_WithOpeningAngleBracket_ExceptionHtmlDataMatchesInitialData() throws IOException {
        // Arrange
        this.setState(Arrays.asList(OPENING_TAG, 'A', 'B', 'C'));
        
        HtmlData expData = this.result;
        HtmlData data = null;
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (UnclosedTagParsingError e) {
            data = e.getHtmlData();
        }
        
        // Apply
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_WithOpeningAngleBracket_ConsumesNoElementsAndUnreadsOpeningAngleBracket() throws IOException {
        // Arrange
        this.setState(Arrays.asList(OPENING_TAG , 'A', 'B', 'C'));
        
        String expData = OPENING_TAG + "ABC";
        String data;
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (UnclosedTagParsingError e) {}
        
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    private void setState(List<Character> data) {
        this.inputData = new LinkedList<>(data);
        this.input = new PushbackAndPositionReaderMock(this.inputData);
        this.result = new HtmlData();
    }
    
    @After
    public void clearState() {
        this.inputData = null;
        this.input = null;
        this.result = null;
    }
    
}