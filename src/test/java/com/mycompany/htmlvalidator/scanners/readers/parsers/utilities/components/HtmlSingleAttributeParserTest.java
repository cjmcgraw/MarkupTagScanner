package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedList;

import org.junit.*;

import com.mycompany.htmlvalidator.scanners.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlAttribute;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions.*;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReaderMock;

public class HtmlSingleAttributeParserTest {
    private final static String CLOSING_TAG = "" + MarkupTag.CLOSING_TAG;
    private final static String SEPARATOR = "" + MarkupTag.ATTRIBUTE_VALUE_SEPARATOR;
    
    private static final String DEFAULT_CLOSING = " " + CLOSING_TAG;
    private static final String MISSING_SPACE_CLOSING = CLOSING_TAG;
    
    private static final String DEFAULT_DATA = "some" + DEFAULT_CLOSING;
    private static final String DATA_NO_SPACE = "some" + MISSING_SPACE_CLOSING;
    private static final String DATA_WITH_VAL = "some" + SEPARATOR + "value" + DEFAULT_CLOSING;
    private static final String DATA_WITH_DOUBLE_QUOTE = "some" + SEPARATOR + "\"" + DEFAULT_CLOSING ;
    private static final String DATA_WITH_SINGLE_QUOTE = "some" + SEPARATOR + "'" +  DEFAULT_CLOSING;
    
    private static final String DEFAULT_REMAINING_ATTRS = " other" + SEPARATOR + "data" + DEFAULT_CLOSING;
    
    private static final String DEFAULT_DATA_WITH_MULTI_ATTRS = "some" + DEFAULT_REMAINING_ATTRS;
    private static final String DATA_WITH_VAL_WITH_MULTI_ATTRS = "some" + SEPARATOR + "value" + DEFAULT_REMAINING_ATTRS;
    private static final String DATA_WITH_DOUBLE_QUOTE_WITH_MULTI_ATTRS = "some" + SEPARATOR + "\"" + DEFAULT_REMAINING_ATTRS;
    private static final String DATA_WITH_SINGLE_QUOTE_WITH_MULTI_ATTRS = "some" + SEPARATOR + "'" + DEFAULT_REMAINING_ATTRS;
     
    private static final String DEFAULT_QUOTE_VALUE = "some quote value";
    
    private static final ComponentException MISSING_CHAR_ERROR = new MissingCharacterComponentException(' ', new Point(0, 0), "some data");
    private static final ComponentException UNEXPECTED_CHAR_ERROR = new UnexpectedCharacterComponentException(' ', new Point(0, 0), "some data");
    
    private HtmlQuoteEnclosureParserMock enclosureParser;
    private PushbackAndPositionReaderMock input;
    private HtmlSingleAttributeParser parser;
    private WhitespaceConsumerMock consumer;
    
