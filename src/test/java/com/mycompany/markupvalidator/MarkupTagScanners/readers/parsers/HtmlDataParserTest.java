package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlAttribute;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mycompany.markupvalidator.MarkupTagScanners.Attribute;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.errors.*;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.subparsers.*;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReaderMock;

public class HtmlDataParserTest {
    public static final List<Character> DEFAULT_INPUT = Arrays.asList('<', '>', '<', 'o', 't', 'h', 'e', 'r', 'd', 'a', 't', 'a', '>');
    public static final List<Character> MISSING_OPENING_INPUT = DEFAULT_INPUT.subList(1, DEFAULT_INPUT.size());
    public static final List<Character> MISSING_CLOSING_INPUT = Arrays.asList('<', '<', 'o', 't', 'h', 'e', 'r', 'd', 'a', 't', 'a', '>');
    
    public static final String DEFAULT_EXP_REMAINING_INPUT = "<otherdata>";
    public static final String INTERRUPT_EXP_REMAINING_INPUT = DEFAULT_INPUT.get(1) + DEFAULT_EXP_REMAINING_INPUT;
    public static final String DEFAULT_ELEMENT = "someElementName";
    public static final List<Attribute> DEFAULT_ATTR = listify(new HtmlAttribute("someName", "someData"),
                                                               new HtmlAttribute("otherName", "otherData"));
    
    private static List<Attribute> listify(Attribute... attrs) {
        List<Attribute> result = new ArrayList<>();
        for (Attribute attr : attrs)
            result.add(attr);
        
        return result;
    }
    
    private HtmlClosingSubParserMock closingParser;
    private HtmlElementSubParserMock elementParser;
    private HtmlAttributeSubParserMock attributeParser;
    
    private PushbackAndPositionReaderMock input;
    private HtmlDataParser parser;
    private Point location = new Point(123, -456);
    
