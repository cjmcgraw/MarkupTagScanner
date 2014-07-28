package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

import org.junit.*;

import com.mycompany.htmlvalidator.scanners.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.*;
import com.mycompany.htmlvalidator.scanners.readers.utilities.*;

public class HtmlAttributesParserTest {;
    private static final String ATTR_DATA = "someAttrData";
    private static final String QUOTE_DATA = "someQuoteData";
    
    private static final HtmlAttribute QUOTE_ATTR = new HtmlAttribute("someQuoteData");
    private static final HtmlAttribute STND_ATTR = new HtmlAttribute("someAttrData");
    
    private static final String SINGLE_ATTR = "X";
    private static final String SINGLE_QUOTE = MarkupTag.SINGLE_QUOTE.toString();
    private static final String DOUBLE_QUOTE = MarkupTag.DOUBLE_QUOTE.toString();
    
    private static final String CLOSING_TAG = MarkupTag.CLOSING_TAG.toString();
    private static final String OPENING_TAG = MarkupTag.OPENING_TAG.toString();
    
    private HtmlSingleAttributeParserMock attributeParser = new HtmlSingleAttributeParserMock(ATTR_DATA);
    private HtmlQuoteEnclosureParserMock enclosureParser = new HtmlQuoteEnclosureParserMock(QUOTE_DATA);
    private WhitespaceConsumerMock whitespaceConsumer = new WhitespaceConsumerMock();
    
    
    private PushbackAndPositionReaderMock input;
    private HtmlAttributesParser parser;
    private MutableHtmlData result;
    
    /* Potential exceptions:
     * 
     *  1. 
     *      public method:
     *          parse
     *      
     *      condition:
     *          null EnclosureParser
     *      
     *      exception:
     *          InvalidStateException
     *          
     *  2. 
     *      public method:
     *          parse
     *      
     *      condition:
     *          null attributeParser
     *      
     *      exception:
     *          InvalidStateException
     *           
     *  3. 
     *      public method:
     *          parse
     *      
     *      condition:
     *          null whitespaceConsumer
     *      
     *      exception:
     *          InvalidStateException
     *  4.
     *      public method:
     *          parse
     *      
     *      condition:
     *          unclosed data (unexpected "<" in string)
     *      
     *      exception:
     *          UnclosedTagParsingException
     *  
     *  5.
     *      public method:
     *          parse
     *      
     *      condition:
     *          Unclosed input (missing ">" in string)
     *      
     *      exception:
     *          EndOfInputParsingException
     *          
     */
    
    /* Potential component exceptions:
     * 
     *  1.
     *      public method:
     *          parse
     *          
     *      component:
     *          enclosureParser
     *      
     *      exception:
     *          EOFException
     *          
     *      expected outcome:
     *          repackage at EndOfInputParsingException
     * 
     */
    
    @Before
    public void setUp() {
        this.parser = new HtmlAttributesParser(this.enclosureParser,
                                               this.attributeParser,
                                               this.whitespaceConsumer);
        this.result = new MutableHtmlData();
    }
    
