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
package com.mycompany.markuptagscanner.readers.parsers.subparsers.components;

import static org.junit.Assert.*;

import java.io.*;
import java.util.LinkedList;

import com.mycompany.markuptagscanner.errors.EndOfInputAttributeError;
import org.junit.*;

import com.mycompany.markuptagscanner.enums.*;
import com.mycompany.markuptagscanner.readers.parsers.tokens.HtmlAttribute;
import com.mycompany.markuptagscanner.readers.utilities.PushbackAndPositionReaderMock;

public class HtmlCommentAttributeParserTest {
    private static final String COMMENT_CLOSE = MarkupTagNames.COMMENT_TAG.getEndName();
    private static final String DEFAULT_DATA = "some comment data on a single line";
    private static final String MULTI_LINE_DATA = String.format("%n%n%n" + DEFAULT_DATA + "%n%n%n");
    private static final String CLOSING = MarkupTagNames.COMMENT_TAG.getEndName() + MarkupTag.CLOSING_TAG;
    
    private static final String MISSING_FINAL_CLOSING = COMMENT_CLOSE.substring(0, COMMENT_CLOSE.length() - 1) + MarkupTag.CLOSING_TAG;
    private static final String MISSING_FIRST_CLOSING = COMMENT_CLOSE.substring(1) + MarkupTag.CLOSING_TAG;
  
    
    private final HtmlCommentAttributeParser parser = new HtmlCommentAttributeParser();
    private PushbackAndPositionReaderMock input;
    
    @Test
    public void testParse_ValidHtmlComment_SingleLine_AttributeContainsMatchingData() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute(DEFAULT_DATA);
        HtmlAttribute data;
        
