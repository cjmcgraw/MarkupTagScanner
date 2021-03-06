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

import java.io.IOException;
import java.util.*;

import com.mycompany.markuptagscanner.errors.*;
import org.junit.*;

import com.mycompany.markuptagscanner.enums.EnclosureTags;
import com.mycompany.markuptagscanner.readers.utilities.*;

public class HtmlComponentEnclosureParserTest extends HtmlComponentEnclosureParser {
    private static final char SOME_CHAR = 'X';
    private static final String DEFAULT_DATA = "some default data";
    private static final Set<Character> VALID_OPENING_ENCLOSURES = generateValidEnclosures();
    
    private static Set<Character> generateValidEnclosures() {
        Set<Character> result = new HashSet<>();
        
        result.add(EnclosureTags.DOUBLE_QUOTE_ENCLOSURE.getOpening().toChar());
        result.add(EnclosureTags.HTML_ENCLOSURE.getOpening().toChar());
        
        return result;
    }
    
    @Test
    public void testSetAndValidateOpening_HtmlEnclosure_SetToValidOpening() throws IOException {
        // Arrange
        EnclosureTags expData = EnclosureTags.HTML_ENCLOSURE;
        EnclosureTags data;
        
        // Apply
        this.setAndValidateOpening(expData.getOpening().toChar());
        data = this.enclosure;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetAndValidateOpening_DoubleQuoteEnclosure_SetToValidOpening_ResultMatches() throws IOException {
        // Arrange
        EnclosureTags expData = EnclosureTags.DOUBLE_QUOTE_ENCLOSURE;
        EnclosureTags data;
        
        // Apply
        this.setAndValidateOpening(expData.getOpening().toChar());
        data = this.enclosure;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=MissingCharacterComponentError.class)
    public void testSetAndValidateOpening_SingleQuoteEnclosure_SetToInvalidOpening_ThrowsExpectedException() throws IOException {
        // Arrange
        EnclosureTags expData = EnclosureTags.SINGLE_QUOTE_ENCLOSURE;
        
        // Apply + Assert
        this.setAndValidateOpening(expData.getOpening().toChar());
    }
    
    @Test(expected=MissingCharacterComponentError.class)
    public void testSetAndValidateOpening_SomeChar_SetToInvalidOpening_ThrowsExpectedException() throws IOException {
        this.setAndValidateOpening(SOME_CHAR);
    }
    
    @Test
    public void testValidateClosing_WithValidEnclosure_DoubleQuote_ValidClosing_ResultIsTrue() throws IOException {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.enclosure = EnclosureTags.DOUBLE_QUOTE_ENCLOSURE;
        char currClosing = this.enclosure.getClosing().toChar();
        
        // Apply
        data = this.validateClosing(currClosing);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testValidateClosing_WithValidEnclosure_HtmlEnclosure_ValidClosing_ResultIsTrue() throws IOException {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.enclosure = EnclosureTags.HTML_ENCLOSURE;
        char currClosing = this.enclosure.getClosing().toChar();
        
        // Apply
        data = this.validateClosing(currClosing);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=MissingCharacterComponentError.class)
    public void testValidateClosing_WithValidEnclosure_InvalidClosing_ResultIsException() throws IOException {
        // Arrange
        this.enclosure = EnclosureTags.DOUBLE_QUOTE_ENCLOSURE;
        
        // Apply + Assert
        this.validateClosing(SOME_CHAR);
    }
    
    @Test(expected=InvalidStateException.class)
    public void testValidateClosing_WithInvalidEnclosure_InvalidClosing_ResultIsException() throws IOException {
        this.validateClosing(SOME_CHAR);
    }
    
    @Test
    public void testIsClosing_WithValidEnclosure_ValidClosing_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.enclosure = EnclosureTags.HTML_ENCLOSURE;
        char currClosing = this.enclosure.getClosing().toChar();
        
        // Apply
        data = this.isClosing(currClosing);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsClosing_WithValidEnclosure_InValidClosingAsEnclosure_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.enclosure = EnclosureTags.HTML_ENCLOSURE;
        char currClosing = EnclosureTags.DOUBLE_QUOTE_ENCLOSURE.getClosing().toChar();
        
        // Apply
        data = this.isClosing(currClosing);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsClosing_WithValidEnclosure_InValidClosingSomeChar_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.enclosure = EnclosureTags.HTML_ENCLOSURE;
        
        // Apply
        data = this.isClosing(SOME_CHAR);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=InvalidStateException.class)
    public void testIsClosing_WithInvalidEnclosure_ResultIsException() {
        this.isClosing(SOME_CHAR);
    }
    
    @Test
    public void testIsOpening_AllValidEnclosures_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data = true;
        
        // Apply
        for (char ch : VALID_OPENING_ENCLOSURES)
            data = data && this.isOpening(ch);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsOpening_InValidEnclosureTagCharacter_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.isOpening(EnclosureTags.SINGLE_QUOTE_ENCLOSURE.getOpening().toChar());
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsOpening_InvalidTag_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.isOpening(SOME_CHAR);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_ValidData() throws IOException {
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
    public void testRead_ValidData_InputContainsExpectedRemainingData() throws IOException {
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
    
    @Test(expected=EndOfInputComponentError.class)
    public void testRead_InvalidData() throws IOException {
        // Arrange
        this.setState(new PushbackAndPositionReaderMock(""));
        
        // Apply + Assert
        this.read();
    }
    
    @Test
    public void testRead_InvalidData_ExceptionStoredDataMatchesExpected() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA;
        String data = "";
        
        this.setState(new PushbackAndPositionReaderMock(""));
        
        // Apply
        try {
            this.read();
        } catch (EndOfInputComponentError e) {
            data = e.getData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_InvalidData_EmptyInputRemainingAfterException() throws IOException {
        // Arrange
        String expData = "";
        String data = "data to prevent false positive";
        
        PushbackAndPositionReaderMock input = new PushbackAndPositionReaderMock("");
        
        this.setState(input);
        
        // Apply
        try {
            this.read();
        } catch (EndOfInputComponentError e) {
            data = input.getRemainingData();
        }
        
        // Assert
        assertEquals(expData, data);
    }

    @Override
    protected String getData() {
        return DEFAULT_DATA;
    }

    @Override
    protected Collection<EnclosureTags> getValidEnclosures() {
        List<EnclosureTags> result = new ArrayList<EnclosureTags>();
        
        result.add(EnclosureTags.DOUBLE_QUOTE_ENCLOSURE);
        result.add(EnclosureTags.HTML_ENCLOSURE);
        
        return result;
    }

    @Override
    public String parse(PushbackAndPositionReader input) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Before
    public void setState() {
        PushbackAndPositionReader input = new PushbackAndPositionReaderMock(new LinkedList<Character>());
        super.setState(input);
    }
    
    @After
    public void clearState() {
        super.clearState();
    }
}
