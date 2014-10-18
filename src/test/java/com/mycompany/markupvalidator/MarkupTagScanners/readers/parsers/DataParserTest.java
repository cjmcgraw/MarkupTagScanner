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
package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;

import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;
import com.mycompany.markupvalidator.errors.*;
import org.junit.*;

import com.mycompany.markupvalidator.MarkupTagScanners.enums.MarkupTag;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.utilities.*;

public class DataParserTest extends DataParser {
    private static final String DEFAULT_NAME = "someName";
    private static final char SOME_CHAR = 'X';
    
    private PushbackAndPositionReader inputData;
    private HtmlData resultData;
    
    @Before
    public void setUp() {
        this.inputData = new PushbackAndPositionReaderMock(new LinkedList<Character>());
        this.resultData = new HtmlData();
        this.resultData.setName(DEFAULT_NAME);
    }
    
    @Test
    public void testValidateChar_SomeChar_ResultIsTrue() throws IOException {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        data = this.validateChar(SOME_CHAR);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=InvalidStateException.class)
    public void testValidateChar_ClosingTag_InputStateUnset_ExpectedExceptionThrown() throws IOException {
        // Apply + Assert
        this.validateChar(MarkupTag.CLOSING_TAG.toChar());
    }
    
    @Test(expected=InvalidStateException.class)
    public void testValidateChar_ClosingTag_ResultStateUnset_ExpectedExceptionThrown() throws IOException {
        // Arrange
        this.input = this.inputData;
        
        // Apply + Assert
        this.validateChar(MarkupTag.CLOSING_TAG.toChar());
    }
    
    @Test(expected=UnexpectedCloseTagParsingError.class)
    public void testValidateChar_ClosingTag_ExpectedExceptionThrown() throws IOException {
        // Arrange
        this.input = this.inputData;
        this.result = this.resultData;
        
        // Apply + Assert
        this.validateChar(MarkupTag.CLOSING_TAG.toChar());
    }
    
    @Test
    public void testValidateChar_ClosingTag_ExceptionContainsMatchingResult() throws IOException {
        // Arrange
        HtmlData expData = this.resultData;
        HtmlData data = null;
        
        this.input = this.inputData;
        this.result = this.resultData;
        
        // Apply
        
        try {
            this.validateChar(MarkupTag.CLOSING_TAG.toChar());
        } catch (ParsingError e) {
            data = e.getHtmlData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=InvalidStateException.class)
    public void testValidateChar_OpeningTag_InputStateUnset_ExpectedExceptionThrown() throws IOException {
        // Apply + Assert
        this.validateChar(MarkupTag.OPENING_TAG.toChar());
    }
    
    @Test(expected=InvalidStateException.class)
    public void testValidateChar_OpeningTag_ResultStateUnset_ExpectedExceptionThrown() throws IOException {
        // Arrange
        this.input = this.inputData;
        
        // Apply + Assert
        this.validateChar(MarkupTag.OPENING_TAG.toChar());
    }
    
    @Test(expected=UnclosedTagParsingError.class)
    public void testValidateChar_OpeningTag_ExpectedExceptionThrown() throws IOException {
        // Arrange
        this.input = this.inputData;
        this.result = this.resultData;
        
        // Apply + Assert
        this.validateChar(MarkupTag.OPENING_TAG.toChar());
    }
    
    @Test
    public void testValidateChar_OpeningTag_ExceptionContainsMatchingResult() throws IOException {
        // Arrange
        HtmlData expData = this.resultData;
        HtmlData data = null;
        
        this.input = this.inputData;
        this.result = this.resultData;
        
        // Apply
        
        try {
            this.validateChar(MarkupTag.OPENING_TAG.toChar());
        } catch (ParsingError e) {
            data = e.getHtmlData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_ValidState_CharsMatch() throws IOException {
        // Arrange
        char expData = SOME_CHAR;
        char data;
        
        this.inputData.unread(SOME_CHAR);
        this.input = this.inputData;
        this.result = this.resultData;
        
        // Apply
        data = this.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testRead_ValidState_EmptyInput_EOFException_ThrowsExpectedException() throws IOException {
        // Arrange
        this.input = this.inputData;
        this.result = this.resultData;
        
        // Apply + Assert
        this.read();
    }
    
    @Test
    public void testRead_ValidState_EmptyInput_ExceptionContainsMatchingResult() throws IOException {
        // Arrange
        HtmlData expData = this.resultData;
        HtmlData data = null;
        
        this.result = this.resultData;
        this.input = this.inputData;
        
        // Apply
        try {
            this.read();
        } catch (ParsingError e) {
            data = e.getHtmlData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=InvalidStateException.class)
    public void testRead_InvalidStateUnsetResult_EmptyInput_ThrowsExpectedException() throws IOException {
        // Arrange
        this.input = this.inputData;
        
        // Apply + Assert
        this.read();
    }
    
    @Test
    public void testSetState_InputSetOnly_ContainsNewEmptyResult() {
        // Arrange
        HtmlData expData = new HtmlData();
        HtmlData data;
        
        // Apply
        this.setState(this.inputData);
        data = this.result;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetState_InputAndResult_ContainsMatchingResult() {
        // Arrange
        HtmlData expData = this.resultData;
        HtmlData data;
        
        // Apply
        this.setState(this.inputData, this.resultData);
        data = this.result;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testClearState_ResultIsNullAfterClearState() {
        // Arrange
        Object expData = null;
        HtmlData data;
        
        this.result = this.resultData;
        
        // Apply
        this.clearState();
        data = this.result;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testGetResult_ValidResult_ReturnedResultMatches() {
        // Arrange
        HtmlData expData = this.resultData;
        HtmlData data;
        
        this.result = expData;
        
        // Apply
        data = this.getResult();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=InvalidStateException.class)
    public void testGetResult_InvalidResult_ThrowsExpectedException() throws IOException {
        // Arrange
        this.result = null;
        
        // Apply + Assert
        this.getResult();
    }
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
    
}
