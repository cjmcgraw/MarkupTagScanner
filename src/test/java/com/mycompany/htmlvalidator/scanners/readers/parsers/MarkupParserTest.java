package com.mycompany.htmlvalidator.scanners.readers.parsers;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.mycompany.htmlvalidator.scanners.MarkupTag;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class MarkupParserTest extends MarkupParser<Boolean> {
    private static final char CLOSING_TAG = MarkupTag.CLOSING_TAG.toChar();
    private static final char OPENING_TAG = MarkupTag.OPENING_TAG.toChar();
    private static final char CLOSING_ATTR = MarkupTag.CLOSING_ATTRIBUTE.toChar();
    private static final char DOUBLE_QUOTE = MarkupTag.DOUBLE_QUOTE.toChar();
    private static final char SINGLE_QUOTE = MarkupTag.SINGLE_QUOTE.toChar();
    
    private static final char SOME_CHAR = 'X';
    
    @Test
    public void testIsClosingTag_NonClosingTag_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.isClosingTag(SOME_CHAR);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsClosingTag_OtherMarkupTag_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.isClosingTag(CLOSING_ATTR);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsClosingTag_OpeningTag_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.isClosingTag(OPENING_TAG);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsClosingTag_ClosingTag_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        data = this.isClosingTag(CLOSING_TAG);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsOpeningTag_NonOpeningTag_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.isOpeningTag(SOME_CHAR);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsOpeninTag_OtherMarkupTag_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.isOpeningTag(CLOSING_ATTR);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsOpeningTag_ClosingTag_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.isOpeningTag(CLOSING_TAG);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsOpeningTag_OpeningTag_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        data = this.isOpeningTag(OPENING_TAG);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsClosingAttribute_NonClosingAttribute_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.isClosingAttribute(SOME_CHAR);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsClosingAttribute_OtherMarkupTag_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.isClosingAttribute(SINGLE_QUOTE);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsClosingAttribute_ClosingAttribute_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        data = this.isClosingAttribute(CLOSING_ATTR);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsQuoteEnclosure_NonQuoteEnclosure_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.isQuoteEnclosure(SOME_CHAR);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsQuoteEnclosure_OtherMarkupTag_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.isQuoteEnclosure(CLOSING_ATTR);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsQuoteEnclosure_SingleQuote_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        data = this.isQuoteEnclosure(SINGLE_QUOTE);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsQuoteEnclosure_DoubleQuote_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        data = this.isQuoteEnclosure(DOUBLE_QUOTE);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsTagEnclosure_NonTagEnclosure_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.isTagEnclosure(SOME_CHAR);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsTagEnclosure_OtherMarkupTag_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.isTagEnclosure(CLOSING_ATTR);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsTagEnclosure_OpeningTag_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        data = this.isTagEnclosure(OPENING_TAG);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsTagEnclosure_ClosingTag_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        data = this.isTagEnclosure(CLOSING_TAG);
        
        // Assert
        assertEquals(expData, data);
    }

    @Override
    public Boolean parse(PushbackAndPositionReader input) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
}