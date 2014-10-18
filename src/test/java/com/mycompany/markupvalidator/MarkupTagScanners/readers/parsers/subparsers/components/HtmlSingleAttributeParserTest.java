/*  This file is part of MarkupValidator.
 *
 *  MarkupValidator is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MarkupValidator is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MarkupValidator. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.subparsers.components;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.*;
import java.util.LinkedList;

import com.mycompany.markupvalidator.MarkupTagScanners.errors.*;
import org.junit.*;

import com.mycompany.markupvalidator.MarkupTagScanners.enums.MarkupTag;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlAttribute;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReaderMock;

public class HtmlSingleAttributeParserTest {
    private final static String CLOSING_TAG = "" + MarkupTag.CLOSING_TAG;
    private final static String CLOSING_ATTR = "" + MarkupTag.CLOSING_ATTRIBUTE;
    private final static String SEPARATOR = "" + MarkupTag.ATTRIBUTE_VALUE_SEPARATOR;
    
    private static final String DEFAULT_CLOSING = " " + CLOSING_TAG;
    private static final String MISSING_SPACE_CLOSING = CLOSING_TAG;
    private static final String CLOSING_WITH_ATTR = CLOSING_ATTR + " " + CLOSING_TAG;
    private static final String MISSING_SPACE_CLOSING_ATTR = CLOSING_ATTR + CLOSING_TAG;
    
    private static final String DEFAULT_DATA = "some" + DEFAULT_CLOSING;
    private static final String DATA_NO_SPACE = "some" + MISSING_SPACE_CLOSING;
    private static final String DATA_WITH_CLOSING_ATTR = "some" + CLOSING_WITH_ATTR;
    
    private static final String DATA_WITH_VAL = "some" + SEPARATOR + "value" + DEFAULT_CLOSING;
    private static final String DATA_WITH_VAL_AND_CLOSING_ATTR = "some" + SEPARATOR + "value" + CLOSING_WITH_ATTR;
    
    private static final String DATA_WITH_DOUBLE_QUOTE = "some" + SEPARATOR + "\"" + DEFAULT_CLOSING ;
    private static final String DATA_WITH_SINGLE_QUOTE = "some" + SEPARATOR + "'" +  DEFAULT_CLOSING;
    
    private static final String DEFAULT_REMAINING_ATTRS = " other" + SEPARATOR + "data" + DEFAULT_CLOSING;
    
    private static final String DEFAULT_DATA_WITH_MULTI_ATTRS = "some" + DEFAULT_REMAINING_ATTRS;
    private static final String DATA_WITH_VAL_WITH_MULTI_ATTRS = "some" + SEPARATOR + "value" + DEFAULT_REMAINING_ATTRS;
    private static final String DATA_WITH_DOUBLE_QUOTE_WITH_MULTI_ATTRS = "some" + SEPARATOR + "\"" + DEFAULT_REMAINING_ATTRS;
    private static final String DATA_WITH_SINGLE_QUOTE_WITH_MULTI_ATTRS = "some" + SEPARATOR + "'" + DEFAULT_REMAINING_ATTRS;
     
    private static final String DEFAULT_QUOTE_VALUE = "some quote value";
    
    private static final ComponentError MISSING_CHAR_ERROR = new MissingCharacterComponentError(' ', new Point(0, 0), "some data");
    private static final ComponentError UNEXPECTED_CHAR_ERROR = new UnexpectedCharacterComponentError(' ', new Point(0, 0), "some data");
    
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
    public void testParse_OnlyClosingAttr_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute(CLOSING_ATTR);
        HtmlAttribute data;
        
        this.setState(MISSING_SPACE_CLOSING_ATTR);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OnlyClosingAttr_RemainingDataIsTag() throws IOException {
        // Arrange
        String expData = MISSING_SPACE_CLOSING;
        String data;
        
        this.setState(MISSING_SPACE_CLOSING_ATTR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OnlyClosingAttr_SingleCallOnWhitespaceConsumer() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(MISSING_SPACE_CLOSING_ATTR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerNumOfCalls();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OnlyClosingAttr_DataWithOnlyTagPassedToWhitespaceConsumer() throws IOException {
        // Arrange
        String expData = MISSING_SPACE_CLOSING;
        String data;
        
        this.setState(MISSING_SPACE_CLOSING_ATTR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OnlyClosingAttr_NoCallsOnEnclosureParser() throws IOException {
        // Arrange
        int expData = 0;
        int data;
        
        this.setState(MISSING_SPACE_CLOSING_ATTR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getEnclosureNumOfCalls();
        
        // Assert
        assertEquals(expData, data);
    }

    @Test
    public void testParse_OnlyClosingAttr_HasSpaceAfterClosingAttr_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute(CLOSING_ATTR);
        HtmlAttribute data;
        
        this.setState(CLOSING_WITH_ATTR);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OnlyClosingAttr_HasSpaceAfterClosingAttr_RemainingDataIsTag() throws IOException {
        // Arrange
        String expData = MISSING_SPACE_CLOSING;
        String data;
        
        this.setState(CLOSING_WITH_ATTR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OnlyClosingAttr_HasSpaceAfterClosingAttr_SingleCallOnWhitespaceConsumer() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(CLOSING_WITH_ATTR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerNumOfCalls();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OnlyClosingAttr_HasSpaceAfterClosingAttr_DataWithOnlyWhitespaceAndTagPassedToWhitespaceConsumer() throws IOException {
        // Arrange
        String expData = DEFAULT_CLOSING;
        String data;
        
        this.setState(CLOSING_WITH_ATTR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OnlyClosingAttr_HasSpaceAfterClosingAttr_NoCallsOnEnclosureParser() throws IOException {
        // Arrange
        int expData = 0;
        int data;
        
        this.setState(CLOSING_WITH_ATTR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getEnclosureNumOfCalls();
        
        // Assert
        assertEquals(expData, data);
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
    public void testParse_SingleAttribute_IsFlag_SpaceAfterAttribute_RemainingDataIsTag() throws IOException {
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
    public void testParse_SingleAttribute_IsFlag_FollowedImmediateByClosingAttr_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some");
        HtmlAttribute data;
        
        this.setState(DATA_WITH_CLOSING_ATTR);
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_FollowedImmediatelyByClosingAttr_RemainingDataContainsClosingAttrAndTag() throws IOException {
        // Arrange
        String expData = CLOSING_WITH_ATTR;
        String data;
        
        this.setState(DATA_WITH_CLOSING_ATTR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_FollowedImmediatelyByClosingAttr_SingleCallOnWhitespaceConsumer() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(DATA_WITH_CLOSING_ATTR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerNumOfCalls();
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_FollowedImmediatelyByClosingAttr_DataWithClosingAttrAndTagPassedToWhitespaceConsumer() throws IOException {
        // Arrange
        String expData = CLOSING_WITH_ATTR;
        String data;
        
        this.setState(DATA_WITH_CLOSING_ATTR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_IsFlag_FollowedImmediatelyByClosingAttr_NoCallsOnEnclosureParser() throws IOException {
        // Arrange
        int expData = 0;
        int data;
        
        this.setState(DATA_WITH_CLOSING_ATTR);
        
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
    public void testParse_SingleAttribute_HasValue_FollowedImmediateByClosingAttr_ReturnedAttributeMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("some", "value");
        HtmlAttribute data;
        
        this.setState(DATA_WITH_VAL_AND_CLOSING_ATTR);
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValue_FollowedImmediatelyByClosingAttr_RemainingDataContainsClosingAttrAndTag() throws IOException {
        // Arrange
        String expData = CLOSING_WITH_ATTR;
        String data;
        
        this.setState(DATA_WITH_VAL_AND_CLOSING_ATTR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValue_FollowedImmediatelyByClosingAttr_TwoCallsOnWhitespaceConsumer() throws IOException {
        // Arrange
        int expData = 2;
        int data;
        
        this.setState(DATA_WITH_VAL_AND_CLOSING_ATTR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerNumOfCalls();
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValue_FollowedImmediatelyByClosingAttr_FirstCallOnWhitespaceIsSeparator() throws IOException {
        // Arrange
        String expData = SEPARATOR + DATA_WITH_VAL_AND_CLOSING_ATTR.split(SEPARATOR)[1];
        String data;
        
        this.setState(DATA_WITH_VAL_AND_CLOSING_ATTR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValue_FollowedImmediatelyByClosingAttr_SecondCallOnWhitespaceIsAfterSeparator() throws IOException {
        // Arrange
        String expData = DATA_WITH_VAL_AND_CLOSING_ATTR.split(SEPARATOR)[1];
        String data;
        
        this.setState(DATA_WITH_VAL_AND_CLOSING_ATTR);
        
        // Apply
        this.parser.parse(this.input);
        data = this.getConsumerCallData(1);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_HasValue_FollowedImmediatelyByClosingAttr_NoCallsOnEnclosureParser() throws IOException {
        // Arrange
        int expData = 0;
        int data;
        
        this.setState(DATA_WITH_VAL_AND_CLOSING_ATTR);
        
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
    
    @Test(expected=EndOfInputAttributeError.class)
    public void testParse_InvalidInput_NoName_NoValue_NoSpace_EOFImmediately() throws IOException {
        // Arrange
        this.setState("");
        // Apply + Assert
        this.parser.parse(this.input);
    }
    
    @Test
    public void testParse_InvalidInput_NoName_NoValue_NoSpace_EOFImmediately_StoredExceptionDataMatchesExpected() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("");
        HtmlAttribute data = null;
        
        this.setState("");
        
        // Apply
        try {
            this.parser.parse(this.input);
        } catch (EndOfInputAttributeError e) {
            data = e.getAttribute();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_InvalidInput_NoName_NoValue_NoSpace_EOFImmediately_RemainingDataMatchesExpected() throws IOException {
        // Arrange
        String expData = "";
        String data = null;
        
        this.setState("");
        
        // Apply
        try {
            this.parser.parse(this.input);
        } catch (EndOfInputAttributeError e) {
            data = this.input.getRemainingData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EndOfInputAttributeError.class)
    public void testParse_InvalidInput_WithName_NoValue_NoSpace_EOFAfterName() throws IOException {
        // Arrange
        this.setState("someName");
        
        // Apply + Assert
        this.parser.parse(this.input);
    }
    
    @Test
    public void testParse_InvalidInput_WithName_NoValue_NoSpace_EOFAfterName_StoredExceptionDataMatchesExpected() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("someName");
        HtmlAttribute data = null;
        
        this.setState("someName");
        
        // Apply
        try {
            this.parser.parse(this.input);
        } catch (EndOfInputAttributeError e) {
            data = e.getAttribute();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testparse_InvalidInput_WithName_NoValue_NoSpace_EOFAfterName_RemainingDataMatchesExpected() throws IOException {
        // Arrange
        String expData = "";
        String data = null;
        
        this.setState("someName");
        
        // Apply
        try {
            this.parser.parse(this.input);
        } catch (EndOfInputAttributeError e) {
            data = this.input.getRemainingData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EndOfInputAttributeError.class)
    public void testParse_InvalidInput_WithName_NoValue_WithSpace_EOFAfterSpaceAfterName() throws IOException {
        // Arrange
        this.setState("someName  ");
        this.consumer.setError(new EndOfInputComponentError(""));
        
        // Apply + Assert
        this.parser.parse(this.input);
    }
    
    @Test
    public void testParse_InvalidInput_WithName_NoValue_WithSpace_EOFAfterSpaceAfterName_StoredExceptionDataMatchesExpected() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("someName");
        HtmlAttribute data = null;
        
        this.setState("someName    ");
        this.consumer.setError(new EndOfInputComponentError(""));
        
        // Apply
        try {
            this.parser.parse(this.input);
        } catch (EndOfInputAttributeError e) {
            data = e.getAttribute();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EndOfInputAttributeError.class)
    public void testParse_InvalidInput_WithName_WithValueSeparator_NoValue_EOFAfterValueSeparator() throws IOException {
        // Arrange
        this.setState("someName" + MarkupTag.ATTRIBUTE_VALUE_SEPARATOR);
        
        // Apply + Assert
        this.parser.parse(this.input);
    }
    
    @Test
    public void testParse_InvalidInput_WithName_WithValueSeparator_NoValue_EOFAfterValueSeparator_StoredExceptionDataMatchesExpected() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("someName");
        HtmlAttribute data = null;
        
        this.setState("someName" + MarkupTag.ATTRIBUTE_VALUE_SEPARATOR);
        
        // Apply
        try {
            this.parser.parse(this.input);
        } catch (EndOfInputAttributeError e) {
            data = e.getAttribute();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_InvalidInput_WithName_WithValueSeparator_NoValue_EOFAfterValueSeparator_RemainingDataMatchesExpected() throws IOException {
        // Arrange
        String expData = "";
        String data = null;
        
        this.setState("someName" + MarkupTag.ATTRIBUTE_VALUE_SEPARATOR);
        
        // Apply
        try {
            this.parser.parse(this.input);
        } catch (EndOfInputAttributeError e) {
            data = this.input.getRemainingData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EndOfInputAttributeError.class)
    public void testParse_InvalidInput_WithName_WithValueSeparator_WithValue_EOFAfterValue() throws IOException {
        // Arrange
        this.setState("someName=SomeValue");
        
        // Apply + Assert
        this.parser.parse(this.input);
    }
    
    @Test
    public void testParse_InvalidInput_WithName_WithValueSeparator_WithValue_EOFAfterValueSeparator_StoredExceptionDataMatchesExpected() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("someName", "someValue");
        HtmlAttribute data = null;
        
        this.setState("someName=someValue");
        
        // Apply
        try {
            this.parser.parse(this.input);
        } catch (EndOfInputAttributeError e) {
            data = e.getAttribute();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_InvalidInput_WithName_WithValueSeparator_WithValue_EOFAfterValueSeparator_RemainingDataMatchesExpected() throws IOException {
        // Arrange
        String expData = "";
        String data = null;
        
        this.setState("someName=someValue");
        
        // Apply
        try {
            this.parser.parse(this.input);
        } catch (EndOfInputAttributeError e) {
            data = this.input.getRemainingData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EndOfInputAttributeError.class)
    public void testParse_InvalidInput_WithName_WithValueSeparator_WithValueInEnclosure_EOFInsideEnclosure() throws IOException {
        // Arrange
        this.setState("someName=\"");
        ComponentError err = new EndOfInputComponentError("\"someValue");
        this.enclosureParser.setError(err);
        
        // Apply + Assert
        this.parser.parse(input);
    }
    
    @Test
    public void testParse_InvalidInput_WithName_WithValueSeparator_WithValueInEnclosure_EOFInsideEnclosure_StoredExceptionDataMatchesExpected() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute("someName", "\"someValue");
        HtmlAttribute data = null;
        
        this.setState("someName=\"");
        
        ComponentError err = new EndOfInputComponentError("\"someValue");
        this.enclosureParser.setError(err);
        
        // Apply
        try {
            this.parser.parse(this.input);
        } catch (EndOfInputAttributeError e) {
            data = e.getAttribute();
        }
        
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
