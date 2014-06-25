package com.mycompany.htmlvalidator.scanners.readers;

import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mycompany.htmlvalidator.scanners.readers.HtmlBufferedReader;
import com.mycompany.htmlvalidator.scanners.readers.parsers.*;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReaderMock;
public class HtmlBufferedReaderTest {
    public static final List<HtmlData> DEFAULT_DATA = generateStandardData();
    public static final List<Character> STANDARD_INPUT_DATA = Arrays.asList( '<', '>', 's', 'o', 'm', 'e', '<', '>', 'd', 'a', 't', 'a', '<', '>');
    
    // There may be some confusion over what UNUSUAL_INPUT_DATA is doing in this context.
    //   The first index is a closing tag with no opening. Ensuring that the HtmlBufferedReader is willing
    //   to accept any tag enclosure (not just opening). The question marks ('?') represent place holders 
    //   for input that is expected (each tag enclosure is followed by another value) - this is just a side
    //   effect for the way that HtmlDataParserMock functions.
    public static final List<Character> UNUSUAL_INPUT_DATA = Arrays.asList('>', '?', '<', '?', '<', '>');
    
    public static final String STANDARD_THIRD_DATA_EXP_REMAINING = "";
    public static final String STANDARD_SECOND_DATA_EXP_REMAINING = "data<>" + STANDARD_THIRD_DATA_EXP_REMAINING;
    public static final String STANDARD_FIRST_DATA_EXP_REMAINING = "some<>" + STANDARD_SECOND_DATA_EXP_REMAINING;
    
    public static final String UNUSUAL_THIRD_DATA_EXP_REMAINING = "";
    public static final String UNUSUAL_SECOND_DATA_EXP_REMAINING = "<>" + UNUSUAL_THIRD_DATA_EXP_REMAINING;
    public static final String UNUSUAL_FIRST_DATA_EXP_REMAINING = "<?" + UNUSUAL_SECOND_DATA_EXP_REMAINING;
    
    
    private static List<HtmlData> generateStandardData() {
        List<HtmlData> result = new ArrayList<>();
        
        MutableHtmlData firstValue = new MutableHtmlData();
        firstValue.setName("value 1");
        result.add(firstValue);
        
        MutableHtmlData midValue = new MutableHtmlData();
        midValue.setName("value 2");
        result.add(midValue);
        
        MutableHtmlData lastValue = new MutableHtmlData();
        lastValue.setName("value 3");
        result.add(lastValue);
        
        return result;
    }
    
    private HtmlDataParserMock parser;
    private PushbackAndPositionReaderMock input;
    
    private HtmlBufferedReader reader;
    
    @Before
    public void InitializeState() throws IOException {
        this.setState(DEFAULT_DATA, STANDARD_INPUT_DATA);
    }
    