        this.setState(DEFAULT_DATA + CLOSING);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ValidHtmlComment_SingleLine_RemainingDataContainsOnlyClosingTag() throws IOException {
        // Arrange
        String expData = "" + MarkupTag.CLOSING_TAG;
        String data;
        
        this.setState(DEFAULT_DATA + CLOSING);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ValidHtmlComment_MultiLine_AttributeContainsMatchingData() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute(MULTI_LINE_DATA);
        HtmlAttribute data;
        
        this.setState(MULTI_LINE_DATA + CLOSING);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ValidHtmlComment_MultiLine_RemainingDataContainsOnlyClosingTag() throws IOException {
        // Arrange
        String expData = "" + MarkupTag.CLOSING_TAG;
        String data;
        
        this.setState(MULTI_LINE_DATA + CLOSING);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ValidHtmlComment_ContainsOpeningHtmlTag() throws IOException {
        // Arrange
        String state = MarkupTag.OPENING_TAG + DEFAULT_DATA;
        
        HtmlAttribute expData = new HtmlAttribute(state);
        HtmlAttribute data;
        
        this.setState(state + CLOSING);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ValidHtmlComment_ContainsOpeningHtmlTag_RemainingDataContainsOnlyClosingTag() throws IOException {
        // Arrange
        String expData = "" + MarkupTag.CLOSING_TAG;
        String data;
        
        this.setState(MarkupTag.OPENING_TAG + DEFAULT_DATA + CLOSING);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ValidHtmlComment_ContainsClosingHtmlTag() throws IOException {
        // Arrange
        String state = DEFAULT_DATA + MarkupTag.CLOSING_TAG;
        
        HtmlAttribute expData = new HtmlAttribute(state);
        HtmlAttribute data;
        
        this.setState(state + CLOSING);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ValidHtmlComment_ContainsClosingHtmlTag_RemainingDataContainsOnlyClosingTag() throws IOException {
        // Arrange
        String expData = "" + MarkupTag.CLOSING_TAG;
        String data;
        
        this.setState(DEFAULT_DATA + MarkupTag.CLOSING_TAG + CLOSING);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EndOfInputAttributeError.class)
    public void testParse_InvalidHtmlComment_SingleLine_MissingFirstCharOfClosingCommentTag() throws IOException {
        // Arrange
        this.setState(DEFAULT_DATA + MISSING_FIRST_CLOSING);
        
        // Apply + Assert
        this.parser.parse(this.input);
    }
    
    @Test
    public void testParse_InvalidHtmlComment_SingleLine_MissingFirstCharOfClosingCommentTag_ExceptionDataMatchesExpected() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute(DEFAULT_DATA + MISSING_FIRST_CLOSING);
        HtmlAttribute data = null;
        
        this.setState(DEFAULT_DATA + MISSING_FIRST_CLOSING);
        
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
    public void testParse_InvalidHtmlComment_SingleLine_MissingFirstCharOfClosingCommentTag_NoDataRemaining() throws IOException {
        // Arrange
        String expData = "";
        String data = "to prevent false positive";
        
        this.setState(DEFAULT_DATA + MISSING_FIRST_CLOSING);
        
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
    public void testParse_InvalidHtmlComment_MultiLine_MissingFirstCharOfClosingCommentTag() throws IOException {
        // Arrange
        this.setState(MULTI_LINE_DATA + MISSING_FIRST_CLOSING);
        
        // Apply + Assert
        this.parser.parse(this.input);
    }
    
    @Test
    public void testParse_InvalidHtmlComment_MultiLine_MissingFirstCharOfClosingCommentTag_ExceptionDataMatchesExpected() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute(MULTI_LINE_DATA + MISSING_FIRST_CLOSING);
        HtmlAttribute data = null;
        
        this.setState(MULTI_LINE_DATA + MISSING_FIRST_CLOSING);
        
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
    public void testParse_InvalidHtmlComment_MultiLine_MissingFirstCharOfClosingCommentTag_NoDataRemaining() throws IOException {
        // Arrange
        String expData = "";
        String data = "to prevent false positive";
        
        this.setState(MULTI_LINE_DATA + MISSING_FIRST_CLOSING);
        
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
    public void testParse_InvalidHtmlComment_SingleLine_MissingFinalCharOfClosingCommentTag() throws IOException {
        // Arrange
        this.setState(DEFAULT_DATA + MISSING_FINAL_CLOSING);
        
        // Apply
        this.parser.parse(this.input);
    }
    
    @Test
    public void testParse_InvalidHtmlComment_SingleLine_MissingFinalCharOfClosingCommentTag_ExceptionDataMatcheExpected() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute(DEFAULT_DATA + MISSING_FINAL_CLOSING);
        HtmlAttribute data = null;
        
        this.setState(DEFAULT_DATA + MISSING_FINAL_CLOSING);
        
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
    public void testParse_InvalidHtmlComment_SingleLine_MissingFinalCharOfClosingCommentTag_NoRemainingData() throws IOException {
        // Arrange
        String expData = "";
        String data = "to prevent false positive";
        
        this.setState(DEFAULT_DATA + MISSING_FINAL_CLOSING);
        
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
    public void testParse_InvalidHtmlComment_MultiLine_MissingFinalCharOfClosingCommentTag() throws IOException {
        // Arrange
        this.setState(MULTI_LINE_DATA + MISSING_FINAL_CLOSING);
        
        // Apply + Assert
        this.parser.parse(this.input);
    }
    
    @Test
    public void testParse_InvalidHtmlComment_MultiLine_MissingFinalCharOfClosingCommentTag_ExceptionDataMatchesExpected() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute(MULTI_LINE_DATA + MISSING_FINAL_CLOSING);
        HtmlAttribute data = null;
        
        this.setState(MULTI_LINE_DATA + MISSING_FINAL_CLOSING);
        
        // Apply
        try {
            this.parser.parse(this.input);
        } catch (EndOfInputAttributeError e) {
            data = e.getAttribute();
        }
        
        //  Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_InvalidHtmlComment_MultiLine_MissingFinalCharOfClosingCommentTag_NoRemainingData() throws IOException {
        // Arrange
        String expData = "";
        String data = "to prevent false positive";
        
        this.setState(MULTI_LINE_DATA + MISSING_FINAL_CLOSING);
        
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
    public void testParse_InvalidHtmlComment_MissingClosingTag() throws IOException {
        // Arrange
        this.setState(DEFAULT_DATA + MarkupTagNames.COMMENT_TAG.getEndName());
        
        // Apply + Assert
        this.parser.parse(this.input);
    }
    
    @Test
    public void testParse_InvalidHtmlComment_MissingClosingTag_ExceptionDataMatchesExpected() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute(DEFAULT_DATA + MarkupTagNames.COMMENT_TAG.getEndName());
        HtmlAttribute data = null;
        
        this.setState(DEFAULT_DATA + MarkupTagNames.COMMENT_TAG.getEndName());
        
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
    public void testParse_InvalidHtmlComment_MissingClosingTag_NoRemainingData() throws IOException {
        // Arrange
        String expData = "";
        String data = "to prevent false positive";
        
        this.setState(DEFAULT_DATA + MarkupTagNames.COMMENT_TAG.getEndName());
        
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
    public void testParse_InvalidHtmlComment_EOFBeforeClosingTag() throws IOException {
        // Arrange
        this.setState(DEFAULT_DATA);
        
        // Apply + Assert
        this.parser.parse(this.input);
    }
    
    @Test
    public void testParse_InvalidHtmlComment_EOFBeforeClosingTag_ExceptionDataMatchesExpected() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute(DEFAULT_DATA);
        HtmlAttribute data = null;
        
        this.setState(DEFAULT_DATA);
        
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
    public void testParse_InvalidHtmlComment_EOFBeforeClosingTag_NoRemainingData() throws IOException {
        // Arrange
        String expData = "";
        String data = "to prevent false positive";
        
        this.setState(DEFAULT_DATA);
        
        // Apply
        try {
            this.parser.parse(this.input);
        } catch (EndOfInputAttributeError e) {
            data = this.input.getRemainingData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    private void setState(String data) {
        LinkedList<Character> inputData = new LinkedList<>();
        
        for (int i = 0; i < data.length(); i++) 
            inputData.add(data.charAt(i));
        
        this.input = new PushbackAndPositionReaderMock(inputData);
    }
    
    @After
    public void clearState() {
        this.input = null;
    }
}
