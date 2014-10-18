/*  This file is part of MarkupTagScanner.
 *
 *  MarkupTagScanner is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MarkupTagScanner is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MarkupTagScanner. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycompany.markuptagscanner.readers.parsers.subparsers;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

import com.mycompany.markuptagscanner.errors.*;
import com.mycompany.markuptagscanner.readers.parsers.tokens.HtmlData;
import org.junit.*;

import com.mycompany.markuptagscanner.enums.*;
import com.mycompany.markuptagscanner.readers.utilities.PushbackAndPositionReaderMock;

public class HtmlElementSubParserTest {
    private static final char CLOSING_TAG = MarkupTag.CLOSING_TAG.toChar();
    private static final char OPENING_TAG = MarkupTag.OPENING_TAG.toChar();
    private static final String COMMENT_OPENING = MarkupTagNames.COMMENT_TAG.getBeginName();
    private static final String CLOSING_ATTR = MarkupTag.CLOSING_ATTRIBUTE.toString();
    
    private static final String WHITESPACE_START_STR = " abcdefg";
    private static final String WHITESPACE_CENTER_STR = "abcd efg";
    private static final String WHITESPACE_END_STR = "abcdefg ";
    
    private HtmlElementSubParser parser = new HtmlElementSubParser();
    private PushbackAndPositionReaderMock input;
    private LinkedList<Character> inputData;
    private HtmlData result;
    
    @Test
    public void testParse_WithValidElementName_WhitespaceStartStr_ReturnsHtmlDataWithExpectedName() throws IOException {
        // Test
        this.testParse_ValidElement_ReturnedDataNameMatches(WHITESPACE_START_STR);
    }
    
    @Test
    public void testParse_WithValidElementName_WhitespaceStartStr_StoredHtmlDataMatchesExpected() throws IOException {
        // Test
        this.testParse_ValidElement_StoredDataNameMatches(WHITESPACE_START_STR);
    }
    
    @Test
    public void testParse_WithValidElementName_WhitespaceStartStr_RemainingDataMatchesExpected() throws IOException {
        // Test
        this.testParse_ValidElement_ConsumesElementNameFromInput(WHITESPACE_START_STR);
    }
    
    @Test
    public void testParse_WithValidElementName_WhitespaceCenterStr_ReturnsHtmlDataWithExpectedName() throws IOException {
        // Test
        this.testParse_ValidElement_ReturnedDataNameMatches(WHITESPACE_CENTER_STR);
    }
    
    @Test
    public void testParse_WithValidElementName_WhitespaceCenterStr_StoredHtmlDataMatchesExpected() throws IOException {
        // Test
        this.testParse_ValidElement_StoredDataNameMatches(WHITESPACE_CENTER_STR);
    }
    
    @Test
    public void testParse_WithValidElementName_WhitespaceCenterStr_RemainingDataMatchesExpected() throws IOException {
        // Test
        this.testParse_ValidElement_ConsumesElementNameFromInput(WHITESPACE_CENTER_STR);
    }
    
    @Test
    public void testParse_WithValidElementName_WhitespaceEndStr_ReturnsHtmlDataWithExpectedName() throws IOException {
        // Test
        this.testParse_ValidElement_ReturnedDataNameMatches(WHITESPACE_END_STR);
    }
    
    @Test
    public void testParse_WithValidElementName_WhitespaceEndStr_StoredHtmlDataMatchesExpected() throws IOException {
        // Test
        this.testParse_ValidElement_StoredDataNameMatches(WHITESPACE_END_STR);
    }
    
    @Test
    public void testParse_WithValidElementName_WhitespaceEndStr_RemainingDataMatchesExpected() throws IOException {
        // Test
        this.testParse_ValidElement_ConsumesElementNameFromInput(WHITESPACE_END_STR);
    }
    
    @Test
    public void testParse_WithValidElementName_CommentNameAtStartOfStr_ReturnsHtmlDataWithExpectedName() throws IOException {
        // Arrange
        String expData = COMMENT_OPENING;
        
        this.setState(expData + WHITESPACE_CENTER_STR);
        
        // Apply
        HtmlData htmlData = this.parser.parse(this.input, this.result);
        
        // Assert
        assertEquals(expData, htmlData.getName());
    }
    
    @Test
    public void testParse_WithValidElementName_CommentNameAtStartOfStr_StoredDataNameMatches() throws IOException {
        // Arrange
        String expData = COMMENT_OPENING;
        
        this.setState(expData + WHITESPACE_CENTER_STR);
        
        // Apply
        this.parser.parse(this.input, this.result);
        
        // Assert
        assertEquals(expData, this.result.getName());
    }
    
    @Test
    public void testParse_WithValidElementName_CommentNameAtStartOfStr_RemainingDataMatchesExpected() throws IOException {
        // Arrange
        String expData = WHITESPACE_CENTER_STR;
        
        this.setState(COMMENT_OPENING + WHITESPACE_CENTER_STR);
        
        // Apply
        this.parser.parse(this.input, this.result);
        
        // Assert
        assertEquals(expData, this.input.getRemainingData());
    }
    
    @Test
    public void testParse_WithValidElementName_CommentNameInCenterOfStr_ReturnsHtmlDataWithExpectedName() throws IOException {
        // Test
        this.testParse_ValidElement_ReturnedDataNameMatches("abcdefg" + COMMENT_OPENING + "hijlmnopqrstuvwxyz ");
        
    }
    
    @Test
    public void testParse_WithValidElementName_CommentNameInCenterOfStr_StoredDataNameMatches() throws IOException {
        // Test
        this.testParse_ValidElement_StoredDataNameMatches("abcdefg" + COMMENT_OPENING + "hijlmnopqrstuvwxyz ");
    }
    
    @Test
    public void testParse_WithValidElementName_CommentNameInCenterOfStr_RemainingDataMatchesExpected() throws IOException {
        
        // Test
        this.testParse_ValidElement_ConsumesElementNameFromInput("abcdefg" + COMMENT_OPENING + "hijlmnopqrstuvwxyz ");
    }

    @Test
    public void testParse_WithValidElementName_CommentNameAtEndOfStr_ReturnsHtmlDataWithExpectedName() throws IOException {
        // Test
        this.testParse_ValidElement_ReturnedDataNameMatches("abcdefghijlmnopqrstuvwxyz" + COMMENT_OPENING + " ");
        
    }
    
    @Test
    public void testParse_WithValidElementName_CommentNameAtEndOfStr_StoredDataNameMatches() throws IOException {
        // Test
        this.testParse_ValidElement_StoredDataNameMatches("abcdefghijlmnopqrstuvwxyz" + COMMENT_OPENING + " ");
    }
    
    @Test
    public void testParse_WithValidElementName_CommentNameAtEndOfStr_RemainingDataMatchesExpected() throws IOException {
        // Test
        this.testParse_ValidElement_ConsumesElementNameFromInput("abcdefghijlmnopqrstuvwxyz" + COMMENT_OPENING + " ");
    }
    
    @Test
    public void testParse_WithValidElementName_ClosingAttrAtEndOfStr_ReturnsHtmlDataWithExpectedName() throws IOException {
        // Arrange
        String expData = "abcdefghijklmnopqrstuvwxyz";
        this.setState(expData + CLOSING_ATTR);
        
        // Apply
        HtmlData htmlData = this.parser.parse(this.input, this.result);
        
        // Assert
        assertEquals(expData, htmlData.getName());
    }
    
    @Test
    public void testParse_WithValidElementName_ClosingAttrAtEndOfStr_StoredDataNameMatches() throws IOException {
        // Arrange
        String expData = "abcdefghijklmnopqrstuvwxyz";
        this.setState(expData + CLOSING_ATTR);
        
        // Apply
        this.parser.parse(this.input, this.result);
        
        // Assert
        assertEquals(expData, this.result.getName());
    }
    
    @Test
    public void testParse_WithValidElementName_ClosingAttrAtEndOfStr_RemainingDataMatchesExpected() throws IOException {
        // Arrange
        String expData = CLOSING_ATTR + " ";
        this.setState("abcdefghijklmnopqrstuvwyxz"+ expData);
        
        // Apply
        this.parser.parse(this.input, this.result);
        
        // Assert
        assertEquals(expData, this.input.getRemainingData());
    }
    
    private void testParse_ValidElement_ReturnedDataNameMatches(String str) throws IOException {
        // Arrange
        String expData = str.split(" ", 2)[0];
        
        this.setState(str);
        
        // Apply
        HtmlData htmlData = this.parser.parse(this.input, this.result);
        
        // Assert
        assertEquals(expData, htmlData.getName());
    }
    
    private void testParse_ValidElement_StoredDataNameMatches(String str) throws IOException {
        // Arrange
        String expData = str.split(" ", 2)[0];
        
        this.setState(str);
        
        // Apply
        this.parser.parse(this.input, this.result);
        
        // Assert
        assertEquals(expData, this.result.getName());
    }
    
    private void testParse_ValidElement_ConsumesElementNameFromInput(String str) throws IOException {
        // Arrange
        String expData = str.split(" ", 2)[1];
        
        this.setState(str);
        
        // Apply
        this.parser.parse(this.input, this.result);
        
        // Assert
        assertEquals(expData, this.input.getRemainingData());
    }
    
    @Test(expected=UnexpectedCloseTagParsingError.class)
    public void testParse_WithInvalidElementName_StrStartsWithClosingTag_ThrowsExpectedException() throws IOException {
        // Test
        this.testParse_InvalidElementName_UnexpectedTag_ThrowsException(WHITESPACE_START_STR, CLOSING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrStarsWithClosingTag_StoredResultMatchesExpected() throws IOException {
        // Test
        this.testParse_InvalidElement_UnexpectedTag_StoredHtmlDataMatchesExpected(WHITESPACE_START_STR, CLOSING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrStarsWithClosingTag_ExceptionDataMatchesStoredResult() throws IOException {
        // Test
        this.testParse_InvalidElement_UnexpectedTag_ExceptionFieldMatchesStoredData(WHITESPACE_START_STR, CLOSING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrStartsWithClosingTag_ConsumesOnlyExpectedInput() throws IOException {
        this.testParse_InvalidElement_UnexpectedTag_ConsumesOnlyExpectedInput_ErrorTagRemains(WHITESPACE_START_STR, CLOSING_TAG);
    }
    
    @Test(expected=UnexpectedCloseTagParsingError.class)
    public void testParse_WithInvalidElementName_StrCenterClosingTag_ThrowsExpectedException() throws IOException {
        // Test
        this.testParse_InvalidElementName_UnexpectedTag_ThrowsException(WHITESPACE_CENTER_STR, CLOSING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrCenterClosingTag_StoredResultMatchesExpected() throws IOException {
        // Test
        this.testParse_InvalidElement_UnexpectedTag_StoredHtmlDataMatchesExpected(WHITESPACE_CENTER_STR, CLOSING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrCenterClosingTag_ExceptionDataMatchesStoredResult() throws IOException {
        // Test
        this.testParse_InvalidElement_UnexpectedTag_ExceptionFieldMatchesStoredData(WHITESPACE_CENTER_STR, CLOSING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrCenterClosingTag_ConsumesOnlyExpectedInput() throws IOException {
        this.testParse_InvalidElement_UnexpectedTag_ConsumesOnlyExpectedInput_ErrorTagRemains(WHITESPACE_CENTER_STR, CLOSING_TAG);
    }
    
    @Test(expected=UnexpectedCloseTagParsingError.class)
    public void testParse_WithInvalidElementName_StrEndsWithClosingTag_ThrowsExpectedException() throws IOException {
        // Test
        this.testParse_InvalidElementName_UnexpectedTag_ThrowsException(WHITESPACE_END_STR, CLOSING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrEndsWithClosingTag_StoredResultMatchesExpected() throws IOException {
        // Test
        this.testParse_InvalidElement_UnexpectedTag_StoredHtmlDataMatchesExpected(WHITESPACE_END_STR, CLOSING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrEndsWithClosingTag_ExceptionDataMatchesStoredResult() throws IOException {
        // Test
        this.testParse_InvalidElement_UnexpectedTag_ExceptionFieldMatchesStoredData(WHITESPACE_END_STR, CLOSING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrEndsWithClosingTag_ConsumesOnlyExpectedInput() throws IOException {
        this.testParse_InvalidElement_UnexpectedTag_ConsumesOnlyExpectedInput_ErrorTagRemains(WHITESPACE_END_STR, CLOSING_TAG);
    }
    
    @Test(expected=UnclosedTagParsingError.class)
    public void testParse_WithInvalidElementName_StrStartsWithOpeningTag_ThrowsExpectedException() throws IOException {
        // Test
        this.testParse_InvalidElementName_UnexpectedTag_ThrowsException(WHITESPACE_START_STR, OPENING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrStarsWithOpeningTag_StoredResultMatchesExpected() throws IOException {
        // Test
        this.testParse_InvalidElement_UnexpectedTag_StoredHtmlDataMatchesExpected(WHITESPACE_START_STR, OPENING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrStarsWithOpeningTag_ExceptionDataMatchesStoredResult() throws IOException {
        // Test
        this.testParse_InvalidElement_UnexpectedTag_ExceptionFieldMatchesStoredData(WHITESPACE_START_STR, OPENING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrStartsWithOpeningTag_ConsumesOnlyExpectedInput() throws IOException {
        this.testParse_InvalidElement_UnexpectedTag_ConsumesOnlyExpectedInput_ErrorTagRemains(WHITESPACE_START_STR, OPENING_TAG);
    }
    
    @Test(expected=UnclosedTagParsingError.class)
    public void testParse_WithInvalidElementName_StrCenterOpeningTag_ThrowsExpectedException() throws IOException {
        // Test
        this.testParse_InvalidElementName_UnexpectedTag_ThrowsException(WHITESPACE_CENTER_STR, OPENING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrCenterOpeningTag_StoredResultMatchesExpected() throws IOException {
        // Test
        this.testParse_InvalidElement_UnexpectedTag_StoredHtmlDataMatchesExpected(WHITESPACE_CENTER_STR, OPENING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrCenterOpeningTag_ExceptionDataMatchesStoredResult() throws IOException {
        // Test
        this.testParse_InvalidElement_UnexpectedTag_ExceptionFieldMatchesStoredData(WHITESPACE_CENTER_STR, OPENING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrCenterOpeningTag_ConsumesOnlyExpectedInput() throws IOException {
        this.testParse_InvalidElement_UnexpectedTag_ConsumesOnlyExpectedInput_ErrorTagRemains(WHITESPACE_CENTER_STR, OPENING_TAG);
    }
    
    @Test(expected=UnclosedTagParsingError.class)
    public void testParse_WithInvalidElementName_StrEndsWithOpeningTag_ThrowsExpectedException() throws IOException {
        // Test
        this.testParse_InvalidElementName_UnexpectedTag_ThrowsException(WHITESPACE_END_STR, OPENING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrEndsWithOpeningTag_StoredResultMatchesExpected() throws IOException {
        // Test
        this.testParse_InvalidElement_UnexpectedTag_StoredHtmlDataMatchesExpected(WHITESPACE_END_STR, OPENING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrEndsWithOpeningTag_ExceptionDataMatchesStoredResult() throws IOException {
        // Test
        this.testParse_InvalidElement_UnexpectedTag_ExceptionFieldMatchesStoredData(WHITESPACE_END_STR, OPENING_TAG);
    }
    
    @Test
    public void testParse_WithInvalidElementName_StrEndsWithOpeningTag_ConsumesOnlyExpectedInput() throws IOException {
        this.testParse_InvalidElement_UnexpectedTag_ConsumesOnlyExpectedInput_ErrorTagRemains(WHITESPACE_END_STR, OPENING_TAG);
    }
    
    private void testParse_InvalidElementName_UnexpectedTag_ThrowsException(String str, char invalidChar) throws IOException {
        // Arrange
        String newStr = str.replace(' ', invalidChar);
        this.setState(newStr);
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    private void testParse_InvalidElement_UnexpectedTag_StoredHtmlDataMatchesExpected(String str, char invalidChar) throws IOException {
        // Arrange
        String expData = str.split(" ", 2)[0];
        String data = "";
        
        String newStr = str.replace(' ', invalidChar);
        this.setState(newStr);
        
        // Apply 
        try {
            this.parser.parse(this.input, this.result);
        } catch (ParsingError e) {
            data = this.result.getName();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    private void testParse_InvalidElement_UnexpectedTag_ExceptionFieldMatchesStoredData(String str, char invalidChar) throws IOException {
        // Arrange
        HtmlData data = null;
        
        String newStr = str.replace(' ', invalidChar);
        this.setState(newStr);
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (ParsingError e) {
            data = e.getHtmlData();
        }
        
        // Assert
        assertEquals(this.result, data);
    }
    
    private void testParse_InvalidElement_UnexpectedTag_ConsumesOnlyExpectedInput_ErrorTagRemains(String str, char invalidChar) throws IOException {
        // Arrange
        String expData = invalidChar + str.split(" ", 2)[1];
        
        this.setState(str.replace(' ', invalidChar));
        
        // Apply
        try {
            this.parser.parse(this.input, this.result);
        } catch (ParsingError e) {
        }
        
        // Assert
        assertEquals(expData, this.input.getRemainingData());
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testParse_WithInvalidInput_EmptyInput_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("");
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testParse_WithInvalidInput_NoWhitespaceInInput_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("abcdefghijklmnopqrstuvwxyz");
        
        // Apply + Assert
        this.parser.parse(this.input, this.result);
    }
    
    @Test(expected=InvalidStateException.class)
    public void testParse_WithNullInput_ThrowsExpectedException() throws IOException {
        // Test
        this.parser.parse(null, this.result);
    }
    
    @Test(expected=InvalidStateException.class)
    public void testParse_WithNullResult_ThrowsExpectedException() throws IOException {
        // Test
        this.parser.parse(this.input, null);
    }
    
    private void setState(String data) {
        this.inputData = new LinkedList<Character>();
        
        for (int i = 0; i < data.length(); i++)
            this.inputData.add(data.charAt(i));
        
        this.input = new PushbackAndPositionReaderMock(inputData);
        this.result = new HtmlData();
    }
    
    @After
    public void clearState() {
        this.inputData = null;
        this.input = null;
        this.result = null;
    }
}