    @Test
    public void testNext_Standard_FirstValue_ReturnsCorrectResult() {
        // Arrange
        HtmlData expData = DEFAULT_DATA.get(0);
        HtmlData data;
        
        // Apply
        data = this.readData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_Unusual_FirstValue_ReturnsCorrectResult() throws IOException {
        // Arrange
        HtmlData expData = DEFAULT_DATA.get(0);
        HtmlData data;
        
        this.setStateUnusual();
        
        // Apply
        data = this.readData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Standard_FirstValue_ParserRecievesOpeningTag() {
        // Arrange
        char expData = HtmlParser.OPEN_TAG_ENCLOSURE;
        char data;
        
        // Apply
        data = parser.getOpenChar();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Unusual_FirstValue_ParserReceivesExpectedOpenTag() throws IOException {
        // Arrange
        char expData = '>';
        char data;
        
        this.setStateUnusual();
        
        // Apply
        data = parser.getOpenChar();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Standard_FirstValue_ParserReceivesClosingTag() {
        // Arrange
        char expData = HtmlParser.CLOSE_TAG_ENCLOSURE;
        char data;
        
        // Apply
        data = parser.getCloseChar();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Unusual_FirstValue_ParserReceivesExpectedCloseTag() throws IOException {
        char expData = '?';
        char data;
        
        this.setStateUnusual();
        
        // Apply
        data = parser.getCloseChar();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Standard_FirstValue_OnlyExpectedDataRemaining() {
        // Arrange
        String expData = STANDARD_FIRST_DATA_EXP_REMAINING;
        String data;
        
        // Apply
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Unusual_FirstValue_OnlyExpectedDataRemaining() throws IOException {
        // Arrange
        String expData = UNUSUAL_FIRST_DATA_EXP_REMAINING;
        String data;
        
        this.setStateUnusual();
        
        // Apply
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_Standard_SecondValue_ReturnsCorrectResult() {
        // Arrange
        HtmlData expData = DEFAULT_DATA.get(1);
        HtmlData data;
        
        // Apply
        data = this.readData();
        data = this.readData();
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_Unusual_SecondValue_ReturnsCorectResult() throws IOException {
        // Arrange
        HtmlData expData = DEFAULT_DATA.get(1);
        HtmlData data;
        
        this.setStateUnusual();
        
        // Apply
        data = this.readData();
        data = this.readData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Standard_SecondValue_ParserReceivesOpeningTag() {
        // Arrange
        char expData = HtmlParser.OPEN_TAG_ENCLOSURE;
        char data;
        
        // Apply
        this.readData();
        data = this.parser.getOpenChar();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Unusual_SecondValue_ParserReceivesExpectedOpeningTag() throws IOException {
        // Arrange
        char expData = '<';
        char data;
        
        this.setStateUnusual();
        
        // Apply
        this.readData();
        data = this.parser.getOpenChar();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Standard_SecondValue_ParserReceivesClosingTag() {
        // Arrange
        char expData = HtmlParser.CLOSE_TAG_ENCLOSURE;
        char data;
        
        // Apply
        this.readData();
        data = this.parser.getCloseChar();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Unusual_SecondValue_ParserReceivesExpectedClosingTag() throws IOException {
        // Arrange
        char expData = '?';
        char data;
        
        this.setStateUnusual();
        
        // Apply
        this.readData();
        data = this.parser.getCloseChar();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Standard_SecondValue_OnlyExpectedDataRemaining() {
        // Arrange
        String expData = STANDARD_SECOND_DATA_EXP_REMAINING;
        String data;
        
        // Apply
        this.readData();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Unusual_SecondValue_OnlyExpectedDataRemaining() throws IOException {
        // Arrange
        String expData = UNUSUAL_SECOND_DATA_EXP_REMAINING;
        String data;
        
        this.setStateUnusual();
        
        // Apply
        this.readData();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_Standard_ThirdValue_ReturnsCorrectResult() {
        // Arrange
        HtmlData expData = DEFAULT_DATA.get(2);
        HtmlData data;
        
        // Apply
        this.readData();
        this.readData();
        data = this.readData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_Unusual_ThirdValue_ReturnsCorrectResult() throws IOException {
        // Arrange
        HtmlData expData = DEFAULT_DATA.get(2);
        HtmlData data;
        
        this.setStateUnusual();
        
        // Apply
        this.readData();
        this.readData();
        data = this.readData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Standard_ThirdValue_ParserReceivesOpeningTag() {
        // Arrange
        char expData = HtmlParser.OPEN_TAG_ENCLOSURE;
        char data;
        
        // Apply
        this.readData();
        this.readData();
        
        data = this.parser.getOpenChar();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Unusual_ThirdValue_ParserReceivesExpectedOpeningTag() throws IOException {
        // Arrange
        char expData = HtmlParser.OPEN_TAG_ENCLOSURE;
        char data;
        
        this.setStateUnusual();
                
        // Apply
        this.readData();
        this.readData();
        
        data = this.parser.getOpenChar();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Standard_ThirdValue_ParserReceivesClosingTag() {
        // Arrange
        char expData = HtmlParser.CLOSE_TAG_ENCLOSURE;
        char data;
        
        // Apply
        this.readData();
        this.readData();
        
        data = this.parser.getCloseChar();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Unusual_ThirdValue_ParserReceivesExpectedClosingTag() throws IOException {
     // Arrange
        char expData = HtmlParser.CLOSE_TAG_ENCLOSURE;
        char data;
        
        this.setStateUnusual();
        
        // Apply
        this.readData();
        this.readData();
        
        data = this.parser.getCloseChar();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Standard_ThirdValue_OnlyExpectedDataRemaining() {
        // Arrange
        String expData = STANDARD_THIRD_DATA_EXP_REMAINING;
        String data;
        
        // Apply
        this.readData();
        this.readData();
        
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPreParse_Unusual_ThirdValue_OnlyExpectedDataRemaining() throws IOException {
        // Arrange
        String expData = UNUSUAL_THIRD_DATA_EXP_REMAINING;
        String data;
        
        this.setStateUnusual();
        
        // Apply
        this.readData();
        this.readData();
        
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testHasNext_HasNextValue_ResultIsTrue() {
        //Arrange
        boolean expData = true;
        boolean data = this.reader.hasNext();
        
        // Apply
        this.readData();
        data = data && this.reader.hasNext();
        this.readData();
        data = data && this.reader.hasNext();
        
        // Assert
        assertEquals(expData, data);
    }
    
    
    @Test
    public void testHasNext_NoNextValue_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        this.readData();
        this.readData();
        this.readData();
        data = this.reader.hasNext();
        
        // Assert
        assertEquals(expData, data);
    }
    
    
    @Test(expected=UnsupportedOperationException.class)
    public void testRemove_ThrowsUnsupportedOperationException() {
        // Apply + Assert
        this.reader.remove();
    }
    
    
    @Test
    public void testClose_ClosesStoredInputReader() throws IOException {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        this.reader.close();
        data = this.input.isClosed();
        
        // Assert
        assertEquals(expData, data);
    }
    
    
    private HtmlData readData() {
        return this.reader.next();
    }
    
    private String getRemainingData() {
        return this.input.getRemainingData();
    }
    
    private void setStateUnusual() throws IOException {
        this.setState(DEFAULT_DATA, UNUSUAL_INPUT_DATA);
    }
    
    private void setState(List<HtmlData> data, List<Character> input) throws IOException {
        this.input = new PushbackAndPositionReaderMock(new LinkedList<>(input));
        this.parser = new HtmlDataParserMock(data);
        
        this.reader = new HtmlBufferedReader(this.parser);
        this.reader.setReader(this.input);
    }
    
    @After
    public void clearState() {
        this.input = null;
        this.parser = null;
        this.reader = null;
    }
}