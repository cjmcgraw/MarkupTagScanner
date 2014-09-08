package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

import org.junit.*;

import com.mycompany.htmlvalidator.scanners.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions.*;
import com.mycompany.htmlvalidator.scanners.readers.utilities.*;

public class HtmlAttributesParserTest {;
    private static final String ATTR_DATA = "someAttrData";
    private static final String CMNT_DATA = "someCommentData";
    private static final String QUOTE_DATA = "someQuoteData";
    
    private static final String FILLER_DATA = "the quick fox jumped over the lazy brown dog";
    
    private static final HtmlAttribute QUOTE_ATTR = new HtmlAttribute(QUOTE_DATA);
    private static final HtmlAttribute STND_ATTR = new HtmlAttribute(ATTR_DATA);
    private static final HtmlAttribute CMNT_ATTR = new HtmlAttribute(CMNT_DATA);
    
    private static final String STANDARD_STR = "X";
    private static final String SINGLE_QUOTE = MarkupTag.SINGLE_QUOTE.toString();
    private static final String DOUBLE_QUOTE = MarkupTag.DOUBLE_QUOTE.toString();
    
    private static final String CLOSING_TAG = MarkupTag.CLOSING_TAG.toString();
    private static final String OPENING_TAG = MarkupTag.OPENING_TAG.toString();
    
    private static final String COMMENT_OPEN = MarkupTagNames.COMMENT_TAG.getBeginName();
    
    private static final HtmlAttribute ATTR_EOF_DATA = new HtmlAttribute("exceptionAttr", "some value");
    private static final AttributeException DEFAULT_ATTR_EOFEXCEPTION = new EndOfInputAttributeException(ATTR_EOF_DATA);
    
    private static final String COMPONENT_EOF_DATA = "someExceptionData";
    private static final HtmlAttribute COMPONENT_EOF_ATTR = new HtmlAttribute("someExceptionData");
    private static final ComponentException DEFAULT_COMPONENT_EOFEXCEPTION = new EndOfInputComponentException(COMPONENT_EOF_DATA);
    
    private static final String COMPONENT_MISSINGCHAR_DATA = "someMissingCharData";
    private static final HtmlAttribute COMPONENT_MISSINGCHAR_ATTR = new HtmlAttribute(COMPONENT_MISSINGCHAR_DATA);
    private static final ComponentException DEFAULT_COMPONENT_MISSINGCHAR_EXCEPTION = new MissingCharacterComponentException('c', null, COMPONENT_MISSINGCHAR_DATA);
    
    private static final String COMPONENT_UNEXPECTEDCHAR_DATA = "someUnexpectedCharData";
    private static final HtmlAttribute COMPONENT_UNEXPECTEDCHAR_ATTR = new HtmlAttribute(COMPONENT_UNEXPECTEDCHAR_DATA);;
    private static final ComponentException DEFAULT_COMPONENT_UNEXPECTEDCHAR_EXCEPTION = new UnexpectedCharacterComponentException('c', null, COMPONENT_UNEXPECTEDCHAR_DATA);
    
    private HtmlSingleAttributeParserMock attributeParser = new HtmlSingleAttributeParserMock(ATTR_DATA);
    private HtmlCommentAttributeParserMock commentParser = new HtmlCommentAttributeParserMock(CMNT_DATA);
    private HtmlQuoteEnclosureParserMock enclosureParser = new HtmlQuoteEnclosureParserMock(QUOTE_DATA);
    private WhitespaceConsumerMock whitespaceConsumer = new WhitespaceConsumerMock();
    
    
    private PushbackAndPositionReaderMock input;
    private HtmlAttributesParser parser;
    private MutableHtmlData result;
    
    
    @BeforeClass()
    public static void setUpClass() {
        /* ComponentException attributes are given references to the
         * respective errors caused by the attribute. This is not the case
         * for the fatal EOF type because it terminates the parsing of attributes.
         */
        COMPONENT_MISSINGCHAR_ATTR.addError(DEFAULT_COMPONENT_MISSINGCHAR_EXCEPTION);
        COMPONENT_UNEXPECTEDCHAR_ATTR.addError(DEFAULT_COMPONENT_UNEXPECTEDCHAR_EXCEPTION);
    }
    
