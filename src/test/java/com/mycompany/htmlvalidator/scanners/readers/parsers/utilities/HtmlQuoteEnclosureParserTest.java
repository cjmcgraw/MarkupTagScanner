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

import com.mycompany.htmlvalidator.scanners.readers.parsers.MutableHtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.EndOfInputParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.MissingEnclosureParsingException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReaderMock;

public class HtmlQuoteEnclosureParserTest {
    private static final List<Character> defaultData = Arrays.asList('"', 's', 'o', 'm', 'e', ' ', 'd', 'a', 't', 'a', '"', 'o', 't', 'h', 'e', 'r', ' ', 'd', 'a', 't', 'a');
    
    private HtmlQuoteEnclosureParser parser = new HtmlQuoteEnclosureParser();
    private PushbackAndPositionReaderMock input;
    private LinkedList<Character> inputData;
    private MutableHtmlData result;
    
    @Before
    public void setUp() {
        this.setState(defaultData);
    }
    
    @Test
    public void testParse_WithValidData_GetMethodReturnsCorrectStringIncludingQuotes() throws IOException {
        // Arrange
        String expData = "\"some data\"";
        String data;
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = parser.getQuoteData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_WithValidData_ReturnsInputConsumesQuotesLeavesRemaining() throws IOException {
        // Arrange
        String expData = "other data";
        String data;
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EndOfInputParsingException.class)
    public void testParse_WithInvalidData_EmptyData() throws IOException {
        // Arrange
        this.setState(new ArrayList<Character>());
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test(expected=MissingEnclosureParsingException.class)
    public void testParse_WithInvalidData_MissingOpeningQuote() throws IOException {
        // Arrange
        this.setState(Arrays.asList('o', 't', 'h', 'e', 'r', ' ', 'd', 'a', 't', 'a', '"'));
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test
    public void testParse_WithInvalidData_MissingOpeningQuote_LeavesUnquotedDataAlone() throws IOException {
        // Arrange
        this.setState(Arrays.asList('o', 't', 'h', 'e', 'r', ' ', 'd', 'a', 't', 'a', '"'));
        String expData = "other data\"";
        String data = "";
        
        // Apply
        try {
            this.parser.parse(this.input,  this.result);
        } catch (MissingEnclosureParsingException e) {
            data = this.input.getRemainingData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_WithInvalidData_MissingOpeningQuote_GetMethodGivesEmptyString() throws IOException {
        // Arrange
        this.setState(Arrays.asList('o', 't', 'h', 'e', 'r', ' ', 'd', 'a', 't', 'a', '"'));
        String expData = "";
        String data = "default data to prevent simple pass";
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (MissingEnclosureParsingException e) {
            data = this.parser.getQuoteData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EndOfInputParsingException.class)
    public void testParse_WithInvalidData_MissingClosingQuote() throws IOException {
        // Arrange
        this.setState(Arrays.asList('"', 'o', 't', 'h', 'e', 'r', ' ', 'd', 'a', 't', 'a'));
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test
    public void testParse_WithInvalidData_MissingClosingQuote_ConsumesAllData() throws IOException {
        // Arrange
        this.setState(Arrays.asList('"', 'o', 't', 'h', 'e', 'r', ' ', 'd', 'a', 't', 'a'));
        String expData = "";
        String data = "some data to prevent simple pass";
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (EndOfInputParsingException e) {
            data = this.input.getRemainingData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_WithInvalidData_MissingClosingQuote_StringContainsAllData() throws IOException {
        // Arrange
        this.setState(Arrays.asList('"', 'o', 't', 'h', 'e', 'r', ' ', 'd', 'a', 't', 'a'));
        String expData = "\"other data";
        String data = "";
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (EndOfInputParsingException e) {
            data = this.parser.getQuoteData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    private void setState(List<Character> data) {
        this.inputData = new LinkedList<Character>(data);
        this.input = new PushbackAndPositionReaderMock(this.inputData);
        this.result = new MutableHtmlData();
    }
    
    @After
    public void clearState() {
        this.inputData = null;
        this.input = null;
        this.result = null;
    }
}