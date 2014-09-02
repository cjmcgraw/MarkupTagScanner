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
    private static final String CMNT_DATA = "someCommentData";
    private static final String QUOTE_DATA = "someQuoteData";
    
    private static final HtmlAttribute QUOTE_ATTR = new HtmlAttribute(QUOTE_DATA);
    private static final HtmlAttribute STND_ATTR = new HtmlAttribute(ATTR_DATA);
    private static final HtmlAttribute CMNT_ATTR = new HtmlAttribute(CMNT_DATA);
    
    private static final String STANDARD_ATTR = "X";
    private static final String SINGLE_QUOTE = MarkupTag.SINGLE_QUOTE.toString();
    private static final String DOUBLE_QUOTE = MarkupTag.DOUBLE_QUOTE.toString();
    
    private static final String CLOSING_TAG = MarkupTag.CLOSING_TAG.toString();
    private static final String OPENING_TAG = MarkupTag.OPENING_TAG.toString();
    
    private static final String COMMENT_OPEN = MarkupTagNames.COMMENT_TAG.getBeginName();
    
    private HtmlSingleAttributeParserMock attributeParser = new HtmlSingleAttributeParserMock(ATTR_DATA);
    private HtmlCommentAttributeParserMock commentParser = new HtmlCommentAttributeParserMock(CMNT_DATA);
    private HtmlQuoteEnclosureParserMock enclosureParser = new HtmlQuoteEnclosureParserMock(QUOTE_DATA);
    private WhitespaceConsumerMock whitespaceConsumer = new WhitespaceConsumerMock();
    
    
    private PushbackAndPositionReaderMock input;
    private HtmlAttributesParser parser;
    private MutableHtmlData result;
    
    
    @Before
    public void setUp() {
        this.parser = new HtmlAttributesParser(this.enclosureParser,
                                               this.attributeParser,
                                               this.commentParser,
                                               this.whitespaceConsumer);
        this.result = new MutableHtmlData();
    }
    
    @Test
    public void testParse_SoloAttr_StndAttr_SpaceBeforeClosingTag_ResultContainsExpectedAttrs()  {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(STND_ATTR);
        String str = STANDARD_ATTR + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_SoloAttr_StndAttr_SpaceBeforeClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_ATTR + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_SoloAttr_StndAttr_SpaceBeforeClosingTag_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_ATTR + " " + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_SoloAttr_StndAttr_NoSpaceBeforeClosingTag_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(STND_ATTR);
        String str = STANDARD_ATTR + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_SoloAttr_StndAttr_NoSpaceBeforeClosingTag_RemainingDataMatches() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_ATTR + CLOSING_TAG);
    }
    
    @Test
    public void testParse_SoloAttr_StndAttr_NoSpaceBeforeClosingTag_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_ATTR + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_SoloAttr_SingleQuoteAttr_SpaceBeforeClosingTag_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(QUOTE_ATTR);
        String str = SINGLE_QUOTE + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_SoloAttr_SingleQuoteAttr_SpaceBeforeClosingTag_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(SINGLE_QUOTE + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_SoloAttr_SingleQuoteAttr_SpaceBeforeClosingTag_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(SINGLE_QUOTE + " " + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_SoloAttr_SingleQuoteAttr_NoSpaceBeforeClosingTag_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(QUOTE_ATTR);
        String str = SINGLE_QUOTE + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_SoloAttr_SingleQuoteAttr_NoSpaceBeforeClosingTag_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(SINGLE_QUOTE + CLOSING_TAG);
    }
    
    @Test
    public void testParse_SoloAttr_SingleQuoteAttr_NoSpaceBeforeClosingTag_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(SINGLE_QUOTE + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_SoloAttr_DoubleQuoteAttr_SpaceBeforeClosingTag_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(QUOTE_ATTR);
        String str = DOUBLE_QUOTE + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_SoloAttr_DoubleQuoteAttr_SpaceBeforeClosingTag_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(DOUBLE_QUOTE + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_SoloAttr_DoubleQuoteAttr_SpaceBeforeClosingTag_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(DOUBLE_QUOTE + " " + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_SoloAttr_DoubleQuoteAttr_NoSpaceBeforeClosingTag_ResultContainsAttrs() {
     // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(QUOTE_ATTR);
        String str = DOUBLE_QUOTE + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_SoloAttr_DoubleQuoteAttr_NoSpaceBeforeClosingTag_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(DOUBLE_QUOTE + CLOSING_TAG);
    }
    
    @Test
    public void testParse_SoloAttr_DoubleQuoteAttr_NoSpaceBeforeClosingTag_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(DOUBLE_QUOTE + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_SoloAttr_ResultHasCommentName_ResultContainsAttrs() {
     // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(CMNT_ATTR);
        String str = STANDARD_ATTR + CLOSING_TAG;
        
        this.result.setName(COMMENT_OPEN);
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_SoloAttr_ResultHasCommentName_RemainingInputMatchesExpected() {
     // Test
        this.result.setName(COMMENT_OPEN);
        this.testParse_HasCommentName_ExpectedInputMatchesRemainingInput(STANDARD_ATTR + CLOSING_TAG);
    }
    
    @Test
    public void testParse_SoloAttr_ResultHasCommentName_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.result.setName(COMMENT_OPEN);
        this.testParse_HasCommentName_ExpectedInputMatchesRemainingInput(STANDARD_ATTR + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_PairAttr_TwoStndAttrs_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(STND_ATTR, STND_ATTR);
        String str = STANDARD_ATTR + " " + STANDARD_ATTR + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_PairAttr_TwoStndAttrs_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_ATTR + " " + STANDARD_ATTR + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_PairAttr_TwoStndAttrs_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_ATTR + " " + STANDARD_ATTR + " " + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    public void testParse_PairAttr_OneStndOneQuote_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(STND_ATTR, QUOTE_ATTR);
        String str = STANDARD_ATTR + " " + SINGLE_QUOTE + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_PairAttr_OneStndOneQuote_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_ATTR + " " + SINGLE_QUOTE + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_PairAttr_OneStndOneQuote_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_ATTR + " " + SINGLE_QUOTE + " "+ CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    public void testParse_PairAttr_OneStndOneDoubleQuote_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(STND_ATTR, QUOTE_ATTR);
        String str = STANDARD_ATTR + " " + DOUBLE_QUOTE + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_PairAttr_OneStndOneDoubleQuote_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_ATTR + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_PairAttr_OneStndOneDoubleQuote_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_ATTR + " " + DOUBLE_QUOTE + " "+ CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_PairAttr_TwoQuotes_BothDiff_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(QUOTE_ATTR, QUOTE_ATTR);
        String str = SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_pairAttr_TwoQuotes_BothDiff_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_PairAttr_TwoQuotes_BothDiff_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(SINGLE_QUOTE + " " + DOUBLE_QUOTE + " "+ CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_PairAttr_TwoQuotes_BothSame_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(QUOTE_ATTR, QUOTE_ATTR);
        String str =  DOUBLE_QUOTE+ " " + DOUBLE_QUOTE + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_PairAttr_TwoQuotes_BothSame_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(DOUBLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_PairAttr_TwoQuotes_BothSame_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(DOUBLE_QUOTE + " " + DOUBLE_QUOTE + " "+ CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_PairAttr_ResultHasCommentName_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(CMNT_ATTR);
        String str = STANDARD_ATTR + STANDARD_ATTR + CLOSING_TAG;
        
        this.result.setName(COMMENT_OPEN);
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_PairAttr_ResultHasCommentName_RemainingInputMatchesExpected() {
     // Test
        this.result.setName(COMMENT_OPEN);
        this.testParse_HasCommentName_ExpectedInputMatchesRemainingInput(STANDARD_ATTR  + STANDARD_ATTR + CLOSING_TAG);
    }
    
    @Test
    public void testParse_PairAttr_ResultHasCommentName_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.result.setName(COMMENT_OPEN);
        this.testParse_HasCommentName_ExpectedInputMatchesRemainingInput(STANDARD_ATTR + STANDARD_ATTR + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_MultiAttr_AllDiff_Standard_Single_Double_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(STND_ATTR, QUOTE_ATTR, QUOTE_ATTR);
        String str = STANDARD_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_MultiAttr_AllDiff_Standard_Single_Double_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_MultiAttr_AllDiff_Standard_Single_Double_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_ATTR + " " + SINGLE_QUOTE + "" + DOUBLE_QUOTE + " "+ CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_MultiAttr_AllDiff_Single_Double_Standard_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(QUOTE_ATTR, QUOTE_ATTR, STND_ATTR);
        String str = SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + STANDARD_ATTR + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_MultiAttr_AllDiff_Single_Double_Standard_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + STANDARD_ATTR + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_MultiAttr_AllDiff_Single_Double_Standard_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + STANDARD_ATTR + " " + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    public void testParse_MultiAttr_AllDiff_Double_Standard_Single_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(QUOTE_ATTR, STND_ATTR, QUOTE_ATTR);
        String str = DOUBLE_QUOTE + " " + STANDARD_ATTR + " " + SINGLE_QUOTE + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_MultiAttr_AllDiff_Double_Standard_Single_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(DOUBLE_QUOTE + " " + STANDARD_ATTR + " " + SINGLE_QUOTE + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_MultiAttr_AllDiff_Double_Standard_Single_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(DOUBLE_QUOTE + " " + STANDARD_ATTR + " " + SINGLE_QUOTE + " " + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_MultiAttr_ResultHasCommentName_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(CMNT_ATTR);
        String str = STANDARD_ATTR + STANDARD_ATTR + STANDARD_ATTR +CLOSING_TAG;
        
        this.result.setName(COMMENT_OPEN);
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_MultiAttr_ResultHasCommentName_RemainingInputMatchesExpected() {
     // Test
        this.result.setName(COMMENT_OPEN);
        this.testParse_HasCommentName_ExpectedInputMatchesRemainingInput(STANDARD_ATTR  + STANDARD_ATTR + STANDARD_ATTR + CLOSING_TAG);
    }
    
    @Test
    public void testParse_MultiAttr_ResultHasCommentName_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.result.setName(COMMENT_OPEN);
        this.testParse_HasCommentName_ExpectedInputMatchesRemainingInput(STANDARD_ATTR + STANDARD_ATTR + STANDARD_ATTR + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    private void testParse_ParsedAttrListMatchesExpectedAttrList(String str, List<HtmlAttribute> attrs) {
        // Arrange
        this.setState(str);
        
        // Apply 
        this.parse();
        
        // Assert
        assertEquals(attrs, this.result.getAttributes());
    }
    
    private void testParse_ExpectedInputMatchesRemainingInput(String str) {
        // Arrange
        String expData = CLOSING_TAG + str.split(CLOSING_TAG, 2)[1];
        
        this.setState(str);
        
        // Apply
        this.parse();
        
        // Assert
        assertEquals(expData, this.input.getRemainingData());
    }
    
    private void testParse_HasCommentName_ExpectedInputMatchesRemainingInput(String str) {
        // Arrange
        String expData = str.substring(1);
        
        this.setState(str);
        
        // Apply
        this.parse();
        
        // Assert
        assertEquals(expData, this.input.getRemainingData());
    }
    
    @Test(expected=InvalidStateException.class)
    public void testParse_WithNullEnclosureParser_ThrowsExpectedException() {
        // Arrange
        this.parser = new HtmlAttributesParser(null,
                                               this.attributeParser,
                                               this.commentParser,
                                               this.whitespaceConsumer);
        // Apply + Assert
        this.parserDryRun();
    }
    
    @Test(expected=InvalidStateException.class)
    public void testParse_WithNullAttributeParser_ThrowsExpectedException() {
        // Arrange
        this.parser = new HtmlAttributesParser(this.enclosureParser,
                                               null,
                                               this.commentParser,
                                               this.whitespaceConsumer);
        // Apply + Assert
        this.parserDryRun();
    }
    
    @Test(expected=InvalidStateException.class)
    public void testParse_WithNullCommentParser_ThrowsExpectedException() {
        // Arrange
        this.parser = new HtmlAttributesParser(this.enclosureParser,
                                               this.attributeParser,
                                               null,
                                               this.whitespaceConsumer);
        // Apply + Assert
        this.parserDryRun();
    }
    
    @Test(expected=InvalidStateException.class)
    public void testParse_WithNullWhitespaceConsumer_ThrowsExpectedException() {
        // Arrange
        this.parser = new HtmlAttributesParser(this.enclosureParser,
                                               this.attributeParser,
                                               this.commentParser,
                                               null);
        // Apply + Assert
        this.parserDryRun();
    }
    
    @Test(expected=InvalidStateException.class)
    public void testParse_WithNullInput_ThrowsExpectedException() throws IOException {
        // Apply + Assert
        this.parser.parse(null, this.result);
    }
    
    @Test(expected=InvalidStateException.class)
    public void testParse_WithNullResult_ThrowsExpectedException() throws IOException {
        // Apply + Assert
        this.parser.parse(this.input, null);
    }
    
    @Test(expected=UnclosedTagParsingException.class)
    public void testParse_OpeningTag_ThrowsExpectedException() {
        // Arrange
        this.setState(STANDARD_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE +" " + OPENING_TAG);
        this.parse();
    }
    
    @Test
    public void testParse_OpeningTag_RemainingInputMatchesExpected_ThrowsException() {
        // Set up
        String remaining = OPENING_TAG + "abcdefghijklmnopqrstuvwxyz";
        String str = STANDARD_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE + remaining;
        
        // Test
        this.testParse_ThrowsException_RemainingInputMatchesExpected(str, remaining);
    }
    
    @Test
    public void testParse_OpeningTag_ExceptionResultMatchesStoredResult_ThrowsException() {
        // Test
        this.testParse_ThrowsException_ThrownExceptionMatchesStoredResult(STANDARD_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE + OPENING_TAG);
    }
    
    @Test
    public void testParse_OpeningTag_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(STND_ATTR, QUOTE_ATTR, QUOTE_ATTR);
        String str = STANDARD_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE + OPENING_TAG;
        
        this.testParse_ThrowsException_ResultContainsExpectedAttrs(str, attrs);
    }
    
    @Test(expected=EndOfInputParsingException.class)
    public void testParse_MissingClosingTag_ThrowsExpectedException() {
        // Arrange
        this.setState(STANDARD_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE);
        
        // Apply + Assert
        this.parse();
    }
    
    @Test
    public void testParse_missingClosingTag_ExceptionResultMatchesStoredResult_ThrowsException() {
        // Test
        this.testParse_ThrowsException_ThrownExceptionMatchesStoredResult(STANDARD_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE);
    }
    
    @Test
    public void testParse_MissingClosingTag_RemainingInputMatchesExpected_ThrowsException() {
        // Test
        this.testParse_ThrowsException_RemainingInputMatchesExpected(STANDARD_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE, "");
    }
    
    @Test
    public void testParse_MissingClosingTag_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(STND_ATTR, QUOTE_ATTR, QUOTE_ATTR);
        String str = STANDARD_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE;
        
        // Test
        this.testParse_ThrowsException_ResultContainsExpectedAttrs(str, attrs);
    }
    
    private void testParse_ThrowsException_RemainingInputMatchesExpected(String str, String remaining) {
        // Arrange
        this.setState(str);
        
        // Apply
        try {
            this.parse();
        } catch (ParsingException e) {}
        
        // Assert
        assertEquals(remaining, this.input.getRemainingData());
    }
    
    private void testParse_ThrowsException_ThrownExceptionMatchesStoredResult(String str) {
        // Arrange
        HtmlData data = null;
        
        this.result.setName("some unique name");
        this.setState(str);
        
        // Apply
        try {
            this.parse();
        } catch (ParsingException e) {
            data = e.getHtmlData();
        }
        
        // Assert
        assertEquals(this.result, data);
    }
    
    private void testParse_ThrowsException_ResultContainsExpectedAttrs(String str, List<HtmlAttribute> attrs) {
        // Arrange
        this.setState(str);
        
        // Apply
        try {
            this.parse();
        } catch (ParsingException e) {}
        
        // Assert
        assertEquals(attrs, this.result.getAttributes());
    }
    
    private void parserDryRun() {
        // Arrange
        this.setState("some data");
        
        // Apply
        this.parse();
    }
    
    private void parse() {
        try {
            this.parser.parse(this.input, this.result);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    
    @Test
    public void testParse_MissingClosingTag_ThrownExceptionMatchesExpectedResult() {
        // Arrange
        MutableHtmlData expData = new MutableHtmlData();
        HtmlData data = null;
        
        expData.updateAttributes(STND_ATTR);
        expData.updateAttributes(QUOTE_ATTR);
        expData.updateAttributes(QUOTE_ATTR);
        
        this.setState(STANDARD_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE);
        
        // Apply
        try {
            this.parse();
        } catch (EndOfInputParsingException err) {
            data = err.getHtmlData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MissingClosingTag_ArgResultMatchesExpectedResult() {
        // Arrange
        MutableHtmlData expData = new MutableHtmlData();
        HtmlData data = null;
        
        expData.updateAttributes(STND_ATTR);
        expData.updateAttributes(QUOTE_ATTR);
        expData.updateAttributes(QUOTE_ATTR);
        
        this.setState(STANDARD_ATTR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE);
        
        // Apply
        try {
            this.parse();
        } catch (EndOfInputParsingException err) {
            data = this.result;
        }
        
        // Assert
        assertEquals(expData, data);
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
    
    private List<HtmlAttribute> generateAttrList(HtmlAttribute... attrs) {
        List<HtmlAttribute> result = new ArrayList<>();
        
        for (HtmlAttribute attr : attrs)
            result.add(attr);
            
        return result;
    }
    
    @After
    public void tearDown() {
        this.parser = null;
        this.result = null;
        this.input = null;
    }
}