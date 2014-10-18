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
package com.mycompany.markuptagscanner.readers.parsers;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.*;
import java.util.LinkedList;

import org.junit.*;

import com.mycompany.markuptagscanner.errors.InvalidStateException;
import com.mycompany.markuptagscanner.readers.utilities.*;

public class InputParserTest extends InputParser<Boolean>{
    private static final String DEFAULT_DATA = "the quick brown fox jumped over the lazy dog";
    private static final int FIRST_SECTION_START = 0;
    private static final int SECOND_SECTION_START = 15;
    private static final int THIRD_SECTION_START = 31;
    private static final char EMPTY_CHAR = Character.UNASSIGNED;
    private static final char SOME_CHAR = 'X';
    
    
    private PushbackAndPositionReaderMock inputData;
    
    @Before
    public void setUp() {
        LinkedList<Character> inputData = this.generateInputData(DEFAULT_DATA);
        this.inputData = new PushbackAndPositionReaderMock(inputData);
    }
    
    private LinkedList<Character> generateInputData(String data) {
        LinkedList<Character> result = new LinkedList<>();
        
        for(int i = 0; i < data.length(); i++) {
            result.add(data.charAt(i));
        }
        
        return result;
    }
    
    @Test
    public void testSetState_WithNonEmptyData_StoredDataMatches() throws IOException {
        // Arrange
        PushbackAndPositionReader expData = this.inputData;
        PushbackAndPositionReader data;
        
        // Apply
        this.setState(expData);
        data = this.inputData;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetState_WitNullData_StoredDatamatches() throws IOException {
        // Arrange
        Object expData = null;
        PushbackAndPositionReader data;
        
        // Apply
        this.setState(null);
        data = this.input;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testClearState_WithNullState_StoredDataIsNull() {
        // Arrange
        Object expData = null;
        PushbackAndPositionReader data;
        
        this.setState(null);
        
        // Apply
        this.tearDown();
        data = this.input;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testClearState_WithNonEmptyState_StoredDataIsNull() {
        // Arrange
        Object expData = null;
        PushbackAndPositionReader data;
        
        this.setState(this.inputData);
        
        // Apply
        this.tearDown();
        data = this.inputData;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_FirstRead_WithDefaultDataInput_FirstValueMatches() throws IOException {
        // Arrange
        char expData = DEFAULT_DATA.charAt(0);
        char data;
        
        this.setState(this.inputData);
        
        // Apply
        data = this.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_FirstRead_WithDefaultDataInput_FirstValueMatchesCurrChar() throws IOException {
        // Arrange
        char expData = DEFAULT_DATA.charAt(0);
        char data;
        
        this.setState(this.inputData);
        this.read();
        
        // Apply
        data = this.currChar;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_FirstRead_WithDefaultDataInput_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA.substring(1);
        String data;
        
        this.setState(this.inputData);
        
        // Apply
        this.read();
        data = this.inputData.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_SecondRead_WithDefaultDataInput_SecondValueMatches() throws IOException {
        // Arrange
        char expData = DEFAULT_DATA.charAt(1);
        char data;
        
        this.setState(this.inputData);
        
        this.read();
        
        // Apply
        data = this.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_SecondRead_WithDefaultDataInput_SecondValueMatchesCurrChar() throws IOException {
        // Arrange
        char expData = DEFAULT_DATA.charAt(1);
        char data;
        
        this.setState(this.inputData);
        
        this.read();
        this.read();
        
        // Apply
        data = this.currChar;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_SecondRead_WithDefaultDataInput_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA.substring(2);
        String data;
        
        this.setState(this.inputData);
        
        // Apply
        this.read();
        this.read();
        data = this.inputData.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_ThirdRead_WithDefaultDataInput_ThirdValueMatches() throws IOException {
        // Arrange
        char expData = DEFAULT_DATA.charAt(2);
        char data;
        
        this.setState(this.inputData);
        
        this.read();
        this.read();
        
        // Apply
        data = this.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_ThirdRead_WithDefaultDataInput_ThirdValueMatchesCurrChar() throws IOException {
        // Arrange
        char expData = DEFAULT_DATA.charAt(2);
        char data;
        
        this.setState(this.inputData);
        
        this.read();
        this.read();
        this.read();
        
        // Apply
        data = this.currChar;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_ThirdRead_WithDefaultDataInput_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA.substring(3);
        String data;
        
        this.setState(this.inputData);
        
        // Apply
        this.read();
        this.read();
        this.read();
        data = this.inputData.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_MultiRead_WithDefaultDataInput_FirstSectionMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA.substring(FIRST_SECTION_START, SECOND_SECTION_START);
        String data;
        
        this.setState(this.inputData);
        
        // Apply
        data = this.getWordBetweenIndices(FIRST_SECTION_START, SECOND_SECTION_START);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_MultiRead_WithDefaultDataInput_FirstSection_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA.substring(SECOND_SECTION_START);
        String data;
        
        this.setState(this.inputData);
        
        // Apply
        this.getWordBetweenIndices(FIRST_SECTION_START, SECOND_SECTION_START);
        data = this.inputData.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_MultiRead_WithDefaultDataInput_SecondSectionMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA.substring(SECOND_SECTION_START, THIRD_SECTION_START);
        String data;
        
        this.setState(this.inputData);
        
        // Apply
        data = this.getWordBetweenIndices(SECOND_SECTION_START, THIRD_SECTION_START);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_MultiRead_WithDefaultDataInput_SecondSection_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA.substring(THIRD_SECTION_START);
        String data;
        
        this.setState(this.inputData);
        
        // Apply
        this.getWordBetweenIndices(SECOND_SECTION_START, THIRD_SECTION_START);
        data = this.inputData.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_MultiRead_WithDefaultDataInput_ThirdSectionMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA.substring(THIRD_SECTION_START, DEFAULT_DATA.length());
        String data;
        
        this.setState(this.inputData);
        
        // Apply
        data = this.getWordBetweenIndices(THIRD_SECTION_START, DEFAULT_DATA.length());
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_MultiRead_WithDefaultDataInput_ThirdSection_RemainingDataMatches() throws IOException {
        // Arrange
        String expData = "";
        String data;
        
        this.setState(this.inputData);
        
        // Apply
        this.getWordBetweenIndices(THIRD_SECTION_START, DEFAULT_DATA.length());
        data = this.inputData.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EOFException.class)
    public void testRead_EmptyInput_ThrowsEOFException() throws IOException {
        // Arrange
        this.setState(new PushbackAndPositionReaderMock(new LinkedList<Character>()));
        
        // Apply + Assert
        this.read();
    }
    
    @Test(expected=EOFException.class)
    public void testRead_EndOfInput_ThrowsEOFexception() throws IOException {
        // Arrange
        this.setState(this.inputData);
        
        this.getWordBetweenIndices(DEFAULT_DATA.length(), DEFAULT_DATA.length());
        
        // Apply + Assert
        this.read();
    }
    
    @Test
    public void testPeekNextRead_FirstPeek_WithDefaultDataInput_FirstValueMatches() throws IOException {
        // Arrange
        char expData = DEFAULT_DATA.charAt(0);
        char data;
        
        this.setState(this.inputData);
        
        // Apply
        data = this.peekNextRead();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPeekNextRead_FirstPeek_WithDefaultDataInput_CurrCharIsEmpty() throws IOException {
        // Arrange
        char expData = EMPTY_CHAR;
        char data;
        
        this.setState(this.inputData);
        
        // Apply
        this.peekNextRead();
        data = this.currChar;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPeekNextRead_SecondPeek_WithDefaultDataInput_FirstValueMatches() throws IOException {
        // Arrange
        char expData = DEFAULT_DATA.charAt(0);
        char data;
        
        this.setState(this.inputData);
        
        // Apply
        this.peekNextRead();
        data = this.peekNextRead();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testPeekNextRead_SecondPeek_WithDefaultDataInput_CurrCharIsEmpty() throws IOException {
        // Arrange
        char expData = EMPTY_CHAR;
        char data;
        
        this.setState(this.inputData);
        
        // Apply
        this.peekNextRead();
        this.peekNextRead();
        
        data = this.currChar;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EOFException.class)
    public void testPeekNextRead_EmptyInput_ThrowsEOFException() throws IOException {
        // Arrange
        this.setState(new PushbackAndPositionReaderMock(new LinkedList<Character>()));
        
        // Apply + Assert
        this.peekNextRead();
    }
    
    @Test
    public void testUnread_GivenChar_EmptyInput_InputContainsUnread() throws IOException {
        // Arrange
        String expData = "" + SOME_CHAR;
        String data;
        
        this.inputData = this.getEmptyReader();
        this.setState(this.inputData);
        
        // Apply
        this.unread(SOME_CHAR);
        data = this.inputData.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testUnread_GivenChar_WithDefaultDataInput_FirstIndexIsUnread() throws IOException {
        // Arrange
        char expData = SOME_CHAR;
        char data;
        
        this.setState(this.inputData);
        
        // Apply
        this.unread(expData);
        data = this.inputData.getRemainingData().charAt(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testUnread_CurrChar_WithDefaultDataInput_FirstIndexIsUnread() throws IOException {
        // Arrange
        char expData = DEFAULT_DATA.charAt(0);
        char data;
        
        this.setState(this.inputData);
        
        // Apply
        this.read();
        this.unread();
        data = this.inputData.getRemainingData().charAt(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=InvalidStateException.class)
    public void testUnread_CurrChar_AfterPeekNextRead_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState(this.inputData);
        
        this.peekNextRead();
        
        // Apply + Assert
        this.unread();
    }
    
    @Test
    public void testCurrentPosition_DefaultPosition_ResultMatches() {
        // Arrange
        Point expData = new Point(0, 0);
        Point data;
        
        this.setState(this.inputData);
        
        // Apply
        data = this.currentPosition();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testCurrentPosition_SetPosition_ResultMatches() {
        // Arrange
        Point expData = new Point(123, 456);
        Point data;
        
        this.inputData.setPosition(expData);
        this.setState(this.inputData);
        
        // Apply
        data = this.currentPosition();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testCurrentPosition_NullPosition_ResultMatches() {
        // Arrange
        Point expData = null;
        Point data;
        
        this.inputData.setPosition(expData);
        this.setState(inputData);
        
        // Apply
        data = this.currentPosition();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsWhitespace_isSpace_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        data = this.isWhitespace(' ');
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsWhitespace_isNewline_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        char newLine = String.format("%n").charAt(0);
        
        // Apply
        data = this.isWhitespace(newLine);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsWhitespace_isTab_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        data = this.isWhitespace('\t');
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsWhitespace_NonWhitespace_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.isWhitespace(SOME_CHAR);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testGetCurrChar_WithSetData_MatchesExpectedCharacter() throws IOException {
        // Arrange
        char expData = SOME_CHAR;
        char data;
        
        this.setState(this.inputData);
        this.currChar = expData;
        
        // Apply
        data = this.getCurrChar();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=InvalidStateException.class)
    public void testGetCurrChar_WithoutReadFirst_ThrowsExpectedException() throws IOException {
        super.getCurrChar();
    }
    
    @Test(expected=InvalidStateException.class)
    public void testGetCurrChar_AfterPeekNextRead_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState(this.inputData);
        this.peekNextRead();
        
        // Apply + Assert
        this.getCurrChar();
    }
    
    @Test(expected=InvalidStateException.class)
    public void testGetCurrChar_AfterUnread_ThrowsExpectedException() throws IOException {
        // Arrange
        this.currChar = SOME_CHAR;
        this.unread();
        
        // Apply + Assert 
        this.getCurrChar();
    }
    
    @Test
    public void testGetInput_WithValidInput_MatchesCurrentInput() throws IOException {
        // Arrange
        PushbackAndPositionReader expData = this.inputData;
        PushbackAndPositionReader data;
        
        super.setState(this.inputData);
        
        // Apply
        data = super.getInput();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=InvalidStateException.class)
    public void testGetInput_WithUnsetInput_ThrowsExpectedException() {
        // Arrange
        
        // Apply + Assert
        super.getInput();
    }
    
    @Test(expected=InvalidStateException.class)
    public void testGetInput_WithNullInput_ThrowsExpectedException() {
        // Arrange
        super.setState(null);
        
        // Apply + Assert
        super.getInput();
    }
    
    @Test(expected=InvalidStateException.class)
    public void testRead_WithoutStateSet_ThrowsExpectedException() throws IOException {
        this.read();
    }
    
    @Test(expected=InvalidStateException.class)
    public void testUnread_WithoutStateSet_ThrowsExpectedException() throws IOException {
        this.unread(SOME_CHAR);
    }
    
    @Test(expected=InvalidStateException.class)
    public void testCurrentPosition_WithoutStateSet_ThrowsExpectedException() throws IOException {
        this.currentPosition();
    }
    
    @Override
    public Boolean parse(PushbackAndPositionReader input) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
    
    private String getWordBetweenIndices(int start, int end) throws IOException {
        StringBuilder result = new StringBuilder();
        
        for(int i = start; i > 0; i--)
            this.read();
        
        for(int i = start; i < end; i++) {
            char c = this.read();
            result.append(c);
        }
        return result.toString();
    }
    
    private PushbackAndPositionReaderMock getEmptyReader() {
        return new PushbackAndPositionReaderMock(new LinkedList<Character>());
    }
    
    @After
    public void tearDown() {
        super.clearState();
        this.inputData = null;
    }
}
