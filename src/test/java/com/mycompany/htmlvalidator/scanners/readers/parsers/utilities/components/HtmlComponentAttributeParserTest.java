package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.IOException;

import org.junit.*;

import com.mycompany.htmlvalidator.exceptions.MarkupError;
import com.mycompany.htmlvalidator.scanners.MarkupTag;
import com.mycompany.htmlvalidator.scanners.readers.parsers.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.InvalidStateException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions.MissingCharacterComponentException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.*;

public class HtmlComponentAttributeParserTest extends HtmlComponentAttributeParser {
    private static final MarkupError SOME_ERROR = new MissingCharacterComponentException('O', new Point(123, 456), "some data");
    private static final char SOME_CHAR = 'X';
    
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

    @Override
    public HtmlAttribute parse(PushbackAndPositionReader input) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
    
}