    @Before
    public void setUp() {
        this.attributeParser.setError(null);
        this.commentParser.setError(null);
        this.enclosureParser.setError(null);
        this.whitespaceConsumer.setError(null);
        
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
        String str = STANDARD_STR + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_SoloAttr_StndAttr_SpaceBeforeClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_STR + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_SoloAttr_StndAttr_SpaceBeforeClosingTag_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_STR + " " + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_SoloAttr_StndAttr_NoSpaceBeforeClosingTag_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(STND_ATTR);
        String str = STANDARD_STR + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_SoloAttr_StndAttr_NoSpaceBeforeClosingTag_RemainingDataMatches() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_STR + CLOSING_TAG);
    }
    
    @Test
    public void testParse_SoloAttr_StndAttr_NoSpaceBeforeClosingTag_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_STR + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
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
        String str = STANDARD_STR + CLOSING_TAG;
        
        this.result.setName(COMMENT_OPEN);
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_SoloAttr_ResultHasCommentName_RemainingInputMatchesExpected() {
     // Test
        this.result.setName(COMMENT_OPEN);
        this.testParse_HasCommentName_ExpectedInputMatchesRemainingInput(STANDARD_STR + CLOSING_TAG);
    }
    
    @Test
    public void testParse_SoloAttr_ResultHasCommentName_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.result.setName(COMMENT_OPEN);
        this.testParse_HasCommentName_ExpectedInputMatchesRemainingInput(STANDARD_STR + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_PairAttr_TwoStndAttrs_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(STND_ATTR, STND_ATTR);
        String str = STANDARD_STR + " " + STANDARD_STR + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_PairAttr_TwoStndAttrs_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_STR + " " + STANDARD_STR + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_PairAttr_TwoStndAttrs_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_STR + " " + STANDARD_STR + " " + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    public void testParse_PairAttr_OneStndOneQuote_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(STND_ATTR, QUOTE_ATTR);
        String str = STANDARD_STR + " " + SINGLE_QUOTE + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_PairAttr_OneStndOneQuote_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_STR + " " + SINGLE_QUOTE + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_PairAttr_OneStndOneQuote_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_STR + " " + SINGLE_QUOTE + " "+ CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    public void testParse_PairAttr_OneStndOneDoubleQuote_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(STND_ATTR, QUOTE_ATTR);
        String str = STANDARD_STR + " " + DOUBLE_QUOTE + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_PairAttr_OneStndOneDoubleQuote_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_STR + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_PairAttr_OneStndOneDoubleQuote_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_STR + " " + DOUBLE_QUOTE + " "+ CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
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
        String str = STANDARD_STR + STANDARD_STR + CLOSING_TAG;
        
        this.result.setName(COMMENT_OPEN);
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_PairAttr_ResultHasCommentName_RemainingInputMatchesExpected() {
     // Test
        this.result.setName(COMMENT_OPEN);
        this.testParse_HasCommentName_ExpectedInputMatchesRemainingInput(STANDARD_STR  + STANDARD_STR + CLOSING_TAG);
    }
    
    @Test
    public void testParse_PairAttr_ResultHasCommentName_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.result.setName(COMMENT_OPEN);
        this.testParse_HasCommentName_ExpectedInputMatchesRemainingInput(STANDARD_STR + STANDARD_STR + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_MultiAttr_AllDiff_Standard_Single_Double_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(STND_ATTR, QUOTE_ATTR, QUOTE_ATTR);
        String str = STANDARD_STR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_MultiAttr_AllDiff_Standard_Single_Double_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_STR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_MultiAttr_AllDiff_Standard_Single_Double_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(STANDARD_STR + " " + SINGLE_QUOTE + "" + DOUBLE_QUOTE + " "+ CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_MultiAttr_AllDiff_Single_Double_Standard_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(QUOTE_ATTR, QUOTE_ATTR, STND_ATTR);
        String str = SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + STANDARD_STR + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_MultiAttr_AllDiff_Single_Double_Standard_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + STANDARD_STR + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_MultiAttr_AllDiff_Single_Double_Standard_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(SINGLE_QUOTE + " " + DOUBLE_QUOTE + " " + STANDARD_STR + " " + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    public void testParse_MultiAttr_AllDiff_Double_Standard_Single_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(QUOTE_ATTR, STND_ATTR, QUOTE_ATTR);
        String str = DOUBLE_QUOTE + " " + STANDARD_STR + " " + SINGLE_QUOTE + " " + CLOSING_TAG;
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_MultiAttr_AllDiff_Double_Standard_Single_RemainingInputMatchesExpected() {
     // Test
        this.testParse_ExpectedInputMatchesRemainingInput(DOUBLE_QUOTE + " " + STANDARD_STR + " " + SINGLE_QUOTE + " " + CLOSING_TAG);
    }
    
    @Test
    public void testParse_MultiAttr_AllDiff_Double_Standard_Single_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.testParse_ExpectedInputMatchesRemainingInput(DOUBLE_QUOTE + " " + STANDARD_STR + " " + SINGLE_QUOTE + " " + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
    }
    
    @Test
    public void testParse_MultiAttr_ResultHasCommentName_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(CMNT_ATTR);
        String str = STANDARD_STR + STANDARD_STR + STANDARD_STR +CLOSING_TAG;
        
        this.result.setName(COMMENT_OPEN);
        
        // Test
        this.testParse_ParsedAttrListMatchesExpectedAttrList(str, attrs);
    }
    
    @Test
    public void testParse_MultiAttr_ResultHasCommentName_RemainingInputMatchesExpected() {
     // Test
        this.result.setName(COMMENT_OPEN);
        this.testParse_HasCommentName_ExpectedInputMatchesRemainingInput(STANDARD_STR  + STANDARD_STR + STANDARD_STR + CLOSING_TAG);
    }
    
    @Test
    public void testParse_MultiAttr_ResultHasCommentName_AdditionalDataAfterClosingTag_RemainingInputMatchesExpected() {
        // Test
        this.result.setName(COMMENT_OPEN);
        this.testParse_HasCommentName_ExpectedInputMatchesRemainingInput(STANDARD_STR + STANDARD_STR + STANDARD_STR + CLOSING_TAG + "abcdefghijklmnopqrstuvwxyz");
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
        this.setState(STANDARD_STR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE +" " + OPENING_TAG);
        this.parse();
    }
    
    @Test
    public void testParse_OpeningTag_RemainingInputMatchesExpected_ThrowsException() {
        // Set up
        String remaining = OPENING_TAG + "abcdefghijklmnopqrstuvwxyz";
        String str = STANDARD_STR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE + remaining;
        
        // Test
        this.testParse_ThrowsException_RemainingInputMatchesExpected(str, remaining);
    }
    
    @Test
    public void testParse_OpeningTag_ExceptionResultMatchesStoredResult_ThrowsException() {
        // Set up
        MutableHtmlData exp = new MutableHtmlData();
        exp.updateAttributes(new HtmlAttribute(ATTR_DATA));
        exp.updateAttributes(new HtmlAttribute(QUOTE_DATA));
        exp.updateAttributes(new HtmlAttribute(QUOTE_DATA));
        
        // Test
        this.testParse_ThrowsException_ThrownExceptionMatchesStoredResult(STANDARD_STR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE + OPENING_TAG, exp);
    }
    
    @Test
    public void testParse_OpeningTag_ResultContainsExpectedAttrs() {
        // Set up
        List<HtmlAttribute> attrs = this.generateAttrList(STND_ATTR, QUOTE_ATTR, QUOTE_ATTR);
        String str = STANDARD_STR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE + OPENING_TAG;
        
        this.testParse_ThrowsException_ResultContainsExpectedAttrs(str, attrs);
    }
    
    @Test(expected=EndOfInputParsingException.class)
    public void testParse_InvalidInput_EmptyInput_ThrowsExpection() {
        /* Test that given an empty input the HtmlAttributesParser throws
         * an EndOfInputParsingException.
         * 
         * We need to load the state as empty.
         * 
         * Performing a parse over the empty state should trigger the
         * exception.
         */
        // Arrange
        this.setState("");
        
        // Apply + Assert
        this.parse();
    }
    
    @Test
    public void testParse_InvalidInput_EmptyInput_ThrowsException_RemainingInputMatchesExpected() {
        /* Test that when given an empty input, and an exception is thrown the
         * remaining input contains the expected data.
         * 
         * In this case it is almost an arbitrary test that is performed for
         * thoroughness.
         * 
         * Given an initial empty input, the expected remaining data will also be
         * empty. As I said before this seems entirely trivial because of course
         * if the initial input was empty, the remaining input will be empty.
         */
        // Test
        this.testParse_ThrowsException_RemainingInputMatchesExpected("", "");
    }
    
    @Test
    public void testParse_InvalidInput_EmptyInput_ThrowsException_ThrownExceptionMatchesExpectedResult() {
        /* Test that when given an empty input, and an exception is thrown, the 
         * HtmlData contained in the exception matches the expected HtmlData.
         * 
         * In this case an empty HtmlData object is expected. Because no attributes
         * have been added.
         */
        this.testParse_ThrowsException_ThrownExceptionMatchesStoredResult("", new MutableHtmlData());
    }
    
    @Test(expected=EndOfInputParsingException.class)
    public void testParse_InvalidInput_EOFDuringAttributeParser_ThrowsException() {
        /* Test that when the "attributeParser" component encounters an EndOfInputAttributeException
         * that the EndOfInputParsingException is triggered.
         * 
         * The error state of the "attributeParser" mock class has to be set to
         * simulate the actual "attributeParser" component throwing the given
         * exception
         */
        // Arrange
        this.primeAttributeParserWithError(DEFAULT_ATTR_EOFEXCEPTION);
        this.setState(STANDARD_STR);
        
        // Apply + Assert
        this.parse();
    }
    
    @Test
    public void testParse_InvalidInput_EOFDuringAttributeParser_RemainingInputMatchesexpected() {
        /* Test that when the "attributeParser" component encounters an EndOfInputAttributException
         * that the EndOfInputParsingExceptoin is triggered any trailing data after where the 
         * "attributeParser" would have triggered the exception remains in the input.
         * 
         * "attributeParser" needs to have the error that it will be throwing set. As such we
         * set it to some exception, with some attribute stored.
         */
        this.primeAttributeParserWithError(DEFAULT_ATTR_EOFEXCEPTION);
        
        /* The "attributeParser" mock is designed to trigger on any non quote, non comment data.
         * Which is represented here by "STANDARD_ATTR". "FILLER_DATA" simply represents a filler
         * that is leading data after the "STANDARD_ATTR".
         * 
         * Here the "FILLER_DATA" will be checked to still be in the input after the exception
         * is thrown by the "attributeParser"
         */
        this.testParse_ThrowsException_RemainingInputMatchesExpected(STANDARD_STR + FILLER_DATA, FILLER_DATA);
    }
    
    @Test
    public void testParse_InvalidInput_EOFDuringAttributeParser_ThrownExceptionContainsExpectedResult() {
        /* The purpose of this test case is to check that when an EOF is found during
         * the parsing done by the component "attributeParser" and it throws an error
         * that error is handled as expected by the class under test.*/
        
        /* "attributeParser" mock class is primed with an error that will carry
         * a specific attribute that represents the attribute that the error
         * occurred in.*/
        this.primeAttributeParserWithError(DEFAULT_ATTR_EOFEXCEPTION);;
        
        /* The expected result after parsing the standard state is to store the
         * attribute that caused the error, and then re-package the thrown error
         * as a "EndOfInputParsingException". Thus the expected result for this
         * test is a HtmlData object that contains the "attr" as the only attribute*/
        MutableHtmlData expected = new MutableHtmlData();
        expected.updateAttributes(ATTR_EOF_DATA);
        
        /* State is set with "STANDARD_STR" which is a default character that
         * is used to trigger the data to be passed to the "attributeParser"*/
        this.testParse_ThrowsException_ThrownExceptionMatchesStoredResult(STANDARD_STR, expected);
    }
    
    @Test
    public void testParse_InvalidInput_EOFDuringAttributeParser_WithLeadingAttrs_ThrownExceptionContainsExpectedResult() {
        /* The purpose of this test case is to check when an EOF is found during
         * the parsing done by the component "attributeParser" and it throws an error
         * that all previously parsed attributes, and the currently parsed attribute
         * are passed through the exception thrown via the test class
         */
        
        this.primeAttributeParserWithError(DEFAULT_ATTR_EOFEXCEPTION);;
        
        /* The expected result after parsing will contain two attributes that
         * didn't cause errors, and a third attribute that did cause the error.
         * This is to ensure that previous attributes were not effected by the
         * error occurring in the parsing of a different attribute
         */
        MutableHtmlData expected = new MutableHtmlData();
        // "QUOTE_ATTR" chosen because "enclosureParser" component is not set
        // to throw an exception
        expected.updateAttributes(QUOTE_ATTR);
        expected.updateAttributes(QUOTE_ATTR);
        expected.updateAttributes(ATTR_EOF_DATA);
        
        /* State is set with "SINGLE_QUOTE" and "DOUBLE_QUOTE" are chosen to
         * proceed "STANDARD_STR" to allow for "enclosureParser" component to
         * parse valid attributes before reaching the error in "attributeParser*/
        this.testParse_ThrowsException_ThrownExceptionMatchesStoredResult(SINGLE_QUOTE + DOUBLE_QUOTE + STANDARD_STR, expected);
    }
    
    @Test(expected=EndOfInputParsingException.class)
    public void testParse_InvalidInput_EOFDuringCommentParser_ThrowsException() {
        /* The purpose of this test case is to check when an EOF is found during
         * the parsing done by the component "commentParser" and it throws an
         * error that error is wrapped by the test class into an 
         * EndOfInputParsingException */
        this.primeCommentParserWithError(DEFAULT_ATTR_EOFEXCEPTION);
        this.setupDataToGoToCommentParser();
        
        this.setState(FILLER_DATA);
        this.parse();
    }
    
    @Test
    public void testParse_InvalidInput_EOFDuringCommentParser_RemainingInputMatchesExpected() {
        /* The purpose of this test case is to check when an EOF is found during 
         * the parsing done by the "commentParser" and it throws an error, the
         * remaining data in the input after the error was found is what is the
         * expected data*/
        this.primeCommentParserWithError(DEFAULT_ATTR_EOFEXCEPTION);
        this.setupDataToGoToCommentParser();
        
        /* Since we are using a mock comment parser it won't actually consume
         * any of the input. As such the remaining input is expected to match
         * the filler data*/
        
        String state = FILLER_DATA;
        String remainingData = FILLER_DATA;
        
        this.testParse_ThrowsException_RemainingInputMatchesExpected(state, remainingData);
    }
    
    @Test
    public void testParse_InvalidInput_EOFDuringCommentParser_ThrownExceptionContainsExpectedResult() {
        /* The purpose of this test case is to check when an EOF is found during
         * the parsing done by the comment parser and it throws an error, the
         * repackaged EndOfInputParsingException contains the expected data*/
        this.primeCommentParserWithError(DEFAULT_ATTR_EOFEXCEPTION);
        this.setupDataToGoToCommentParser();
        
        
        String state = FILLER_DATA;
        
        // expected data is set up with expected name and attr
        MutableHtmlData expected = new MutableHtmlData();
        expected.setName(MarkupTagNames.COMMENT_TAG.getBeginName());
        expected.updateAttributes(ATTR_EOF_DATA);
        
        this.testParse_ThrowsException_ThrownExceptionMatchesStoredResult(state, expected);
    }
    
    @Test(expected=EndOfInputParsingException.class)
    public void testParse_InvalidInput_EOFDuringEnclosureParser_ThrowsException() {
        /* The purpose of this test is to check when an EOF is found during the 
         * parsing done by the enclosure parser and it throws an error, the error
         * is successfully interpreted and repackaged as an EndOfInputParsingException
         * */
        // Arrange
        this.primeEnclosureParserWithError(DEFAULT_COMPONENT_EOFEXCEPTION);
        this.setState(SINGLE_QUOTE);
        
        // Apply + Assert
        this.parse();
    }
    
    @Test
    public void testParse_InvalidInput_EOFDuringEnclosureParser_RemainingInputMatchesExpected() {
        /* The purpose of this test is to check when an EOF is found during the
         * parsing done by he enclosure parser and it throws an error, that any
         * remaining non-enclosure input hasn't been consumed and still remains
         */
        // Set up
        this.primeEnclosureParserWithError(DEFAULT_COMPONENT_EOFEXCEPTION);
        
        String remaining = FILLER_DATA;
        String state = SINGLE_QUOTE + remaining;
        
        // Test
        this.testParse_ThrowsException_RemainingInputMatchesExpected(state, remaining);
    }
    
    @Test
    public void testParse_InvalidInput_EOFDuringEnclosureParser_ThrownExceptionContainsExpectedResult() {
        /* The purpose of this test is to check when an EOF is found during the
         * parsing done by the enclosure parser and it throws an error, that the
         * data being parsed by the enclosure parser is caught and placed as the
         * last attribute parsed in the resultant data.
         */
        // Set up
        this.primeEnclosureParserWithError(DEFAULT_COMPONENT_EOFEXCEPTION);
        
        String fillerAttrs = STANDARD_STR + STANDARD_STR + STANDARD_STR;
        String exceptionState = SINGLE_QUOTE;
        String state = fillerAttrs + exceptionState;
        
        MutableHtmlData exp = new MutableHtmlData();
        exp.updateAttributes(STND_ATTR);
        exp.updateAttributes(STND_ATTR);
        exp.updateAttributes(STND_ATTR);
        exp.updateAttributes(COMPONENT_EOF_ATTR);
        
        // Test
        this.testParse_ThrowsException_ThrownExceptionMatchesStoredResult(state, exp);
    }
    
    @Test
    public void testParse_InvalidInput_MissingCharacterComponentExceptionDuringEnclosureParser_CatchesException() {
        /* The purpose of this test is to check when a MissingCharacterComponentException
         * is thrown in the enclosure parser, that it is caught and doesn't raise
         * an exception
         */
        this.primeEnclosureParserWithError(DEFAULT_COMPONENT_MISSINGCHAR_EXCEPTION);
        
        this.setState(SINGLE_QUOTE + CLOSING_TAG);
        
        this.parse();
    }
    
    @Test
    public void testParse_InvalidInput_MissingCharacterComponentExceptionDuringEnclosureParser_RemainingInputAlsoConsumed() {
        /* The purpose of this test is to check that when a MissingCharacterComponentException
         * occurs it is caught, and any remaining input is consumed
         */
        this.primeEnclosureParserWithError(DEFAULT_COMPONENT_MISSINGCHAR_EXCEPTION);
        this.setState(SINGLE_QUOTE + STANDARD_STR + STANDARD_STR + CLOSING_TAG);
        
        HtmlData data;
        
        MutableHtmlData exp = new MutableHtmlData();
        exp.updateAttributes(COMPONENT_MISSINGCHAR_ATTR);
        exp.updateAttributes(STND_ATTR);
        exp.updateAttributes(STND_ATTR);
        
        data = this.parse();
        
        assertEquals(exp, data);
        
    }
    
    @Test
    public void testParse_InvalidInput_UnexpectedCharacterComponentExceptionDuringEnclosureParser_CatchesException() {
        /* The purpose of this test is to check that when a UnexpectedCharacterComponentException
         * is thrown by the enclosure parser, that it is caught and doesn't cause a
         * system failure. 
         */
        // Set up
        this.primeEnclosureParserWithError(DEFAULT_COMPONENT_UNEXPECTEDCHAR_EXCEPTION);
        this.setState(SINGLE_QUOTE + CLOSING_TAG);
        
        // Test
        this.parse();
    }
    
    @Test
    public void testParse_InvalidInput_UnexpectedCharacterComponentExceptoinDuringEnclosureParser_RemainingInputAlsoConsumed() {
        /* The purpose of this test is to check that when an UnexpectedCharacterComponentException
         * is thrown by the enclosure parser, that it allows for the system to
         * parse the error data into the data and then continues parsing the
         * remaining attributes.
         */
        // Set up
        this.primeEnclosureParserWithError(DEFAULT_COMPONENT_UNEXPECTEDCHAR_EXCEPTION);
        this.setState(SINGLE_QUOTE + STANDARD_STR + STANDARD_STR + CLOSING_TAG);
        
        HtmlData data;
        MutableHtmlData exp = new MutableHtmlData();
        exp.updateAttributes(COMPONENT_UNEXPECTEDCHAR_ATTR);
        exp.updateAttributes(STND_ATTR);
        exp.updateAttributes(STND_ATTR);
        
        data = this.parse();
        
        assertEquals(exp, data);
    }
    
    @Test(expected=EndOfInputParsingException.class)
    public void testParse_InvalidInput_EOFDuringWhitespaceConsumer_ThrowsException() {
        /* This test case is used to ensure that when an EOF is encountered in the
         * whitespace consumer it repackages it and throws the EndOfInputParsingException
         * instead
         */
        this.primeWhitespaceConsumerWithError(DEFAULT_COMPONENT_EOFEXCEPTION);
        this.setState(CLOSING_TAG);
        
        this.parse();
    }
    
    @Test
    public void testParse_InvalidInput_EOFDuringWhitespaceConsumer_RemainingInputMatchesExpected() {
        /* This test case is used to ensure that any input remaining after the EOF
         * is found in the whitespace consumer remains present in the input.
         */
        this.primeWhitespaceConsumerWithError(DEFAULT_COMPONENT_EOFEXCEPTION);
        
        this.testParse_ThrowsException_RemainingInputMatchesExpected(FILLER_DATA + CLOSING_TAG, FILLER_DATA + CLOSING_TAG);
    }
    
    private void testParse_ThrowsException_RemainingInputMatchesExpected(String state, String remaining) {
        // Arrange
        this.setState(state);
        
        // Apply
        try {
            this.parse();
        } catch (ParsingException e) {}
        
        // Assert
        assertEquals(remaining, this.input.getRemainingData());
    }
    
    private void testParse_ThrowsException_ThrownExceptionMatchesStoredResult(String state, HtmlData exp) {
        // Arrange
        HtmlData data = null;
        this.setState(state);
        
        // Apply
        try {
            this.parse();
        } catch (ParsingException e) {
            data = e.getHtmlData();
        }
        
        // Assert
        assertEquals(exp, data);
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
    
    private HtmlData parse() {
        try {
            return this.parser.parse(this.input, this.result);
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
        
        this.setState(STANDARD_STR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE);
        
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
        
        this.setState(STANDARD_STR + " " + SINGLE_QUOTE + " " + DOUBLE_QUOTE);
        
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
    
    private void setupDataToGoToCommentParser() {
        this.result.setName(MarkupTagNames.COMMENT_TAG.getBeginName());
    }
    
    private void primeAttributeParserWithError(AttributeException err) {
        this.attributeParser.setError(err);
    }
    
    private void primeEnclosureParserWithError(ComponentException err) {
        this.enclosureParser.setError(err);
    }
    
    private void primeCommentParserWithError(AttributeException err) {
        this.commentParser.setError(err);
    }
    
    private void primeWhitespaceConsumerWithError(ComponentException err) {
        this.whitespaceConsumer.setError(err);
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