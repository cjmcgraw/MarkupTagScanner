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
package com.mycompany.markuptagscanner.readers.utilities;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PushbackAndNewLineConsumerReaderTest {
    private static final String newLine = PushbackAndNewLineConsumerReader.newLine;
    private static final String[] defaultPhrases = {"someString", "some other data", "    the last bit of data   \t"};
    private static final String defaultData = defaultPhrases[0] + newLine + defaultPhrases[1] + newLine + newLine + newLine + defaultPhrases[2] + newLine;
    private static final String expectedSentence = defaultPhrases[0] + defaultPhrases[1] + defaultPhrases[2];

    private static final int FIRST_LINE = 1;
    
    private PushbackAndNewLineConsumerReader reader;
    
    @Before
    public void setUp() {
        this.setState(defaultData);
    }
    
    @After
    public void tearDown() throws IOException{
        this.clearState();
    }
    
    @Test
    public void testRead_SingleChar_StartCharacter() throws IOException {
        // Arrange
        char data;
        char expData = defaultData.charAt(0);
        
        // Apply
        data = (char) this.reader.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test 
    public void testRead_SingleChar_StartPhrase() throws IOException {
        // Arrange
        String data;
        String expData = defaultPhrases[0];
        
        // Apply
        data = this.readData(0, expData.length());
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_SingleChar_SecondPhrase() throws IOException {
        // Arrange
        String data;
        String expData = defaultPhrases[1];
        
        // Apply
        data = this.readData(defaultPhrases[0].length(), expData.length());
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_SingleChar_ThirdPhrase() throws IOException {
        // Arrange
        String data;
        String expData = defaultPhrases[2];
        
        // Apply
        data = this.readData(defaultPhrases[0].length() + 
                             defaultPhrases[1].length(),
                             expData.length());
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_SingleChar_EntireString() throws IOException {
        // Arrange
        String data;
        String expData = expectedSentence;
        
        // Apply
        data = this.readData(0, expData.length());
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_SingleChar_BeyondEntireString() throws IOException {
        // Arrange
        int data;
        int expData = -1;
        
        this.reader.skip(expectedSentence.length());
        
        // Apply
        data = this.reader.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_EntireArray_StartChar() throws IOException {
        // Arrange
        char[] data = new char[1];
        char[] expData = {defaultData.charAt(0)};
        
        // Apply
        this.reader.read(data);
        
        // Assert
        assertArrayEquals(expData, data);
    }
    
    @Test
    public void testRead_EntireArray_FirstPhrase() throws IOException {
        // Arrange
        char[] expData = defaultPhrases[0].toCharArray();
        char[] data = new char[expData.length];
        
        // Apply
        this.reader.read(data);
        
        // Assert
        assertArrayEquals(expData, data);
    }
    
    @Test
    public void testRead_EntireArray_EntireString() throws IOException {
        // Arrange
        char[] expData = (expectedSentence).toCharArray();
        char[] data = new char[expData.length];
        
        // Apply
        this.reader.read(data);
        
        // Assert
        assertArrayEquals(expData, data);
    }
    
    @Test
    public void testRead_EntireArray_BeyondEntireString() throws IOException {
        // Arrange
        char[] expData = new char[15];
        char[] data = new char[15];
        
        Arrays.fill(expData, (char) -1);
        
        this.reader.skip(expectedSentence.length());
        
        // Apply
        this.reader.read(data);
        
        // Assert
        assertArrayEquals(expData, data);
    }
    
    @Test
    public void testRead_SubsectionArray_ThreeOffset_StartChar() throws IOException {
        // Arrange
        char[] expData = this.fillArrayWithPhrase(3, defaultData.substring(0, 1));
        char[] data = new char[expData.length];
        
       // Apply
        this.reader.read(data, 3, data.length);
        
        // Assert
        assertArrayEquals(expData, data);
    }
    
    @Test
    public void testRead_SubsectionArray_ThreeOffset_SecondPhrase() throws IOException {
        // Arrange
        char[] expData = this.fillArrayWithPhrase(3, defaultPhrases[1]);
        char[] data = new char[expData.length];
        
        this.readData(0, defaultPhrases[0].length());
        
        // Apply
        this.reader.read(data, 3, data.length);
        
        // Assert
        assertArrayEquals(expData, data);
    }
    
    @Test
    public void testRead_SubsectionArray_ThreeOffset_EntireString() throws IOException {
        // Arrange
        char[] expData = this.fillArrayWithPhrase(3, expectedSentence);
        char[] data = new char[expData.length];
        
        // Apply
        this.reader.read(data, 3, data.length);
        
        // Assert
        assertArrayEquals(expData, data);
    }
    
    @Test
    public void testRead_SkipsNewLines_ReadsFirstCharofSecondPhrase() throws IOException {
        // Arrange
        String expData = "" + defaultPhrases[1].charAt(0);
        String data;
        
        // Apply
        data = this.readData(defaultPhrases[0].length(), 1);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_CharBuffer_StartChar() throws IOException {
        // Arrange
        CharBuffer expData = CharBuffer.allocate(1).put(defaultData.charAt(0));
        CharBuffer data = CharBuffer.allocate(1);
        
        // Apply
        this.reader.read(data);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_CharBuffer_SecondPhrase() throws IOException {
        // Arrange
        char[] values = defaultPhrases[1].toCharArray();
        CharBuffer expData = CharBuffer.allocate(values.length).put(values);
        CharBuffer data = CharBuffer.allocate(values.length);
        
        this.readData(0, defaultPhrases[0].length());
        
        // Apply
        this.reader.read(data);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_CharBuffer_EntireString() throws IOException {
        // Arrange
        char[] values = (expectedSentence).toCharArray();
        CharBuffer expData = CharBuffer.allocate(values.length).put(values);
        CharBuffer data = CharBuffer.allocate(values.length);
        
        // Apply
        this.reader.read(data);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testRead_CharBuffer_BeyondEntireString() throws IOException {
        // Arrange
        char[] values = expectedSentence.toCharArray();
        CharBuffer expData = CharBuffer.allocate(values.length + 3).put(values);
        expData.append((char) -1);
        expData.append((char) -1);
        expData.append((char) -1);
        
        CharBuffer data = CharBuffer.allocate(values.length + 3);
        
        // Apply
        this.reader.read(data);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testUnread_AfterRead_StartCharacter() throws IOException {
        // Arrange
        char data;
        char expData = (char) this.reader.read();
        
        // Apply
        this.reader.unread(expData);
        data = (char) this.reader.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testUnread_NoRead_SomeCharacter() throws IOException {
        // Arrange
        char data;
        char expData = '~';
        
        // Apply
        this.reader.unread(expData);
        data = (char) this.reader.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=IOException.class)
    public void testUnread_ExceedMaximumBufferLength() throws IOException {
        // Arrange
        String data;
        String expData = "ab";
        
        // Apply
        this.unreadData(expData);
        data = readData(0, expData.length());
        
        // Assert
        assertEquals(expData, data);
        
    }
    
    @Test
    public void testGetPosition_FirstPhrase_NoNewLines_OnlyChangesInX() throws IOException {
        // Arrange
        int expData = defaultPhrases[0].length();
        int data;
        
        // Apply
        this.readData(0, expData);
        data = this.reader.getPosition().x;
        
        // Assert
        assertEquals(expData, data);
    }

    @Test
    public void testGetPosition_FirstPhrase_NoNewLines_ExpectedValueMatchesY() throws IOException {
        // Arrange
        int expData = FIRST_LINE;
        int data;

        // Apply
        this.readData(0, defaultPhrases[0].length());
        data = this.reader.getPosition().y;

        // Assert
        assertEquals(expData, data);
    }

    @Test
    public void testGetPosition_TwoPhrases_WithNewLines_ExpectedValueMatchesY() throws IOException {
        // Arrange
        int expData = FIRST_LINE + 1;
        int data;


        // Apply
        this.readData(0, defaultPhrases[0].length() + defaultPhrases[1].length());
        data = this.reader.getPosition().y;

        // Assert
        assertEquals(expData, data);
    }

    @Test
    public void testGetPosition_ThreePhrases_WithNewLines_ExpectedValueMatchesY() throws IOException {
        // Arrange
        int expData = FIRST_LINE + 1 + 3;
        int data;

        // Apply
        this.readData(0, defaultPhrases[0].length() + defaultPhrases[1].length() + defaultPhrases[0].length());
        data = this.reader.getPosition().y;

        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testGetPosition_EndOfFirstPhraseToSecondPhrase_OneNewLine_XIsReset() throws IOException {
        // Arrange
        Point expData = new Point(defaultPhrases[1].length(), FIRST_LINE + 1);
        Point data;
        
        // Apply
        this.readData(defaultPhrases[0].length(), defaultPhrases[1].length());
        data = this.reader.getPosition();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testGetPosition_EndOfSecondPhraseToThirdPhrase_FourNewLines_XIsReset() throws IOException {
        // Arrange
        Point expData = new Point(defaultPhrases[2].length(), FIRST_LINE + 1 + 3);
        Point data;
        
        // Apply
        this.readData(defaultPhrases[0].length() + defaultPhrases[1].length(),
                      expData.x);
        data = this.reader.getPosition();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSkip_SkipFirstCharacter() throws IOException {
        // Arrange
        char expData = defaultData.charAt(1);
        char data;
        
        // Apply
        this.reader.skip(1);
        data = (char) this.reader.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test 
    public void testSkip_SkipFirstPhrase() throws IOException {
        // Arrange
        char expData = defaultPhrases[1].charAt(0);
        char data;
        
        // Apply
        this.reader.skip(defaultPhrases[0].length());
        data = (char) this.reader.read();
        
        // Assert
        assertEquals(expData, data);
    }
    
    private String readData(int startLength, int numOfChars) throws IOException {
        StringBuilder data = new StringBuilder();
        
        this.reader.skip(startLength);
        
        for(int i = 0; i < numOfChars; i++) {
            char c = (char) this.reader.read();
            data.append(c);
        }
        return data.toString();
    }
    
    private void unreadData(String data) throws IOException {
        for(int i = 0; i < data.length(); i++) {
            char c = data.charAt(0);
            this.reader.unread(c);
        }
    }
    
    private char[] fillArrayWithPhrase(int startIndex, String phrase) {
        char[] result = new char[startIndex + phrase.length()];
        
        for (int i = startIndex; i < result.length; i++) {
            result[i] = phrase.charAt(i - startIndex);
        }
        
        return result;
    }
    
    private void setState(String data) {
        Reader input = new StringReader(data);
        this.reader = new PushbackAndNewLineConsumerReader(input);
    }
    
    private void clearState() throws IOException {
        this.reader.close();
        this.reader = null;
    }
}
