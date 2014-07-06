package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.*;

import com.mycompany.htmlvalidator.scanners.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.EndOfInputParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.UnclosedTagParsingException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReaderMock;

public class HtmlAttributeParserTest {
    public static final String DEFAULT = "someData";
    public static final String DEFAULT_WITH_VALUE = DEFAULT + "=someValue";
    public static final String DEFAULT_WITH_QUOTES = DEFAULT + "=\"someValue\"";
    public static final String DEFAULT_MULTIPLE = DEFAULT + " " + DEFAULT_WITH_VALUE + " " + DEFAULT_WITH_QUOTES;
    
    public static final HtmlAttribute DEFAULT_EXP_ATTR = new HtmlAttribute(DEFAULT, "");
    public static final HtmlAttribute DEFAULT_CLOSING_ATTR = new HtmlAttribute(MarkupTag.CLOSING_ATTRIBUTE.toString());
    public static final HtmlAttribute DEFAULT_WITH_VALUE_EXP_ATTR = new HtmlAttribute(DEFAULT, "someValue");
    public static final HtmlAttribute DEFAULT_WITH_QUOTES_EXP_ATTR = new HtmlAttribute(DEFAULT, "\"someValue\"");
    
    public static final String DEFAULT_CLOSE = MarkupTag.CLOSING_TAG.toString();
    public static final String SELF_CLOSING = " " + MarkupTag.CLOSING_ATTRIBUTE.toString() + DEFAULT_CLOSE;
    public static final String LEADING_WHITESPACE = "    " + System.lineSeparator() + System.lineSeparator() + "\t\t        ";
    
    private HtmlAttributeParser parser = new HtmlAttributeParser();
    private PushbackAndPositionReaderMock input;
    private List<HtmlAttribute> attributes;
    private MutableHtmlData result;
    
    
    @Before
    public void setUp() {
        this.setState(DEFAULT + DEFAULT_CLOSE);
    }
    