    @Test
    public void testParse_SoloAttr_SingleAttrs_SpaceBeforeClosingTag_ResultContainsAttrsAsFirstValue() throws IOException {
        // Arrange
        HtmlAttribute expData = STND_ATTR;
        HtmlAttribute data;
        
        this.setState(SINGLE_ATTR + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_SingleAttrs_SpaceBeforeClosingTag_ResultAttrssContainsOnlyOneAttr() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(SINGLE_ATTR + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().size();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_SingleAttrs_SpaceBeforeClosingTag_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = CLOSING_TAG;
        String data;
        
        this.setState(SINGLE_ATTR + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_SingleAttrs_NoSpaceBeforeClosingTag_ResultContainsAttrsAsFirstValue() throws IOException {
        // Arrange
        HtmlAttribute expData = STND_ATTR;
        HtmlAttribute data;
        
        this.setState(SINGLE_ATTR + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_SingleAttrs_NoSpaceBeforeClosingTag_ResultAttrssContainsOnlyOneAttr() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(SINGLE_ATTR + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().size();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_SingleAttrs_NoSpaceBeforeClosingTag_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = CLOSING_TAG;
        String data;
        
        this.setState(SINGLE_QUOTE + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_SingleQuoteAttr_SpaceBeforeClosingTag_ResultContainsAttrsAsFirstValue() throws IOException {
        // Arrange
        HtmlAttribute expData = QUOTE_ATTR;
        HtmlAttribute data;
        
        this.setState(SINGLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_SingleQuoteAttr_SpaceBeforeClosingTag_ResultAttrssContainsOnlyOneAttr() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(SINGLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().size();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_SingleQuoteAttr_SpaceBeforeClosingTag_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = CLOSING_TAG;
        String data;
        
        this.setState(SINGLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_SingleQuoteAttr_NoSpaceBeforeClosingTag_ResultContainsAttrsAsFirstValue() throws IOException {
        // Arrange
        HtmlAttribute expData = QUOTE_ATTR;
        HtmlAttribute data;
        
        this.setState(SINGLE_QUOTE + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_SingleQuoteAttr_NoSpaceBeforeClosingTag_ResultAttrssContainsOnlyOneAttr() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(SINGLE_QUOTE + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().size();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_SingleQuoteAttr_NoSpaceBeforeClosingTag_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = CLOSING_TAG;
        String data;
        
        this.setState(SINGLE_QUOTE + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_DoubleQuoteAttr_SpaceBeforeClosingTag_ResultContainsAttrsAsFirstValue() throws IOException {
        // Arrange
        HtmlAttribute expData = QUOTE_ATTR;
        HtmlAttribute data;
        
        this.setState(DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_DoubleQuoteAttr_SpaceBeforeClosingTag_ResultAttrssContainsOnlyOneAttr() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().size();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_DoubleQuoteAttr_SpaceBeforeClosingTag_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = CLOSING_TAG;
        String data;
        
        this.setState(DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_DoubleQuoteAttr_NoSpaceBeforeClosingTag_ResultContainsAttrsAsFirstValue() throws IOException {
        // Arrange
        HtmlAttribute expData = QUOTE_ATTR;
        HtmlAttribute data;
        
        this.setState(DOUBLE_QUOTE + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_DoubleQuoteAttr_NoSpaceBeforeClosingTag_ResultAttrssContainsOnlyOneAttr() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(DOUBLE_QUOTE + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().size();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SoloAttr_DoubleQuoteAttr_NoSpaceBeforeClosingTag_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = CLOSING_TAG;
        String data;
        
        this.setState(DOUBLE_QUOTE + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_TwoSingleAttrs_ResultContainsExpectedAttrsAsFirstAttr() throws IOException {
        // Arrange
        HtmlAttribute expData = STND_ATTR;
        HtmlAttribute data;
        
        this.setState(SINGLE_ATTR + " " + SINGLE_ATTR + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_TwoSingleAttrs_ResultContainsExpectedAttrsAsSecondAttr() throws IOException {
        // Arrange
        HtmlAttribute expData = STND_ATTR;
        HtmlAttribute data;
        
        this.setState(SINGLE_ATTR + " " + SINGLE_ATTR + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(1);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_TwoSingleAttrs_ResultContainsOnlyTwoAttrs() throws IOException {
        // Arrange
        int expData = 2;
        int data;
        
        this.setState(SINGLE_ATTR + " " + SINGLE_ATTR + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().size();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_TwoSingleAttrs_RemainingInputMatches() throws IOException {
        // Arrange
        String expData = CLOSING_TAG;
        String data;
        
        this.setState(SINGLE_ATTR + " " + SINGLE_ATTR + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    public void testParse_PairAttr_OneSingleOneQuote_ResultContainsExpectedAttrsAsFirstAttr() throws IOException {
        // Arrange
        HtmlAttribute expData = STND_ATTR;
        HtmlAttribute data;
        
        this.setState(SINGLE_ATTR + " " + SINGLE_QUOTE + " " + CLOSING_TAG); 
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_OneSingleOneQuote_ResultContainsExpectedAttrsAsSecondAttr() throws IOException {
        // Arrange
        HtmlAttribute expData = QUOTE_ATTR;
        HtmlAttribute data;
        
        this.setState(SINGLE_ATTR + " " + SINGLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(1);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_OneSingleOneQuote_ResultContainsOnlyTwoAttrs() throws IOException {
        // Arrange
        int expData = 2;
        int data;
        
        this.setState(SINGLE_ATTR + " " + SINGLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().size();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_OneSingleOneQuote_RemainingInputMatches() throws IOException {
        // Arrange
        String expData = CLOSING_TAG;
        String data;
        
        this.setState(SINGLE_ATTR + " " + SINGLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    public void testParse_PairAttr_OneSingleOneDoubleQuote_ResultContainsExpectedAttrsAsFirstAttr() throws IOException {
        // Arrange
        HtmlAttribute expData = STND_ATTR;
        HtmlAttribute data;
        
        this.setState(SINGLE_ATTR + " " + DOUBLE_QUOTE + " " + CLOSING_TAG); 
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_OneSingleOneDoubleQuote_ResultContainsExpectedAttrsAsSecondAttr() throws IOException {
        // Arrange
        HtmlAttribute expData = QUOTE_ATTR;
        HtmlAttribute data;
        
        this.setState(SINGLE_ATTR + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(1);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_OneSingleOneDoubleQuote_ResultContainsOnlyTwoAttrs() throws IOException {
        // Arrange
        int expData = 2;
        int data;
        
        this.setState(SINGLE_ATTR + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().size();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_OneSingleOneDoubleQuote_RemainingInputMatches() throws IOException {
        // Arrange
        String expData = CLOSING_TAG;
        String data;
        
        this.setState(SINGLE_ATTR + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_TwoQuotes_BothDiff_ResultContainsExpectedAttrsAsFirstAttr() throws IOException {
        // Arrange
        HtmlAttribute expData = QUOTE_ATTR;
        HtmlAttribute data;
        
        this.setState(SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_TwoQuotes_BothDiff_ResultContainsExpectedAttrsAsSecondAttr() throws IOException {
        // Arrange
        HtmlAttribute expData = QUOTE_ATTR;
        HtmlAttribute data;
        
        this.setState(SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(1);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_TwoQuotes_BothDiff_ResultContainsOnlyTwoAttrs() throws IOException {
        // Arrange
        int expData = 2;
        int data;
        
        this.setState(SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().size();
        
        // Apply
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_pairAttr_TwoQuotes_BothDiff_RemainingInputMatches() throws IOException {
        // Arrange
        String expData = CLOSING_TAG;
        String data;
        
        this.setState(SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_TwoQuotes_BothSame_ResultContainsExpectedAttrsAsFirstAttr() throws IOException {
        // Arrange
        HtmlAttribute expData = QUOTE_ATTR;
        HtmlAttribute data;
        
        this.setState(DOUBLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_TwoQuotes_BothSame_ResultContainsExpectedAttrsAsSecondAttr() throws IOException {
        // Arrange
        HtmlAttribute expData = QUOTE_ATTR;
        HtmlAttribute data;
        
        this.setState(DOUBLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(1);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_TwoQuotes_BothSame_ResultContainsOnlyTwoAttrs() throws IOException {
        // Arrange
        int expData = 2;
        int data;
        
        this.setState(DOUBLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().size();
        
        // Apply
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_PairAttr_TwoQuotes_BothSame_RemainingInputMatches() throws IOException {
        // Arrange
        String expData = CLOSING_TAG;
        String data;
        
        this.setState(DOUBLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttr_ResultContainsExpectedAttrsAsFirstAttr() throws IOException {
        // Arrange
        HtmlAttribute expData = STND_ATTR;
        HtmlAttribute data;
        
        this.setState(SINGLE_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttr_ResultContainsExpectedAttrsAsSecondAttr() throws IOException {
        // Arrange
        HtmlAttribute expData = QUOTE_ATTR;
        HtmlAttribute data;
        
        this.setState(SINGLE_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(1);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttr_ResultContainsExpectedAttrsAsThirdAttr() throws IOException {
        // Arrange
        HtmlAttribute expData = QUOTE_ATTR;
        HtmlAttribute data;
        
        this.setState(SINGLE_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().get(1);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttr_ResultContainsOnlyThreeAttrs() throws IOException {
        // Arrange
        int expData = 3;
        int data;
        
        this.setState(SINGLE_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().size();
        
        // Apply
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultiAttr_RemainingInputMatches() throws IOException {
        // Arrange
        String expData = CLOSING_TAG;
        String data;
        
        this.setState(SINGLE_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    // Exception 1
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    @Test(expected=InvalidStateException.class)
    public void testParse_WithNullEnclosureParser_ThrowsExpectedException() throws IOException {
        // Arrange
        this.parser = new HtmlAttributesParser(null,
                                               this.attributeParser,
                                               this.whitespaceConsumer);
        this.setState("some data");
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    // Exception 2
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    @Test(expected=InvalidStateException.class)
    public void testParse_WithNullAttributeParser_ThrowsExpectedException() throws IOException {
        // Arrange
        this.parser = new HtmlAttributesParser(this.enclosureParser,
                                               null,
                                               this.whitespaceConsumer);
        this.setState("some data");
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    // Exception 3
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    @Test(expected=InvalidStateException.class)
    public void testParse_WithNullWhitespaceConsumer_ThrowsExpectedException() throws IOException {
        // Arrange
        this.parser = new HtmlAttributesParser(this.enclosureParser,
                                               this.attributeParser,
                                               null);
        this.setState("some data");
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    // Exception 4
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    @Test(expected=UnclosedTagParsingException.class)
    public void testParse_OpeningTag_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState(SINGLE_ATTR + " " + OPENING_TAG);
        this.parser.parse(this.input, this.result);
    }
    
    @Test
    public void testParse_OpeningTag_RemainingInputContainsParsedOpeningTag() throws IOException {
        // Arrange
        String expData = OPENING_TAG + "some other data";
        String data = "";
        
        this.setState(SINGLE_ATTR + " " + OPENING_TAG + "some other data");
        
        // Apply
        try {
            this.parser.parse(input, result);
        } catch (UnclosedTagParsingException err) {
            data = this.input.getRemainingData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OpeningTag_ThrownExceptionMatchesExpectedResult() throws IOException {
        // Arrange
        MutableHtmlData expData = new MutableHtmlData();
        HtmlData data = null;
        
        expData.updateAttributes(STND_ATTR);
        
        this.setState(SINGLE_ATTR + " " + OPENING_TAG);
        
        // Apply
        try {
            this.parser.parse(input, result);
        } catch (UnclosedTagParsingException err) {
            data = err.getHtmlData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OpeningTag_ArgResultMatchesExpectedResult() throws IOException {
        // Arrange
        MutableHtmlData expData = new MutableHtmlData();
        HtmlData data = null;
        
        expData.updateAttributes(STND_ATTR);
        
        this.setState(SINGLE_ATTR + " " + OPENING_TAG);
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (UnclosedTagParsingException err) {
            data = this.result;
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    // Exception 5
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    @Test(expected=EndOfInputParsingException.class)
    public void testParse_MissingClosingTag_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState(SINGLE_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE);
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test
    public void testParse_MissingClosingTag_RemainingInputIsEmpty() throws IOException {
        // Arrange
        int expData = 0;
        int data = -1;
        
        this.setState(SINGLE_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE);
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (EndOfInputParsingException err) {
            data = this.input.getRemainingData().length();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MissingClosingTag_ThrownExceptionMatchesExpectedResult() throws IOException {
        // Arrange
        MutableHtmlData expData = new MutableHtmlData();
        HtmlData data = null;
        
        expData.updateAttributes(STND_ATTR);
        expData.updateAttributes(QUOTE_ATTR);
        expData.updateAttributes(QUOTE_ATTR);
        
        this.setState(SINGLE_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE);
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (EndOfInputParsingException err) {
            data = err.getHtmlData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MissingClosingTag_ArgResultMatchesExpectedResult() throws IOException {
        // Arrange
        MutableHtmlData expData = new MutableHtmlData();
        HtmlData data = null;
        
        expData.updateAttributes(STND_ATTR);
        expData.updateAttributes(QUOTE_ATTR);
        expData.updateAttributes(QUOTE_ATTR);
        
        this.setState(SINGLE_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE);
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (EndOfInputParsingException err) {
            data = this.result;
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    // Component Exception 5
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    
    //@Test(expected=EndOfInputParsingException.class)
    public void testParse_EnclosureParserThrowsEOFException_RepackagesAndThrowsExpectedException() throws IOException {
        // Arrange
        
        // Apply + Assert
    }
    
    public void setState(String data) {
        LinkedList<Character> inputData = this.generateDataList(data);
        this.input = new PushbackAndPositionReaderMock(inputData);
    }
    
    private LinkedList<Character> generateDataList(String data) {
        LinkedList<Character> result = new LinkedList<>();
        
        for (int i = 0; i < data.length(); i++)
            result.add(data.charAt(i));
        
        return result;
    }
    
    @After
    public void tearDown() {
        this.parser = null;
        this.result = null;
        this.input = null;
    }
}