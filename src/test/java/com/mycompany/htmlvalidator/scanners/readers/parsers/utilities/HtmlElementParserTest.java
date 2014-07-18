package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.MutableHtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.*;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReaderMock;

public class HtmlElementParserTest {
    private static final List<Character> defaultData = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', ' ', 'g', 'h');
    private static final List<Character> additionalData = Arrays.asList( ' ', 'a', 'b', 'c', 'd', 'e', 'f');
    
    private HtmlElementParser parser = new HtmlElementParser();
    private PushbackAndPositionReaderMock input;
    private LinkedList<Character> inputData;
    private MutableHtmlData result;
    
    @Before
    public void setUp() {
        this.setState(defaultData);
    }
    
    @Test
    public void testParse_WithValidElementName_ReturnsHtmlData_HasCorrectString() throws IOException {
        // Arrange
        String expData = "abcdef";
        String data;
        
        // Apply
        HtmlData htmlData = this.parser.parse(this.input, this.result);
        data = htmlData.getName();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_WithValidElementName_ModifiesStoredMutableHtmlData_HasCorrectString() throws IOException {
        // Arrange
        String expData = "abcdef";
        String data;
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getName();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_WithValidElementName_ConsumesElementNameFromInput() throws IOException {
        // Arrange
        String expData = "gh";
        String data = "";
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_WithValidElementName_EmptyElementName_ReturnsHtmlData_HasCorrectString() throws IOException {
        // Arrange
        String expData = "";
        String data;
        
        this.setState(additionalData);
        
        // Apply
        HtmlData htmlData = this.parser.parse(this.input, this.result);
        data = htmlData.getName();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_WithElementName_EmptyElementName_ModifiesStoredMutableHtmlData_HasCorrectString() throws IOException {
        // Arrange
        String expData = "";
        String data;
        
        this.setState(additionalData);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getName();
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_WithElementName_EmptyElementName_ConsumesElementNameFromInput() throws IOException {
        // Arrange
        String expData = "abcdef";
        String data = "";
        
        this.setState(additionalData);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=UnexpectedCloseTagParsingException.class)
    public void testParse_WithInvalidElementName_ContainsClosingTag() throws IOException {
        // Arrange
        this.setState(Arrays.asList('a', 'b', 'c', '>', 'e', 'f', 'g'));
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test
    public void testParse_WithInvalidElementName_ContainsClosingTag_ExceptionHtmlDataMatchesInitialData() throws IOException {
        // Arrange
        this.setState(Arrays.asList('a', 'b', 'c', '>', 'e', 'f', 'g'));
        
        HtmlData expData = this.result;
        HtmlData data = null;
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (UnexpectedCloseTagParsingException e) {
            data = e.getHtmlData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_WithInvalidElementName_ContainsClosingTag_ConsumesElementFromInputExceptForClosingTagAndProceedingData() throws IOException {
        // Arrange
        this.setState(Arrays.asList('a', 'b', 'c', '>', 'e', 'f', 'g'));
        
        String expData = ">efg";
        String data = "";
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (UnexpectedCloseTagParsingException e) {}
        
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=UnclosedTagParsingException.class)
    public void testParse_WithInvalidElementName_ContainsOpeningTag() throws IOException {
        // Arrange
        this.setState(Arrays.asList('a', 'b', 'c', '<', 'e', 'f', 'g'));
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test
    public void testParse_WithInvalidElementName_ContainsOpeningTag_ExceptionHtmlDataMatchesInitialData() throws IOException {
        // Arrange
        this.setState(Arrays.asList('a', 'b', 'c', '<', 'e', 'f', 'g'));
        
        HtmlData expData = this.result;
        HtmlData data = null;
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (UnclosedTagParsingException e) {
            data = e.getHtmlData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_WithInvalidElementName_ContainsOpeningTag_ConsumesElementFromInputExceptForOpeningTagAndProceedingData() throws IOException {
        // Arrange
        this.setState(Arrays.asList('a', 'b', 'c', '<', 'e', 'f', 'g'));
        
        String expData = "<efg";
        String data = "";
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (UnclosedTagParsingException e) {}
        
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EndOfInputParsingException.class)
    public void testParse_WithInvalidElementName_EmptyInput() throws IOException {
        // Arrange
        this.setState(new ArrayList<Character>());
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    private void setState(List<Character> data) {
        this.inputData = new LinkedList<Character>(data);
        this.input = new PushbackAndPositionReaderMock(inputData);
        this.result = new MutableHtmlData();
    }
    
    @After
    public void clearState() {
        this.inputData = null;
        this.input = null;
        this.result = null;
    }
}