    @Before
    public void setUp() {
        this.enclosureParser = new HtmlQuoteEnclosureParserMock(DEFAULT_QUOTE_VALUE);
        this.consumer = new WhitespaceConsumerMock();
        this.parser = new HtmlSingleAttributeParser(this.enclosureParser, this.consumer);
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_SpaceAfterAttribute_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some");
        HtmlAttribute data;
        
        this.setState(DEFAULT_DATA);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_SpaceAfterAttribute_RemainingDataIsWhitespaceAndTag() throws IOException {
        // Arrange
        String expData = MISSING_SPACE_CLOSING;
        String data;
        
        this.setState(DEFAULT_DATA);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_SpaceAfterAttribute_SingleCallOnWhitespaceConsumer() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(DEFAULT_DATA);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerNumOfCalls();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_SpaceAfterAttribute_DataWithWhitespaceAndTagPassedToWhitespaceConsumer() throws IOException {
        // Arrange
        String expData = DEFAULT_CLOSING;
        String data;
        
        this.setState(DEFAULT_DATA);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(0);
        
        // Assert
        assertEquals(expData, data); 
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_SpaceAfterAttribute_NoCallsOnEnclosureParser() throws IOException {
        // Arrange
        int expData = 0;
        int data;
        
        this.setState(DEFAULT_DATA);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getEnclosureNumOfCalls();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_isFlag_NoSpaceAfterAttribute_ReturnedAttributeMatces() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some");
        HtmlAttribute data;
        
        this.setState(DATA_NO_SPACE);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_NoSpaceAfterAttribute_RemainingDataIsTag() throws IOException {
        // Arrange
        String expData = MISSING_SPACE_CLOSING;
        String data;
        
        this.setState(DATA_NO_SPACE);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_NoSpaceAfterAttribute_SingleCallOnWhitespaceConsumer() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(DATA_NO_SPACE);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerNumOfCalls();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_NoSpaceAfterAttribute_DataWithTagPassedToWhitespaceConsumer() throws IOException {
        // Arrange
        String expData = MISSING_SPACE_CLOSING;
        String data;
        
        this.setState(DATA_NO_SPACE);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_NoSpaceAfterAttribute_NoCallsOnEnclosureParser() throws IOException {
        // Arrange
        int expData = 0;
        int data;
        
        this.setState(DATA_NO_SPACE);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getEnclosureNumOfCalls();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValue_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some", "value");
        HtmlAttribute data;
        
        this.setState(DATA_WITH_VAL);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValue_RemainingDataIsWhitespaceAndTag() throws IOException {
        // Arrange
        String expData = DEFAULT_CLOSING;
        String data;
        
        this.setState(DATA_WITH_VAL);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValue_TwoCallsOnWhitespaceConsumer() throws IOException {
        // Arrange
        int expData = 2;
        int data;
        
        this.setState(DATA_WITH_VAL);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerNumOfCalls();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValue_FirstCallOnWhitespaceConsumerIsSeparator() throws IOException {
        // Arrange
        String expData = SEPARATOR + DATA_WITH_VAL.split(SEPARATOR)[1];
        String data;
        
        this.setState(DATA_WITH_VAL);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValue_SecondCallOnWhitespaceConsumerIsAfterSeparator() throws IOException {
        // Arrange
        String expData = DATA_WITH_VAL.split(SEPARATOR)[1];
        String data;
        
        this.setState(DATA_WITH_VAL);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(1);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValue_NoCallsOnEnclosureParser() throws IOException {
        // Arrange
        int expData = 0;
        int data;
        
        this.setState(DATA_WITH_VAL);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getEnclosureNumOfCalls();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValueInDoubleQuote_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some", DEFAULT_QUOTE_VALUE);
        HtmlAttribute data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValueInDoubleQuote_RemainingDataIsWhitespaceAndTag() throws IOException {
        // Arrange
        String expData = DEFAULT_CLOSING;
        String data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValueInDoubleQuote_TwoCallsOnWhitespaceConsumer() throws IOException {
        // Arrange
        int expData = 2;
        int data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerNumOfCalls();
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValueInDoubleQuote_FirstCallOnWhitespaceConsumerIsBeforeSeparator() throws IOException {
        // Arrange
        String expData = SEPARATOR + DATA_WITH_DOUBLE_QUOTE.split(SEPARATOR)[1];
        String data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(0);
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValueInDoubleQuote_SecondCallOnWhitespaceConsumerIsAfterSeparator() throws IOException {
        // Arrange
        String expData = DATA_WITH_DOUBLE_QUOTE.split(SEPARATOR)[1];
        String data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(1);
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValueInDoubleQuote_SingleCallOnEnclosureParser() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getEnclosureNumOfCalls();
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValueInDoubleQuote_FirstCallOnEnclosureParserIsAfterSeparatorData() throws IOException {
        // Arrange
        String expData = DATA_WITH_DOUBLE_QUOTE.split(SEPARATOR)[1];
        String data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getEnclosureCallData(0);
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValueInSingleQuote_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some", DEFAULT_QUOTE_VALUE);
        HtmlAttribute data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE);
        
        // Apply
        data = this.parser.parse(this.input);
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValueInSingleQuote_RemainingDataIsWhitespaceAndTag() throws IOException {
        // Arrange
        String expData = DEFAULT_CLOSING;
        String data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValueInSingleQuote_TwoCallsOnWhitespaceSeparator() throws IOException {
        // Arrange
        int expData = 2;
        int data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerNumOfCalls();
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValueInSingleQuote_FirstCallOnWhitespaceConsumerIsBeforeSeparator() throws IOException {
        // Arrange
        String expData = SEPARATOR + DATA_WITH_SINGLE_QUOTE.split(SEPARATOR)[1];
        String data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(0);
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValueInSingleQuote_SecondCallOnWhitespaceConsumerIsAfterSeparator() throws IOException {
        // Arrange
        String expData = DATA_WITH_SINGLE_QUOTE.split(SEPARATOR)[1];
        String data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(1);
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValueInSingleQuote_SingleCallOnEnclosureParser() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getEnclosureNumOfCalls();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValueIsSingleQuote_FirstEnclosureParserDataIsAfterSeparator() throws IOException {
        // Arrange
        String expData = DATA_WITH_SINGLE_QUOTE.split(SEPARATOR)[1];
        String data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getEnclosureCallData(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_IsFlag_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some");
        HtmlAttribute data;
        
        this.setState(DEFAULT_DATA_WITH_MULTI_ATTRS);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_IsFlag_RemainingDataIsWhitespaceAndTag() throws IOException {
        // Arrange
        String expData = DEFAULT_REMAINING_ATTRS.substring(1);
        String data;
        
        this.setState(DEFAULT_DATA_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_IsFlag_SingleCallOnWhitespaceConsumer() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(DEFAULT_DATA_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerNumOfCalls();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_IsFlag_DataWithWhitespaceAndTagPassedToWhitespaceConsumer() throws IOException {
        // Arrange
        String expData = DEFAULT_REMAINING_ATTRS;
        String data;
        
        this.setState(DEFAULT_DATA_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(0);
        
        // Assert
        assertEquals(expData, data); 
    }
    
    @Test
    public void testParse_MultiAttribute_IsFlag_NoCallsOnEnclosureParser() throws IOException {
        // Arrange
        int expData = 0;
        int data;
        
        this.setState(DEFAULT_DATA_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getEnclosureNumOfCalls();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValue_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some", "value");
        HtmlAttribute data;
        
        this.setState(DATA_WITH_VAL);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValue_RemainingDataIsWhitespaceAndTag() throws IOException {
        // Arrange
        String expData = DEFAULT_REMAINING_ATTRS;
        String data;
        
        this.setState(DATA_WITH_VAL_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValue_TwoCallsOnWhitespaceConsumer() throws IOException {
        // Arrange
        int expData = 2;
        int data;
        
        this.setState(DATA_WITH_VAL_WITH_MULTI_ATTRS);
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerNumOfCalls();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValue_FirstCallOnWhitespaceConsumerIsSeparator() throws IOException {
        // Arrange
        String expData = SEPARATOR + DATA_WITH_VAL_WITH_MULTI_ATTRS.split(SEPARATOR, 2)[1];
        String data;
        
        this.setState(DATA_WITH_VAL_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValue_SecondCallOnWhitespaceConsumerIsAfterSeparator() throws IOException {
        // Arrange
        String expData = DATA_WITH_VAL_WITH_MULTI_ATTRS.split(SEPARATOR, 2)[1];
        String data;
        
        this.setState(DATA_WITH_VAL_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(1);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValue_NoCallsOnEnclosureParser() throws IOException {
        // Arrange
        int expData = 0;
        int data;
        
        this.setState(DATA_WITH_VAL_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getEnclosureNumOfCalls();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValueInDoubleQuote_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some", DEFAULT_QUOTE_VALUE);
        HtmlAttribute data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE_WITH_MULTI_ATTRS);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValueInDoubleQuote_RemainingDataIsWhitespaceAndTag() throws IOException {
        // Arrange
        String expData = DEFAULT_REMAINING_ATTRS;
        String data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValueInDoubleQuote_TwoCallsOnWhitespaceConsumer() throws IOException {
        // Arrange
        int expData = 2;
        int data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerNumOfCalls();
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValueInDoubleQuote_FirstCallOnWhitespaceConsumerIsBeforeSeparator() throws IOException {
        // Arrange
        String expData = SEPARATOR + DATA_WITH_DOUBLE_QUOTE_WITH_MULTI_ATTRS.split(SEPARATOR, 2)[1];
        String data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(0);
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValueInDoubleQuote_SecondCallOnWhitespaceConsumerIsAfterSeparator() throws IOException {
        // Arrange
        String expData = DATA_WITH_DOUBLE_QUOTE_WITH_MULTI_ATTRS.split(SEPARATOR, 2)[1];
        String data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(1);
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValueInDoubleQuote_SingleCallOnEnclosureParser() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getEnclosureNumOfCalls();
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValueInDoubleQuote_FirstCallOnEnclosureParserIsAfterSeparatorData() throws IOException {
        // Arrange
        String expData = DATA_WITH_DOUBLE_QUOTE_WITH_MULTI_ATTRS.split(SEPARATOR, 2)[1];
        String data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getEnclosureCallData(0);
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValueInSingleQuote_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some", DEFAULT_QUOTE_VALUE);
        HtmlAttribute data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE_WITH_MULTI_ATTRS);
        
        // Apply
        data = this.parser.parse(this.input);
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValueInSingleQuote_RemainingDataIsWhitespaceAndTag() throws IOException {
        // Arrange
        String expData = DEFAULT_REMAINING_ATTRS;
        String data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValueInSingleQuote_TwoCallsOnWhitespaceSeparator() throws IOException {
        // Arrange
        int expData = 2;
        int data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerNumOfCalls();
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValueInSingleQuote_FirstCallOnWhitespaceConsumerIsBeforeSeparator() throws IOException {
        // Arrange
        String expData = SEPARATOR + DATA_WITH_SINGLE_QUOTE_WITH_MULTI_ATTRS.split(SEPARATOR, 2)[1];
        String data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(0);
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValueInSingleQuote_SecondCallOnWhitespaceConsumerIsAfterSeparator() throws IOException {
        // Arrange
        String expData = DATA_WITH_SINGLE_QUOTE_WITH_MULTI_ATTRS.split(SEPARATOR, 2)[1];
        String data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(1);
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValueInSingleQuote_SingleCallOnEnclosureParser() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getEnclosureNumOfCalls();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValueIsSingleQuote_FirstEnclosureParserDataIsAfterSeparator() throws IOException {
        // Arrange
        String expData = DATA_WITH_SINGLE_QUOTE_WITH_MULTI_ATTRS.split(SEPARATOR, 2)[1];
        String data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE_WITH_MULTI_ATTRS);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getEnclosureCallData(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_MissingCharExceptionDuringWhitespaceParsing_SingleErrorThrown_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some");
        HtmlAttribute data;
        
        this.setState(DEFAULT_DATA);
        
        expData.addError(MISSING_CHAR_ERROR);
        this.consumer.setError(MISSING_CHAR_ERROR);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_MissingCharExceptionDuringWhitspaceParsing_RemainingDataIsWhitespaceAndClosingTag() throws IOException {
        // Arrange
        String expData = DEFAULT_CLOSING;
        String data;
        
        this.setState(DEFAULT_CLOSING);
        
        this.consumer.setError(MISSING_CHAR_ERROR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_IsFlag_MissingCharExceptionDuringWhitespaceParsing_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_REMAINING_ATTRS;
        String data;
        
        this.setState(DEFAULT_DATA_WITH_MULTI_ATTRS);
        
        this.consumer.setError(MISSING_CHAR_ERROR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValue_MissingCharExceptionDuringWhitespaceParsing_TwoErrorsThrown_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some", "value");
        HtmlAttribute data;
        
        this.setState(DATA_WITH_VAL);
        
        expData.addError(MISSING_CHAR_ERROR);
        expData.addError(MISSING_CHAR_ERROR);
        
        this.consumer.setError(MISSING_CHAR_ERROR);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValue_MissingCharExceptionDuringWhitespaceParsing_RemainingDataIsWhitespaceAndClosingTag() throws IOException {
        // Arrange
        String expData = DEFAULT_CLOSING;
        String data;
        
        this.setState(DATA_WITH_VAL);
        
        this.consumer.setError(MISSING_CHAR_ERROR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValue_MissingCharExceptionDuringWhitespaceParsing_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_REMAINING_ATTRS;
        String data;
        
        this.setState(DATA_WITH_VAL_WITH_MULTI_ATTRS);
        
        this.consumer.setError(MISSING_CHAR_ERROR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_UnexpectedCharExceptionDuringWhitespaceParsing_SingleErrorThrown_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some");
        HtmlAttribute data;
        
        this.setState(DEFAULT_DATA);
        
        expData.addError(UNEXPECTED_CHAR_ERROR);
        this.consumer.setError(UNEXPECTED_CHAR_ERROR);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_UnexpectedCharExceptionDuringWhitspaceParsing_RemainingDataIsWhitespaceAndClosingTag() throws IOException {
        // Arrange
        String expData = DEFAULT_CLOSING;
        String data;
        
        this.setState(DEFAULT_CLOSING);
        
        this.consumer.setError(UNEXPECTED_CHAR_ERROR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_IsFlag_UnexpectedCharExceptionDuringWhitespaceParsing_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_REMAINING_ATTRS;
        String data;
        
        this.setState(DEFAULT_DATA_WITH_MULTI_ATTRS);
        
        this.consumer.setError(UNEXPECTED_CHAR_ERROR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValue_UnexpectedCharExceptionDuringWhitespaceParsing_TwoErrorsThrown_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some", "value");
        HtmlAttribute data;
        
        this.setState(DATA_WITH_VAL);
        
        expData.addError(UNEXPECTED_CHAR_ERROR);
        expData.addError(UNEXPECTED_CHAR_ERROR);
        
        this.consumer.setError(UNEXPECTED_CHAR_ERROR);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValue_UnexpectedCharExceptionDuringWhitespaceParsing_RemainingDataIsWhitespaceAndClosingTag() throws IOException {
        // Arrange
        String expData = DEFAULT_CLOSING;
        String data;
        
        this.setState(DATA_WITH_VAL);
        
        this.consumer.setError(UNEXPECTED_CHAR_ERROR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasValue_UnexpectedCharExceptionDuringWhitespaceParsing_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_REMAINING_ATTRS;
        String data;
        
        this.setState(DATA_WITH_VAL_WITH_MULTI_ATTRS);
        
        this.consumer.setError(UNEXPECTED_CHAR_ERROR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasDoubleQuotedValue_MissingCharExceptionDuringParsingQuote_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some", MISSING_CHAR_ERROR.getData());
        HtmlAttribute data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE);
        
        expData.addError(MISSING_CHAR_ERROR);
        this.enclosureParser.setError(MISSING_CHAR_ERROR);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasDoubleQuotedValue_MissingCharExceptionDuringParsingQuote_RemainingDataIsWhitespaceAndClosingTag() throws IOException {
        // Arrange
        String expData = DEFAULT_CLOSING;
        String data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE);
        
        this.enclosureParser.setError(MISSING_CHAR_ERROR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasDoubleQuotedValue_MissingCharExceptionDuringParsingQuote_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_REMAINING_ATTRS;
        String data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE_WITH_MULTI_ATTRS);
        
        this.enclosureParser.setError(MISSING_CHAR_ERROR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasDoubleQuotedValue_UnexpectedCharExceptionDuringParsingQuote_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some", UNEXPECTED_CHAR_ERROR.getData());
        HtmlAttribute data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE);
        
        expData.addError(UNEXPECTED_CHAR_ERROR);
        this.enclosureParser.setError(UNEXPECTED_CHAR_ERROR);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasDoubleQuotedValue_UnexpectedCharExceptionDuringParsingQuote_RemainingDataIsWhitespaceAndClosingTag() throws IOException {
        // Arrange
        String expData = DEFAULT_CLOSING;
        String data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE);
        
        this.enclosureParser.setError(UNEXPECTED_CHAR_ERROR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasDoubleQuotedValue_UnexpectedCharExceptionDuringParsingQuote_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_REMAINING_ATTRS;
        String data;
        
        this.setState(DATA_WITH_DOUBLE_QUOTE_WITH_MULTI_ATTRS);
        
        this.enclosureParser.setError(UNEXPECTED_CHAR_ERROR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasSingleQuotedValue_MissingCharExceptionDuringParsingQuote_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some", MISSING_CHAR_ERROR.getData());
        HtmlAttribute data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE);
        
        expData.addError(MISSING_CHAR_ERROR);
        this.enclosureParser.setError(MISSING_CHAR_ERROR);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasSingleQuotedValue_MissingCharExceptionDuringParsingQuote_RemainingDataIsWhitespaceAndClosingTag() throws IOException {
        // Arrange
        String expData = DEFAULT_CLOSING;
        String data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE);
        
        this.enclosureParser.setError(MISSING_CHAR_ERROR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasSingleQuotedValue_MissingCharExceptionDuringParsingQuote_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_REMAINING_ATTRS;
        String data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE_WITH_MULTI_ATTRS);
        
        this.enclosureParser.setError(MISSING_CHAR_ERROR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasSingleQuotedValue_UnexpectedCharExceptionDuringParsingQuote_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some", UNEXPECTED_CHAR_ERROR.getData());
        HtmlAttribute data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE);
        
        expData.addError(UNEXPECTED_CHAR_ERROR);
        this.enclosureParser.setError(UNEXPECTED_CHAR_ERROR);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasSingleQuotedValue_UnexpectedCharExceptionDuringParsingQuote_RemainingDataIsWhitespaceAndClosingTag() throws IOException {
        // Arrange
        String expData = DEFAULT_CLOSING;
        String data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE);
        
        this.enclosureParser.setError(UNEXPECTED_CHAR_ERROR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttribute_HasSingleQuotedValue_UnexpectedCharExceptionDuringParsingQuote_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_REMAINING_ATTRS;
        String data;
        
        this.setState(DATA_WITH_SINGLE_QUOTE_WITH_MULTI_ATTRS);
        
        this.enclosureParser.setError(UNEXPECTED_CHAR_ERROR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    private void setState(String data) {
        LinkedList<Character> inputData = this.generateData(data);
        
        this.input = new PushbackAndPositionReaderMock(inputData);
    }
    
    private LinkedList<Character> generateData(String data) {
        LinkedList<Character> result = new LinkedList<>();
        
        for (int i = 0; i < data.length(); i++)
            result.add(data.charAt(i));
        
        return result;
    }
    
    private int getConsumerNumOfCalls() {
        return this.consumer.getReceivedData().size();
    }
    
    private String getConsumerCallData(int n) {
        return this.consumer.getReceivedData().get(n);
    }
    
    private int getEnclosureNumOfCalls() {
        return this.enclosureParser.getReceivedData().size();
    }
    
    private String getEnclosureCallData(int n) {
        return this.enclosureParser.getReceivedData().get(n);
    }
    
    @After
    public void clearState() {
        this.enclosureParser = null;
        this.consumer = null;
        this.parser = null;
        this.input = null;
    }
}