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
import java.io.IOException;

import com.mycompany.markupvalidator.MarkupTagScanners.errors.*;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlAttribute;
import org.junit.*;

import com.mycompany.markupvalidator.MarkupTagScanners.enums.MarkupTag;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.*;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.utilities.*;

public class HtmlComponentAttributeParserTest extends HtmlComponentAttributeParser {
    private static final MarkupError SOME_ERROR = new MissingCharacterComponentError('O', new Point(123, 456), "some data");
    private static final char SOME_CHAR = 'X';
    
    private static final String DEFAULT_NAME = "some default attribute name";
    private static final String DEFAULT_VALUE = "some default attribute value";
    
    private HtmlAttributeMock mockAttr;
    
    @Before
    public void setState() {
        this.mockAttr = new HtmlAttributeMock();
        this.setState(null);
        this.attribute = this.mockAttr;
    }
    
    @Test
    public void testIsValueSeparator_GivenValueSeparator_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        data = this.isValueSeparator(MarkupTag.ATTRIBUTE_VALUE_SEPARATOR.toChar());
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsValueSeparator_NonValueSeparator_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.isValueSeparator(SOME_CHAR);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsValueSeparator_UnaffectedByEmptyAttribute_GivenValueSeparator_ResultIsTrue() {
     // Arrange
        boolean expData = true;
        boolean data;
        
        this.attribute = null;
        
        // Apply
        data = this.isValueSeparator(MarkupTag.ATTRIBUTE_VALUE_SEPARATOR.toChar());
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testAddError_MockAttributeAddErrorCalled_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        this.addError(SOME_ERROR);
        data = this.mockAttr.AddErrorCalled();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testAddError_MockAttributeContainsExpectedError_ResultMatches() {
        // Arrange
        MarkupError expData = SOME_ERROR;
        MarkupError data;
        
        // Apply
        this.addError(SOME_ERROR);
        data = this.mockAttr.getAddedError();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=InvalidStateException.class)
    public void testAddError_MissingAttribute_ThrowsExpectedException() {
        // Arrange
        this.attribute = null;
        
        // Apply + Assert
        this.addError(SOME_ERROR);
    }
    
    @Test
    public void testRead_ValidInput() throws IOException {
        // Arrange
        char expData = 'x';
        char data;
        
        this.setState(new PushbackAndPositionReaderMock("xyz"));
        
        // Apply
        data = this.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_ValidInput_ExpectedDataRemainsInInput() throws IOException {
        // Arrange
        String expData = "yz";
        String data;
        
        PushbackAndPositionReaderMock input = new PushbackAndPositionReaderMock("xyz");
        
        this.setState(input);
        
        // Apply
        this.read();
        data = input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EndOfInputAttributeError.class)
    public void testRead_WithEOFInput() throws IOException {
        // Arrange
        this.setState(new PushbackAndPositionReaderMock(""));
        
        // Apply + Assert
        this.read();
    }
    
    @Test
    public void testRead_WithEOFInput_StoredExceptionDataMatches() throws IOException {
        // Arrange
        HtmlAttribute expData = new HtmlAttribute(DEFAULT_NAME, DEFAULT_VALUE);
        HtmlAttribute data = null;
        
        this.setState(new PushbackAndPositionReaderMock(""));
        
        // Apply
        try {
            this.read();
        } catch (EndOfInputAttributeError e) {
            data = e.getAttribute();
        }
        
        // Assert
        assertEquals(expData, data);
    }

    @Override
    public HtmlAttribute parse(PushbackAndPositionReader input) throws IOException {
        return null;
    }
    

    @Override
    protected String getAttributeName() {
        return DEFAULT_NAME;
    }

    @Override
    protected String getAttributeValue() {
        return DEFAULT_VALUE;
    }
    
}
