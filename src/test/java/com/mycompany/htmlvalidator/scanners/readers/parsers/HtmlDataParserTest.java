package com.mycompany.htmlvalidator.scanners.readers.parsers;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.HtmlAttributeParserMock;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.HtmlClosingParserMock;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.HtmlElementParserMock;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReaderMock;

public class HtmlDataParserTest {
    public static final List<Character> DEFAULT_INPUT = Arrays.asList('<', '>', '<', 'o', 't', 'h', 'e', 'r', 'd', 'a', 't', 'a', '>');
    public static final List<Character> MISSING_OPENING_INPUT = DEFAULT_INPUT.subList(1, DEFAULT_INPUT.size());
    public static final List<Character> MISSING_CLOSING_INPUT = Arrays.asList('<', '<', 'o', 't', 'h', 'e', 'r', 'd', 'a', 't', 'a', '>');
    
    public static final String DEFAULT_EXP_REMAINING_INPUT = "<otherdata>";
    public static final String INTERRUPT_EXP_REMAINING_INPUT = DEFAULT_INPUT.get(1) + DEFAULT_EXP_REMAINING_INPUT;
    public static final String DEFAULT_ELEMENT = "someElementName";
    public static final List<HtmlAttribute> DEFAULT_ATTR = Arrays.asList(new HtmlAttribute("someName", "someData"),
                                                                         new HtmlAttribute("otherName", "otherData"));
    
    private HtmlClosingParserMock closingParser;
    private HtmlElementParserMock elementParser;
    private HtmlAttributeParserMock attributeParser;
    
    private PushbackAndPositionReaderMock input;
    private HtmlDataParser parser;
    
    
    @Before
    public void setUp() {
        this.closingParser = new HtmlClosingParserMock(null);
        this.elementParser = new HtmlElementParserMock(null);
        this.attributeParser = new HtmlAttributeParserMock(null);
        this.setState(DEFAULT_INPUT);
    }
    