    @Before
    public void setUp() {
        this.closingParser = new HtmlClosingSubParserMock(null);
        this.elementParser = new HtmlElementSubParserMock(null);
        this.attributeParser = new HtmlAttributeSubParserMock(null);
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
    public void testParse_ClosingParser_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = DEFAULT_EXP_REMAINING_INPUT;
        String data;
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testParse_ClosingParser_ThrowsExpectedException_EndOfInputParsingException()  {
        // Arrange
        ParsingError exception = this.createException("EndOfInput");
        this.closingParser = new HtmlClosingSubParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        this.parse();
    }
    
    @Test
    public void testParse_ClosingParser_ThrowsExpectedException_EndOfInputParsingException_ExceptionResultMatchesExpected() {
        // Arrange 
        HtmlData expData = this.createDataFromException();
        expData.setIsClosing(true);
        
        HtmlData data = null;
        
        ParsingError exception = this.createException("EndOfInput");
        this.closingParser = new HtmlClosingSubParserMock(exception);
        this.closingParser.setData(expData.isClosing());
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        try {
            this.parse();
        } catch (EndOfInputParsingError err) {
            data = err.getHtmlData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_ThrowsExpectedException_EndOfInputParsingException_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = INTERRUPT_EXP_REMAINING_INPUT;
        String data = "";
        
        ParsingError exception = this.createException("EndOfInput");
        this.closingParser = new HtmlClosingSubParserMock(exception);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        try {
            this.parse();
            data = this.getRemainingData();
        } catch (EndOfInputParsingError e) {
            data = this.input.getRemainingData();
        }
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_ThrowsUnclosedTagParsingException_ExceptionIsCaught()  {
        // Arrange
        ParsingError exception = this.createException("UnclosedTag");
        this.closingParser = new HtmlClosingSubParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply + Assert
        this.parse();
    }
    
    @Test
    public void testParse_ClosingParser_UnclosedTagParsingExceptionCaught_ResultMatchesExpected()  {
        // Arrange
        HtmlData expData = this.createDataFromException();
        expData.setIsClosing(true);
        
        HtmlData data = null;
        
        ParsingError exception = this.createException("UnclosedTag");
        this.closingParser = new HtmlClosingSubParserMock(exception);
        this.closingParser.setData(expData.isClosing());
        
        expData.getErrorReporter().addError(exception);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        data = this.parse();
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_UnclosedTagParsingExceptionCaught_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = INTERRUPT_EXP_REMAINING_INPUT;
        String data = "";
        
        ParsingError exception = this.createException("UnclosedTag");
        this.closingParser = new HtmlClosingSubParserMock(exception);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        
        this.parse();
        data = input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_UnexpectedCloseExceptionCaught_ResultIsTrue()  {
        // Arrange
        boolean expData = true;
        boolean data;
        
        ParsingError exception = this.createException("UnexpectedClose");
        this.closingParser = new HtmlClosingSubParserMock(exception);
        this.closingParser.setData(expData);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        HtmlData result = this.parse();
        data = result.isClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_UnexpectedCloseExceptionCaught_ResultIsFalse()  {
        // Arrange
        boolean expData = false;
        boolean data;
        
        ParsingError exception = this.createException("UnexpectedClose");
        this.closingParser = new HtmlClosingSubParserMock(exception);
        this.closingParser.setData(expData);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        HtmlData result = this.parse();
        data = result.isClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ClosingParser_UnexpectedCloseExceptionCaught_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = DEFAULT_EXP_REMAINING_INPUT;
        String data;
        
        ParsingError exception = this.createException("UnexpectedClose");
        this.closingParser = new HtmlClosingSubParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }

    @Test
    public void testParse_InputParser_LocationMatchesExpected() {
        // Arrange
        Point exp = new Point(-1, 1);

        this.input.setPosition(exp);

        // Apply + Assert
        assertEquals(exp, this.parse().location());
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
    public void testParse_ElementParser_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = DEFAULT_EXP_REMAINING_INPUT;
        String data;
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testParse_ElementParser_ThrowsExpectedException_EndOfInputParsingException() {
        // Arrange
        ParsingError exception = this.createException("EndOfInput");
        this.elementParser = new HtmlElementSubParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply + Assert
        this.parse();
    }
    
    @Test 
    public void testParse_ElementParser_ThrowsExpectedException_EndOfInputParsingException_ExceptionResultMatchesExpected()  {
        // Arrange
        HtmlData expData = this.createDataFromException();
        expData.setName(DEFAULT_ELEMENT);
        
        HtmlData data = null;
        
        ParsingError exception =  this.createException("EndOfInput");
        this.elementParser = new HtmlElementSubParserMock(exception);
        this.elementParser.setElement(expData.getName());
         
        this.setState(DEFAULT_INPUT);
        
        // Apply
        try {
            this.parse();
        } catch (EndOfInputParsingError e) {
            data = e.getHtmlData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ElementParser_ThrowsExpectedException_EndOfInputParsingException_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = INTERRUPT_EXP_REMAINING_INPUT;
        String data = "";
        
        ParsingError exception = new EndOfInputParsingError(new Point(0, 0), null);
        this.elementParser = new HtmlElementSubParserMock(exception);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        try {
            this.parse();
        } catch (EndOfInputParsingError e) {
            data = this.getRemainingData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ElementParser_ThrowsExpectedException_ExceptionIsCaught() {
        // Arrange
        ParsingError exception = this.createException("UnclosedTag");
        this.elementParser = new HtmlElementSubParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply + Assert
        this.parse();
    }
    
    @Test
    public void testParse_ElementParser_UnclosedTagParsingExceptionCaught_ResultMatchesExpected()  {
        // Arrange
        HtmlData expData = this.createDataFromException();
        expData.setName(DEFAULT_ELEMENT);
        
        HtmlData data;
        
        ParsingError exception = this.createException("UnclosedTag");
        this.elementParser = new HtmlElementSubParserMock(exception);
        this.elementParser.setElement(expData.getName());
        
        expData.getErrorReporter().addError(exception);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        data = this.parse();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ElementParser_UnclosedTagParsingExceptionCaught_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = INTERRUPT_EXP_REMAINING_INPUT;
        String data = "";
        
        ParsingError exception = this.createException("UnclosedTag");
        this.elementParser = new HtmlElementSubParserMock(exception);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ElementParser_UnexpectedCloseTagExceptionCaught_ResultNameMatches()  {
        // Arrange
        String expData = DEFAULT_ELEMENT;
        String data;
        
        ParsingError exception =  this.createException("UnexpectedClose");
        
        this.elementParser = new HtmlElementSubParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        this.elementParser.setElement(expData);
        
        // Apply
        HtmlData result = this.parse();
        data = result.getName();
        
        // assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ElementParser_UnexpectedCloseTagExceptionCaught_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = DEFAULT_EXP_REMAINING_INPUT;
        String data;
        
        ParsingError exception =  this.createException("UnexpectedClose");
        this.elementParser = new HtmlElementSubParserMock(exception);
        
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
        List<Attribute> expData = new ArrayList<>();
        Iterable<Attribute> data;
        
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
        List<Attribute> expData = DEFAULT_ATTR;
        Iterable<Attribute> data;
        
        this.attributeParser.setData(expData);
        
        // Apply
        HtmlData result = this.parse();
        data = result.getAttributes();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testParse_AttributeParser_ThrowsExpectedException_EndOfInputParsingException()  {
        // Arrange
        ParsingError exception =  this.createException("EndOfInput");
        this.attributeParser = new HtmlAttributeSubParserMock(exception);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        this.parse();
    }
    
    @Test
    public void testParse_AttributeParser_ThrowsExpectedException_EndOfInputParsingException_ExceptionResultMatchesExpected() {
        // Arrange
        HtmlData expData = this.createDataFromException();
        expData.updateAttributes(DEFAULT_ATTR);
        
        HtmlData data = null;
        
        ParsingError exception = this.createException("EndOfInput");
        this.attributeParser = new HtmlAttributeSubParserMock(exception);
        this.attributeParser.setData(DEFAULT_ATTR);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        try {
            this.parse();
        } catch (EndOfInputParsingError e) {
            data = e.getHtmlData();
        }
        
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_AttributeParser_ThrowsExpectedException_EndOfInputParsingException_ConsumesOnlyExpectedInput()  {
        // Arrange
        String expData = INTERRUPT_EXP_REMAINING_INPUT;
        String data = "";
        
        ParsingError exception = this.createException("EndOfInput");
        this.attributeParser = new HtmlAttributeSubParserMock(exception);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        try {
            this.parse();
        } catch (EndOfInputParsingError e) {
            data = this.getRemainingData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_AttributeParser_UnclosedTagParsingException_ExceptionIsCaught()  {
        //Arrange
        ParsingError exception = this.createException("UnclosedTag");
        this.attributeParser = new HtmlAttributeSubParserMock(exception);
        this.setState(DEFAULT_INPUT);
        
        // Apply + Apply
        this.parse();
    }
    
    @Test
    public void testParse_AttributeParser_UnclosedTagParsingExceptionCaugth_ResultMatchesExpected() {
        // Arrange
        HtmlData expData = this.createDataFromException();
        expData.setAttributes(DEFAULT_ATTR);
        
        HtmlData data = null;
        
        ParsingError exception = this.createException("UnclosedTag");
        this.attributeParser = new HtmlAttributeSubParserMock(exception);
        this.attributeParser.setData(DEFAULT_ATTR);
        
        expData.getErrorReporter().addError(exception);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        data = this.parse();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_AttributeParser_UnclosedTagParsingExceptionCaught_ConsumesOnlyExpectedInput() {
        // Arrange
        String expData = INTERRUPT_EXP_REMAINING_INPUT;
        String data = "";
        
        ParsingError exception = this.createException("UnclosedTag");
        this.attributeParser = new HtmlAttributeSubParserMock(exception);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_AttributeParser_UnexpectedCloseTagExceptionCaught_ResultIsDefaultAttributeData() {
        // Arrange
        List<Attribute> expData = DEFAULT_ATTR;
        Iterable<Attribute> data;
        
        ParsingError exception = this.createException("UnexpectedClose");
        this.attributeParser = new HtmlAttributeSubParserMock(exception);
        this.attributeParser.setData(expData);
        this.setState(DEFAULT_INPUT);
        
        // Apply
        HtmlData result = this.parse();
        data = result.getAttributes();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_AttributeParser_UnexpectedCloseTagExceptionCaught_ConsumesOnlyExpectedInput() {
        // Arrange
        String expData = DEFAULT_EXP_REMAINING_INPUT;
        String data;
        
        ParsingError exception = this.createException("UnexpectedClose");
        this.attributeParser = new HtmlAttributeSubParserMock(exception);
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
        HtmlData expData = new HtmlData();
        expData.confirmOpeningTag();
        expData.confirmClosingTag();
        expData.setLocation(this.location);
        HtmlData data;
        
        // Apply
        data = this.parse();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_HasOpeningTag_ResultHasOpeningTagIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        HtmlData result = this.parse();
        data = result.hasOpeningBracket();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_HasClosingTag_ResultHasClosingTagIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        HtmlData result = this.parse();
        data = result.hasClosingBracket();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_HasOpeningTag_ThrowsException_EndOfInputParsingException_ResultHasOpeningTagIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data = !expData;
        
        ParsingError exception = this.createException("EndOfInput");
        this.attributeParser = new HtmlAttributeSubParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        try {
            this.parse();
        } catch (EndOfInputParsingError e) {
            data = e.getHtmlData().hasOpeningBracket();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_HasClosingTag_ThrowsException_EndOfInputParsingException_ResultHasClosingTagIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data = !expData;
        
        ParsingError exception = this.createException("EndOfInput");
        this.attributeParser = new HtmlAttributeSubParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        try {
            this.parse();
        } catch (EndOfInputParsingError e) {
            data = e.getHtmlData().hasClosingBracket();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_HasOpeningTag_UnclosedTagParsingExceptionCaught_ResultHasOpeningTagIsTrue() {
     // Arrange
        boolean expData = true;
        boolean data = !expData;
        
        ParsingError exception = this.createException("UnclosedTag");
        this.attributeParser = new HtmlAttributeSubParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        HtmlData result = this.parse();
        data = result.hasOpeningBracket();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_HasClosingTag_UnclosedTagParsingExceptionCaught_ResultHasClosingTagIsFalse() {
     // Arrange
        boolean expData = false;
        boolean data = !expData;
        
        ParsingError exception = this.createException("UnclosedTag");
        this.attributeParser = new HtmlAttributeSubParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        HtmlData result = this.parse();
        data = result.hasClosingBracket();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_HasOpeningTag_UnexpectedCloseTagParsingExceptionCaught_ResultHasOpeningTagIsTrue() {
     // Arrange
        boolean expData = true;
        boolean data;
        
        ParsingError exception = this.createException("UnexpectedClose");
        this.attributeParser = new HtmlAttributeSubParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        HtmlData result = this.parse();
        data = result.hasOpeningBracket();
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_HasClosingTag_UnexpectedCloseTagParsingExceptionCaught_ResultHasClosingTagIsTrue() {
     // Arrange
        boolean expData = true;
        boolean data;
        
        ParsingError exception = this.createException("UnexpectedClose");
        this.attributeParser = new HtmlAttributeSubParserMock(exception);
        
        this.setState(DEFAULT_INPUT);
        
        // Apply
        HtmlData result = this.parse();
        data = result.hasClosingBracket();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MissingOpeningTag_MissingEnclosureParsingException_ExceptionIsCaught() {
        // Arrange
        this.setState(MISSING_OPENING_INPUT);
        
        // Apply + Assert
        this.parse();
    }
    
    @Test
    public void testParse_MissingOpeningTag_MissingEnclosureParsingExceptionCaught_ResultHasOpeningTagIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data = !expData;
        
        this.setState(MISSING_OPENING_INPUT);
        
        // Apply
        HtmlData result = this.parse();
        data = result.hasOpeningBracket();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MissingOpeningtag_MissingEnclosureParsingExceptionCaught_ConsumesOnlyExpectedInput() {
        // Arrange
        String expData = DEFAULT_EXP_REMAINING_INPUT;
        String data = "";
        
        this.setState(MISSING_OPENING_INPUT);
        
        // Apply
        this.parse();
        data = this.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MissingClosingTag_MissingEnclosureParsingException_ExceptionIsCaught() {
        // Arrange
        this.setState(MISSING_CLOSING_INPUT);
        
        // Apply + Assert
        this.parse();
    }
    
    @Test
    public void testParse_MissingClosingTag_MissingEnclosureParsingExceptionCaught_ResultMatchesExpected() {
        // Arrange
        HtmlData expData = this.createDataFromException();
        expData.setIsClosing(true);
        expData.setName(DEFAULT_ELEMENT);
        expData.setAttributes(DEFAULT_ATTR);
        
        expData.getErrorReporter().addError(this.createException("missingenclosure"));
        
        HtmlData data = null;
        
        this.closingParser.setData(expData.isClosing());
        this.elementParser.setElement(DEFAULT_ELEMENT);
        this.attributeParser.setData(DEFAULT_ATTR);
        
        this.setState(MISSING_CLOSING_INPUT);
        
        // Apply
        data = this.parse();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MissingClosingTag_MissingEnclosureParsingExceptionCaught_ConsumesOnlyExpectedInput() {
        // Arrange
        String expData = DEFAULT_EXP_REMAINING_INPUT;
        String data = "";
        
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
        this.input.setPosition(this.location);
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
    
    private HtmlData createDataFromException() {
        HtmlData data = createData();
        data.confirmOpeningTag();
        return data;
    }

    private HtmlData createData() {
        HtmlData data = new HtmlData();
        data.setLocation(this.location);
        return data;
    }
    
    private ParsingError createException(String name) {
        switch (name.toLowerCase()) {
            case "endofinput"       :   return this.createEndOfInputException();
            case "unexpectedclose"  :   return this.createUnexpectedCloseTagException();
            case "unclosedtag"      :   return this.createUnclosedTagException();
            case "missingenclosure"  :   return this.createMissingEnclosureTagException();
        }
        return null;
    }
    
    private ParsingError createEndOfInputException() {
        return new EndOfInputParsingError(this.location, createData());
    }
    
    private ParsingError createUnexpectedCloseTagException() {
        return new UnexpectedCloseTagParsingError(this.location, createData());
    }
    
    private ParsingError createUnclosedTagException() {
        return new UnclosedTagParsingError(this.location, createData());
    }
    
    private ParsingError createMissingEnclosureTagException() {
        return new MissingEnclosureParsingError(this.location, ' ', ' ', createData());
    }
}