    @Test
    public void testParse_SingleAttributeWithNoValue_StandardClose_FirstElementMIsData() throws IOException {
        // Arrange
        HtmlAttribute expData = DEFAULT_EXP_ATTR;
        HtmlAttribute data;
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttributeWithNovalues_StandardClose_ResultIsexpectedNumberOfAttributes() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.size();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttributeWithNoValue_StandardClose_EnclosureTagRemains() throws IOException {
        // Arrange
        String expData = ">";
        String data;
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttributeWithNoValue_StandardClose_LeadingWhitespace_FirstElementIsDefault() throws IOException {
        // Arrange
        HtmlAttribute expData = DEFAULT_EXP_ATTR;
        HtmlAttribute data;
        
        this.setState(LEADING_WHITESPACE + DEFAULT + DEFAULT_CLOSE);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttributeWithNoValue_StandardClose_LeadingWhitespace_ResultIsExpectedNumberOfAttributes() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(LEADING_WHITESPACE + DEFAULT + DEFAULT_CLOSE);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.size();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttributeWithNoValue_StandardClose_LeadingWhitespace_EnclosureTagRemains() throws IOException {
        // Arrange
        String expData = ">";
        String data;
        
        this.setState(LEADING_WHITESPACE + DEFAULT + DEFAULT_CLOSE);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttributeWithNoValue_SelfClosing_FirstAttributeIsData() throws IOException {
        // Arrange
        HtmlAttribute expData = DEFAULT_EXP_ATTR;
        HtmlAttribute data;
        
        this.setState(DEFAULT + SELF_CLOSING);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttributeWithNoValue_SelfClosing_SecondAttributeIsClosingAttribute() throws IOException {
        // Arrange
        HtmlAttribute expData = DEFAULT_CLOSING_ATTR;
        HtmlAttribute data;
        
        this.setState(DEFAULT + SELF_CLOSING);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(1);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttributeWithNoValue_SelfClosing_ResultIsExpectedNumberOfAttributes() throws IOException {
        // Arrange
        int expData = 2;
        int data;
        
        this.setState(DEFAULT + SELF_CLOSING);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.size();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttributeWithNoValue_SelfClosing_EnclosureTagRemains() throws IOException {
        // Arrange
        String expData = ">";
        String data;
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttributeWithSomeValue_StandardClose_FirstElementIsData() throws IOException {
        // Arrange
        HtmlAttribute expData = DEFAULT_WITH_VALUE_EXP_ATTR;
        HtmlAttribute data;
        
        this.setState(DEFAULT_WITH_VALUE + DEFAULT_CLOSE);
       
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttributeWithSomeValue_StandardClose_ResultIsExpectedNumberOfAttributes() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(DEFAULT_WITH_VALUE + DEFAULT_CLOSE);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.size();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttributeWithSomeValue_StandardClose_EnclosureTagRemains() throws IOException {
        // Arrange
        String expData = ">";
        String data;
        
        this.setState(DEFAULT_WITH_VALUE + DEFAULT_CLOSE);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SelfClosingOnly_FirstElementIsData() throws IOException {
        // Arrange
        HtmlAttribute expData = DEFAULT_CLOSING_ATTR;
        HtmlAttribute data;
        
        this.setState(SELF_CLOSING);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SelfClosingOnly_ResultIsExpectedNumberOfAttributes() throws IOException {
        // Arrange
        int expData = 1;
        int data;
        
        this.setState(SELF_CLOSING);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.size();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SelfClosingOnly_EnclosureTagRemains() throws IOException {
        // Arrange
        String expData = ">";
        String data;
        
        this.setState(SELF_CLOSING);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAtributeWithSomeValueInQuotes_StandardClose() throws IOException {
       // Arrange
        HtmlAttribute expData = DEFAULT_WITH_QUOTES_EXP_ATTR;
        HtmlAttribute data;
        
        this.setState(DEFAULT_WITH_QUOTES + DEFAULT_CLOSE);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttributeWithSomeValueInQuotes_StandardClose_EnclosureTagRemains() throws IOException {
        // Arrange
        String expData = ">";
        String data;
        
        this.setState(DEFAULT_WITH_QUOTES + DEFAULT_CLOSE);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttributeWithSomeValueInQuotes_SelfClosing() throws IOException {
        // Arrange
        HtmlAttribute expData = DEFAULT_WITH_QUOTES_EXP_ATTR;
        HtmlAttribute data;
        
        this.setState(DEFAULT_WITH_QUOTES + SELF_CLOSING);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttributeWithSomeValueInQuotes_SelfClosing_ClosingAttribute() throws IOException {
        // Arrange
        HtmlAttribute expData = DEFAULT_CLOSING_ATTR;
        HtmlAttribute data;
        
        this.setState(DEFAULT_WITH_QUOTES + SELF_CLOSING);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(1);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttributeWithSomeValueInQuotes_SelfClosing_TagEnclosureRemains() throws IOException {
        // Arrange
        String expData = ">";
        String data;
        
        this.setState(DEFAULT_WITH_QUOTES + SELF_CLOSING);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_SingleAttribute_SelfClosing_LastElementIsClosingAttribute() throws IOException {
        // Arrange
        HtmlAttribute expData = DEFAULT_CLOSING_ATTR;
        HtmlAttribute data;
        
        this.setState(SELF_CLOSING);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(attributes.size() - 1);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultipleAttribute_StandardClose_FirstElement_AttributeWithNoValue() throws IOException {
        // Arrange
        HtmlAttribute expData = DEFAULT_EXP_ATTR;
        HtmlAttribute data;
        
        this.setState(DEFAULT_MULTIPLE + DEFAULT_CLOSE);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultipleAttribute_StandardClose_SecondElement_AttributeWithValue() throws IOException {
        // Arrange
        HtmlAttribute expData = DEFAULT_WITH_VALUE_EXP_ATTR;
        HtmlAttribute data;
        
        this.setState(DEFAULT_MULTIPLE + DEFAULT_CLOSE);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(1);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultipleAttribute_StandardClose_ThirdElement_AttributeWithQuotes() throws IOException {
        // Arrange
        HtmlAttribute expData = DEFAULT_WITH_QUOTES_EXP_ATTR;
        HtmlAttribute data;
        
        this.setState(DEFAULT_MULTIPLE + DEFAULT_CLOSE);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(2);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultipleAttribute_StandardClose_TagEnclosureRemains() throws IOException {
        // Arrange
        String expData = ">";
        String data;
        
        this.setState(DEFAULT_MULTIPLE + DEFAULT_CLOSE);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultipleAttribute_SelfClosing_FirstElement_AttributeWithNoValue() throws IOException {
        // Arrange
        HtmlAttribute expData = DEFAULT_EXP_ATTR;
        HtmlAttribute data;
        
        this.setState(DEFAULT_MULTIPLE + DEFAULT_CLOSE);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultipleAttribute_SelfClosing_SecondElement_AttributeWithValue() throws IOException {
        // Arrange
        HtmlAttribute expData = DEFAULT_WITH_VALUE_EXP_ATTR;
        HtmlAttribute data;
        
        this.setState(DEFAULT_MULTIPLE + SELF_CLOSING);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(1);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultipleAttribute_SelfClosing_ThirdElement_AttributeWithQuotes() throws IOException {
        //Arrange
        HtmlAttribute expData = DEFAULT_WITH_QUOTES_EXP_ATTR;
        HtmlAttribute data;
        
        this.setState(DEFAULT_MULTIPLE + SELF_CLOSING);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(2);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultipleAttribute_SelfClosing_ClosingElement() throws IOException {
        //Arrange
        HtmlAttribute expData = DEFAULT_CLOSING_ATTR;
        HtmlAttribute data;
        
        this.setState(DEFAULT_MULTIPLE + SELF_CLOSING);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.get(3);
        
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_MultipleAttribute_SelfClosing_TagEnclosureRemains() throws IOException {
        // Arrange
        String expData = ">";
        String data;
        
        this.setState(DEFAULT_MULTIPLE + SELF_CLOSING);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ScriptTag_IgnoresAttributes_NoDataPopulated() throws IOException {
        // Arrange
        int expData = 0;
        int data;
        
        this.setState("some data that represent scripting = data $ with @ some values present>");
        this.result.setName(MarkupTagNames.SCRIPT_TAG.toString());
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = attributes.size();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ScriptTag_IgnoresAttributes_TagEnclosureRemains() throws IOException {
        // Arrange
        String expData = ">";
        String data;
        
        this.setState("some data that represnts scripting = data $ with @ some values present>");
        this.result.setName(MarkupTagNames.SCRIPT_TAG.toString());
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_CommentTag_FormsAttributeWithInnerString_FirstAttributeNameIsString() throws IOException {
        // Arrange
        String expData = "some inner data in this comment --";
        String data;
        
        this.setState(expData + ">");
        this.result.setName(MarkupTagNames.COMMENT_TAG.toString());
        
        // Apply
        this.parser.parse(this.input, this.result);
        HtmlAttribute attribute = this.result.getAttributes().get(0);
        data = attribute.getName();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_CommentTag_FormsSingleAttributeWithInnerString_FirstAttributeIsFlagIsTrue() throws IOException {
        // Arrange
        String commentData = "some inner data in this comment --";
        
        boolean expData = false;
        boolean data;
        
        this.setState(commentData + ">");
        this.result.setName(MarkupTagNames.COMMENT_TAG.toString());
        
        // Apply
        this.parser.parse(this.input, this.result);
        HtmlAttribute attribute = this.result.getAttributes().get(0);
        data = attribute.isFlag();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_CommentTag_FormsSingleAttributeWithInnerString_AttributesListOnlyContainsSingleValue() throws IOException {
        // Arrange
        String commentData = "some inner data in this comment --";
        
        int expData = 1;
        int data;
        
        this.setState(commentData + ">");
        this.result.setName(MarkupTagNames.COMMENT_TAG.toString());
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.result.getAttributes().size();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EndOfInputParsingException.class)
    public void testParse_InvalidData_EndOfInput_EmptyData() throws IOException {
        // Arrange
        this.setState("");
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test(expected=EndOfInputParsingException.class)
    public void testParse_InvalidData_EndOfInput_AttributeWithNoValue() throws IOException {
        // Arrange
        this.setState(DEFAULT);
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test(expected=EndOfInputParsingException.class)
    public void testParse_InvalidData_EndOfInput_AttributeWithValue() throws IOException {
        // Arrange
        this.setState(DEFAULT_WITH_VALUE);
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test(expected=EndOfInputParsingException.class)
    public void testParse_InvalidData_EndOfInput_AttributeWithValueInQuotes() throws IOException {
        // Arrange
        this.setState(DEFAULT_WITH_QUOTES);
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test(expected=EndOfInputParsingException.class)
    public void testParse_InvalidData_EndOfInput_MultipleAttributes() throws IOException {
        // Arrange
        this.setState(DEFAULT_MULTIPLE);
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test(expected=UnclosedTagParsingException.class)
    public void testParse_InvalidData_OpeningTagWithNoCloseTagPrior_WithoutExtraData() throws IOException {
        // Arrange
        this.setState("<");
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test(expected=UnclosedTagParsingException.class)
    public void testParse_InvalidData_OpeningTagWithNoCloseTagPrior_WithExtraData_AttributeWithNoValue() throws IOException {
        // Arrange
        this.setState(DEFAULT + "<");
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test(expected=UnclosedTagParsingException.class)
    public void testParse_InvalidData_OpeningTagWithNoCloseTagPrior_WithExtraData_AttributeWithValue() throws IOException {
        // Arrange
        this.setState(DEFAULT_WITH_VALUE + "<");
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test(expected=UnclosedTagParsingException.class)
    public void testParse_InvalidData_OpeningTagWithNoCloseTagPrior_WithExtraData_AttributeWithValueInQuotes() throws IOException {
        // Arrange
        this.setState(DEFAULT_WITH_QUOTES + "<");
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test(expected=UnclosedTagParsingException.class)
    public void testParse_InvalidData_OpeningTagWithNoCloseTagPrior_WithExtraData_MultipleAttributes() throws IOException {
        // Arrange
        this.setState(DEFAULT_MULTIPLE + "<");
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test(expected=UnclosedTagParsingException.class)
    public void testParse_InvalidData_OpeningTagWithNoCloseTagPrior_ExtraDataRemainsInInput() throws IOException {
        // Arrange
        String expData = "<someTag someOtherData=\"should be untouched\">";
        String data;
        
        this.setState(expData);
        
        // Apply
        this.parser.parse(this.input, this.result);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    public void setState(String data) {
        this.input = new PushbackAndPositionReaderMock(data);
        this.result = new MutableHtmlData();
        this.attributes = this.result.getAttributes();
    }
    
    @After
    public void clearState() {
        this.input = null;
        this.result = null;
    }
}