    @Test
    public void testParse_ClosingParser_ResultIsTrue()  {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.closingParser.setData(expData);
        
        // Apply
        HtmlData result = this.parse();
        data = result.isClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_ResultIsFalse()  {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.closingParser.setData(expData);
        
        // Apply
        HtmlData result = this.parse();
        data = result.isClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_StandardParse_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = DEFAULT_EXP_REMAINING_INPUT;
        String data;
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_ResultIsTrue_EndOfInputParsingException()  {
        // Arrange
        boolean expData = true;
        boolean data;
        
        ParsingException exception = this.createException("EndOfInput");
        this.closingParser = new HtmlClosingParserMock(exception);
        this.closingParser.setData(expData);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        HtmlData result = this.parse();
        data = result.isClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_ResultIsFalse_EndOfInputParsingException()  {
        // Arrange
        boolean expData = false;
        boolean data;
        
        ParsingException exception = this.createException("EndOfInput");
        this.closingParser = new HtmlClosingParserMock(exception);
        this.closingParser.setData(expData);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        HtmlData result = this.parse();
        data = result.isClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_ExceptionParse_EndOfInputParsingException_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = INTERRUPT_EXP_REMAINING_INPUT;
        String data;
        
        ParsingException exception = this.createException("EndOfInput");
        this.closingParser = new HtmlClosingParserMock(exception);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_ResultIsTrue_UnclosedTagException()  {
        // Arrange
        boolean expData = true;
        boolean data;
        
        ParsingException exception = this.createException("UnclosedTag");
        this.closingParser = new HtmlClosingParserMock(exception);
        this.closingParser.setData(expData);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        HtmlData result = this.parse();
        data = result.isClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_ResultIsFalse_UnclosedTagException()  {
        // Arrange
        boolean expData = false;
        boolean data;
        
        ParsingException exception = this.createException("UnclosedTag");
        this.closingParser = new HtmlClosingParserMock(exception);
        this.closingParser.setData(expData);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        HtmlData result = this.parse();
        data = result.isClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_ExceptionDuringParse_UnclosedTagException_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = INTERRUPT_EXP_REMAINING_INPUT;
        String data;
        
        ParsingException exception = this.createException("UnclosedTag");
        this.closingParser = new HtmlClosingParserMock(exception);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_ResultIsTrue_UnexpectedCloseTagException()  {
        // Arrange
        boolean expData = true;
        boolean data;
        
        ParsingException exception = this.createException("UnexpectedClose");
        this.closingParser = new HtmlClosingParserMock(exception);
        this.closingParser.setData(expData);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        HtmlData result = this.parse();
        data = result.isClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_ResultIsFalse_UnexpectedCloseTagException()  {
        // Arrange
        boolean expData = false;
        boolean data;
        
        ParsingException exception = this.createException("UnexpectedClose");
        this.closingParser = new HtmlClosingParserMock(exception);
        this.closingParser.setData(expData);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        HtmlData result = this.parse();
        data = result.isClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_ExceptionnDuringParse_UnexpectedCloseTagException()  {
        // Arrange
        String expData = DEFAULT_EXP_REMAINING_INPUT;
        String data;
        
        ParsingException exception = this.createException("UnexpectedClose");
        this.closingParser = new HtmlClosingParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ElementParser_ResultIsDefaultString()  {
        // Arrange
        String expData = DEFAULT_ELEMENT;
        String data;
        
        this.elementParser.setElement(expData);
        
        // Apply
        HtmlData result = this.parse();
        data = result.getName();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ElementParser_ResultIsEmptyString()  {
        // Arrange
        String expData = "";
        String data;
        
        this.elementParser.setElement(expData);
        
        // Apply
        HtmlData result = this.parse();
        data = result.getName();
        
        // Assert
        assertEquals(expData, data);
        
    }
    
    @Test
    public void testParse_ElementParser_StandardParse_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = DEFAULT_EXP_REMAINING_INPUT;
        String data;
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test 
    public void testParse_ElementParser_ResultIsDefaultString_EndOfInputParsingException()  {
        // Arrange
        String expData = DEFAULT_ELEMENT;
        String data;
        
        ParsingException exception =  this.createException("EndOfInput");
        this.elementParser = new HtmlElementParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        this.elementParser.setElement(expData);
        
        // Apply
        HtmlData result = this.parse();
        data = result.getName();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ElementParser_ExceptionDuringParse_EndOfInputParsingException_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = INTERRUPT_EXP_REMAINING_INPUT;
        String data;
        
        ParsingException exception = new EndOfInputParsingException(new Point(0, 0), null);
        this.elementParser = new HtmlElementParserMock(exception);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ElementParser_ResultIsDefaultString_UnclosedTagException()  {
        // Arrange
        String expData = DEFAULT_ELEMENT;
        String data;
        
        ParsingException exception = new UnclosedTagParsingException(new Point(0, 0), null);
        
        this.elementParser = new HtmlElementParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        this.elementParser.setElement(expData);
        
        // Apply
        HtmlData result = this.parse();
        data = result.getName();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ElementParser_ExceptionDuringParse_UnclosedTagException_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = INTERRUPT_EXP_REMAINING_INPUT;
        String data;
        
        ParsingException exception = new UnclosedTagParsingException(new Point(0, 0), null);
        this.elementParser = new HtmlElementParserMock(exception);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ElementParser_ResultIsDefaultString_UnexpectedCloseTagException()  {
        // Arrange
        String expData = DEFAULT_ELEMENT;
        String data;
        
        ParsingException exception =  this.createException("UnexpectedClose");
        
        this.elementParser = new HtmlElementParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        this.elementParser.setElement(expData);
        
        // Apply
        HtmlData result = this.parse();
        data = result.getName();
        
        // assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ElementParser_ExceptionDuringParse_UnexpectedCloseTagException_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = DEFAULT_EXP_REMAINING_INPUT;
        String data;
        
        ParsingException exception =  this.createException("UnexpectedClose");
        this.elementParser = new HtmlElementParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_AttributeParser_ResultIsEmptyAttributeData()  {
        // Arrange
        List<HtmlAttribute> expData = new ArrayList<>();
        List<HtmlAttribute> data;
        
        this.attributeParser.setData(expData);
        
        // Apply
        HtmlData result = this.parse();
        data = result.getAttributes();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_AttributeParser_ResultIsDefaultAttributeData()  {
        // Arrange
        List<HtmlAttribute> expData = DEFAULT_ATTR;
        List<HtmlAttribute> data;
        
        this.attributeParser.setData(expData);
        
        // Apply
        HtmlData result = this.parse();
        data = result.getAttributes();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_AttributeParser_ResultIsDefaultAttributeData_EndOfInputParsingException()  {
        // Arrange
        List<HtmlAttribute> expData = DEFAULT_ATTR;
        List<HtmlAttribute> data;
        
        ParsingException exception =  this.createException("EndOfInput");
        this.attributeParser = new HtmlAttributeParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        this.attributeParser.setData(expData);
        
        // Apply
        HtmlData result = this.parse();
        data = result.getAttributes();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_AttributeParser_ExceptionDuringParsing_EndOfInputParsingException_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = INTERRUPT_EXP_REMAINING_INPUT;
        String data;
        
        ParsingException exception = this.createException("EndOfInput");
        this.attributeParser = new HtmlAttributeParserMock(exception);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_AttributeParser_ResultIsDefaultAttributeData_UnclosedTagException()  {
        //Arrange
        List<HtmlAttribute> expData = DEFAULT_ATTR;
        List<HtmlAttribute> data;
        
        ParsingException exception = this.createException("UnclosedTag");
        this.attributeParser = new HtmlAttributeParserMock(exception);
        this.attributeParser.setData(expData);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        HtmlData result = this.parse();
        data = result.getAttributes();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_AttributeParser_ExceptionDuringParsing_UnclosedTagException_ConsumesOnlyExpectedInput() {
        // Arrange
        String expData = INTERRUPT_EXP_REMAINING_INPUT;
        String data;
        
        ParsingException exception = this.createException("UnclosedTag");
        this.attributeParser = new HtmlAttributeParserMock(exception);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_AttributeParser_ResultIsDefaultAttributeData_UnexpectedCloseTagException() {
        // Arrange
        List<HtmlAttribute> expData = DEFAULT_ATTR;
        List<HtmlAttribute> data;
        
        ParsingException exception = this.createException("UnexpectedClose");
        this.attributeParser = new HtmlAttributeParserMock(exception);
        this.attributeParser.setData(expData);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        HtmlData result = this.parse();
        data = result.getAttributes();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_AttributeParser_ExceptionDuringParsing_UnexpectedCloseTagException_ConsumesOnlyExpectedInput() {
        // Arrange
        String expData = DEFAULT_EXP_REMAINING_INPUT;
        String data;
        
        ParsingException exception = this.createException("UnexpectedClose");
        this.attributeParser = new HtmlAttributeParserMock(exception);
        this.setState(DEFAULT_INPUT);
        
        // Arrange
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_HasOpeningAndClosingTag_ResultIsEmptyHtmlData() {
        // Arrange
        MutableHtmlData expData = new MutableHtmlData();
        expData.confirmOpeningTag();
        expData.confirmClosingTag();
        HtmlData data;
        
        // Apply
        data = this.parse();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MissingOpeningTag_ExceptionDuringParsing_ResultIsEmptyHtmlData_EmptyData() {
        // Arrange
        HtmlData expData = new MutableHtmlData();
        HtmlData data;
        
        this.closingParser.setData(true);
        this.elementParser.setElement(DEFAULT_ELEMENT);
        this.attributeParser.setData(DEFAULT_ATTR);
        
        this.setState(MISSING_OPENING_INPUT);
        
        // Apply
        data = this.parse();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MissingOpeningtag_ExceptionDuringParsing_ConsumesOnlyExpectedInput() {
        // Arrange
        String expData = INTERRUPT_EXP_REMAINING_INPUT;
        String data;
        
        this.setState(MISSING_OPENING_INPUT);
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MissingClosingTag_ExceptionDuringParsing_ResultIsFullHtmlData_DefaultData() {
        // Arrange
        boolean closing = true;
        
        MutableHtmlData expData = new MutableHtmlData();
        HtmlData data;
        
        expData.confirmOpeningTag();
        
        this.closingParser.setData(closing);
        expData.setIsClosing(closing);
        
        this.elementParser.setElement(DEFAULT_ELEMENT);
        expData.setName(DEFAULT_ELEMENT);
        
        
        this.attributeParser.setData(DEFAULT_ATTR);
        expData.setAttributes(DEFAULT_ATTR);
        
        this.setState(MISSING_CLOSING_INPUT);
        
        // Apply
        data = this.parse();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MissingClosingTag_ExceptionDuringParsing_ConsumesOnlyExpectedInput() {
        // Arrange
        String expData = DEFAULT_EXP_REMAINING_INPUT;
        String data;
        
        this.setState(MISSING_CLOSING_INPUT);
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    public void setState(List<Character> data) {
        this.parser = new HtmlDataParser(this.closingParser, this.elementParser, this.attributeParser);
        this.input = new PushbackAndPositionReaderMock(new LinkedList<>(data));
    }
    
    @Test(expected=InvalidStateException.class)
    public void testParse_InvalidClosingParser_ThrowsExpectedException() {
        // Arrange
        this.parser = new HtmlDataParser(null, this.elementParser, this.attributeParser);
        
        // Apply + Assert
        this.parse();
    }
    
    @Test(expected=InvalidStateException.class)
    public void testParse_InvalidElementParser_ThrowsExpectedException() {
        // Arrange
        this.parser = new HtmlDataParser(this.closingParser, null, this.attributeParser);
        
        // Apply + Assert
        this.parse();
    }
    
    @Test(expected=InvalidStateException.class)
    public void testParse_InvalidAttributeParser_ThrowsExpectedException() {
        // Arrange
        this.parser = new HtmlDataParser(this.closingParser, this.elementParser, null);
        
        // Apply + Assert
        this.parse();
    }
    
    @After
    public void clearState() {
        this.closingParser = null;
        this.elementParser = null;
        this.attributeParser = null;
        
        this.parser = null;
        this.input = null;
    }
    
    private HtmlData parse() {
        try {
            return this.parser.parse(this.input);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    
    private String getRemainingData() {
        return this.input.getRemainingData();
    }
    
    private ParsingException createException(String name) {
        switch (name.toLowerCase()) {
            case "endofinput"       :   return this.createEndOfInputException();
            case "unexpectedclose"  :   return this.createUnexpectedCloseTagException();
            case "unclosedtag"      :   return this.createUnclosedTagException();
        }
        return null;
    }
    
    
    private ParsingException createEndOfInputException() {
        return new EndOfInputParsingException(new Point(0,0), null);
    }
    
    
    private ParsingException createUnexpectedCloseTagException() {
        return new UnexpectedCloseTagParsingException(new Point(0,0), null);
    }
    
    
    private ParsingException createUnclosedTagException() {
        return new UnclosedTagParsingException(new Point(0,0), null);
    }
}
