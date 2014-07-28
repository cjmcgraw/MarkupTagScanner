package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.*;

import com.mycompany.htmlvalidator.scanners.MarkupTag;
import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.InvalidStateException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.HtmlQuoteEnclosureParser;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions.MissingCharacterComponentException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReaderMock;

public class HtmlQuoteEnclosureParserTest {
    private static final String DEFAULT_DATA_SINGLE_QUOTED = String.format("%sother data%s", MarkupTag.SINGLE_QUOTE, MarkupTag.SINGLE_QUOTE);
    private static final String DEFAULT_DATA_DOUBLE_QUOTED = String.format("%ssome other data%s", MarkupTag.DOUBLE_QUOTE, MarkupTag.DOUBLE_QUOTE);
    private static final String DEFAULT_REMAINING_DATA = "some remaining data";
    
    private HtmlQuoteEnclosureParser parser = new HtmlQuoteEnclosureParser();
    private PushbackAndPositionReaderMock input;
    private LinkedList<Character> inputData;
    
    /* Potential exceptions specific to this class:
     * 
     *  1.
     *      public method:
     *          parse
     *      
     *      condition:
     *          first character is not a valid enclosure (' , ")
     *          
     *      exception:
     *          parser -> setAndValidateOpening -> MissingCharacterComponentException
     *  
     *  2.
     *      public method:
     *          parse
     *      
     *      condition:
     *          last character is not a valid matching enclosure ( ' --> ', " --> ")
     *      
     *      exception:
     *          EOFException (as the enclosure never terminates)
     *          
     *  3.
     *      public method:
     *          parse
     *      
     *      condition:
     *          different opening and closing enclosures
     *          
     *      exception:
     *          EOFException (as the enclosure never terminates)
     *  
     *  4.
     *      public method:
     *          parse
     *          
     *      condition:
     *          quoteData cleared and never reinitialized
     *          Cannot possibly occur and acts only to prevent
     *          misuse of protected methods.
     *              
     *      exception:
     *          InvalidStateException
     */
    
    @Test
    public void testParse_OneQuotedValue_ValidSingleQuotedValue_ReturnedDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA_SINGLE_QUOTED;
        String data;
        
        this.setState(expData);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OneQuotedValueWithExtraRemainingData_ValidSingleQuotedValue_ReturnedDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA_SINGLE_QUOTED;
        String data;
        
        this.setState(expData + DEFAULT_REMAINING_DATA);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_TwoQuotedValuesSingleFollowedByDouble_ValidSingleQuotedValue_ReturnedDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA_SINGLE_QUOTED;
        String data;
        
        this.setState(expData + DEFAULT_DATA_DOUBLE_QUOTED);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_TwoQuotedValuesTwoSingle_ValidSingleQuotedValue_ReturnedDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA_SINGLE_QUOTED;
        String data;
        
        this.setState(expData + expData);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OneQuotedValue_ValidSingleQuotedValue_RemainingDataIsEmpty() throws IOException {
        // Arrange
        String expData = "";
        String data;
        
        this.setState(DEFAULT_DATA_SINGLE_QUOTED);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OneQuotedValueWithExtraRemainingData_ValidSingleQuotedValue_ExpectedRemainingData() throws IOException {
        String expData = DEFAULT_REMAINING_DATA;
        String data;
        
        this.setState(DEFAULT_DATA_SINGLE_QUOTED + expData);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_TwoQuotedValuesOneSingleFollowedByOneDouble_ValidSingleQuotedValue_ExpectedRemainingData() throws IOException {
        String expData = DEFAULT_DATA_DOUBLE_QUOTED;
        String data;
        
        this.setState(DEFAULT_DATA_SINGLE_QUOTED + expData);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_TwoQuotedValuesBothSingle_ValidSingleQuotedValue_ExpectedRemainingData() throws IOException {
        String expData = DEFAULT_DATA_SINGLE_QUOTED;
        String data;
        
        this.setState(DEFAULT_DATA_SINGLE_QUOTED + expData);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OneQuotedValue_ValidDoubleQuotedValue_ReturnedDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA_SINGLE_QUOTED;
        String data;
        
        this.setState(expData);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OneQuotedValueWithExtraRemainingData_ValidDoubleQuotedValue_ReturnedDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA_DOUBLE_QUOTED;
        String data;
        
        this.setState(expData + DEFAULT_REMAINING_DATA);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_TwoQuotedValuesSingleFollowedByDouble_ValidDoubleQuotedValue_ReturnedDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA_DOUBLE_QUOTED;
        String data;
        
        this.setState(expData + DEFAULT_DATA_DOUBLE_QUOTED);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_TwoQuotedValuesTwoSingle_ValidDoubleQuotedValue_ReturnedDataMatches() throws IOException {
        // Arrange
        String expData = DEFAULT_DATA_DOUBLE_QUOTED;
        String data;
        
        this.setState(expData + expData);
        
        // Apply
        data = this.parser.parse(this.input);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OneQuotedValue_ValidDoubleQuotedValue_RemainingDataIsEmpty() throws IOException {
        // Arrange
        String expData = "";
        String data;
        
        this.setState(DEFAULT_DATA_DOUBLE_QUOTED);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_OneQuotedValueWithExtraRemainingData_ValidDoubleQuotedValue_ExpectedRemainingData() throws IOException {
        String expData = DEFAULT_REMAINING_DATA;
        String data;
        
        this.setState(DEFAULT_DATA_DOUBLE_QUOTED + expData);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_TwoQuotedValuesOneSingleFollowedByOneDouble_ValidDoubleQuotedValue_ExpectedRemainingData() throws IOException {
        String expData = DEFAULT_DATA_DOUBLE_QUOTED;
        String data;
        
        this.setState(DEFAULT_DATA_DOUBLE_QUOTED + expData);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_TwoQuotedValuesBothSingle_ValidDoubleQuotedValue_ExpectedRemainingData() throws IOException {
        String expData = DEFAULT_DATA_SINGLE_QUOTED;
        String data;
        
        this.setState(DEFAULT_DATA_DOUBLE_QUOTED + expData);
        
        // Apply
        this.parser.parse(this.input);
        data = this.input.getRemainingData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    // Exception 1
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    @Test(expected=MissingCharacterComponentException.class)
    public void testParse_InvalidValueMissingStartQuote_ExpectedThrowsException() throws IOException {
        // Arrange
        this.setState(DEFAULT_REMAINING_DATA + MarkupTag.DOUBLE_QUOTE);
        
        // Apply + Assert
        this.parser.parse(input);
    }
    
    @Test
    public void testParse_InvalidValueMissingStartQuote_AllDataRemainingInInput() throws IOException {
        // Arrange
        String expData = DEFAULT_REMAINING_DATA + MarkupTag.DOUBLE_QUOTE;
        String data = "";
        
        this.setState(expData);
        
        // Apply
        try {
            this.parser.parse(input);
        } catch (MissingCharacterComponentException e) {
            data = this.input.getRemainingData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_InvalidValueMissingStartQuote_StoredExceptionDataIsEmpty() throws IOException {
        // Arrange
        String expData = "";
        String data = "filler to prevent false positive";
        
        this.setState(DEFAULT_REMAINING_DATA + MarkupTag.DOUBLE_QUOTE);
        
        // Apply
        try {
            this.parser.parse(input);
        } catch (MissingCharacterComponentException e) {
            data = e.getData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    // Exception 2
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    @Test(expected=EOFException.class)
    public void testParse_InvalidValueMissingEndDoubleQuote_ThrowsEOFException() throws IOException {
        // Arrange
        this.setState(MarkupTag.DOUBLE_QUOTE + DEFAULT_REMAINING_DATA);
        
        // Apply + Assert
        this.parser.parse(input);
    }
    
    @Test
    public void testParse_InvalidValueMissingEndDoubleQuote_NoRemainingData() throws IOException {
        // Arrange
        String expData = "";
        String data = "value to prevent false positive";
        
        this.setState(MarkupTag.DOUBLE_QUOTE + DEFAULT_REMAINING_DATA);
        
        // Apply
        try {
            this.parser.parse(input);
        } catch (EOFException e) {
            data = this.input.getRemainingData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    // Exception 3
    //=========================================================================
    //*************************************************************************
    //=========================================================================
    
    @Test(expected=EOFException.class)
    public void testParse_SingleQuoteOpeningDoubleQuoteClosing_ThrowsEOFException() throws IOException {
        // Arrange
        this.setState(MarkupTag.DOUBLE_QUOTE + DEFAULT_REMAINING_DATA + MarkupTag.SINGLE_QUOTE + DEFAULT_REMAINING_DATA);
        
        // Apply + Assert
        this.parser.parse(input);
    }
    
    @Test
    public void testParse_SingleQuoteOpeningDoubleQuoteClosing_RemainingDataIsEmpty() throws IOException {
        // Arrange
        String expData = "";
        String data = "value to prevent false positive";
        
        this.setState(MarkupTag.DOUBLE_QUOTE + DEFAULT_REMAINING_DATA + MarkupTag.SINGLE_QUOTE + DEFAULT_REMAINING_DATA);
        
        // Apply
        try {
            this.parser.parse(input);
        } catch (EOFException e) {
            data = this.input.getRemainingData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=EOFException.class)
    public void testParse_DoubleQuoteOpeningSingleQuoteClosing_ThrowsEOFException() throws IOException {
        // Arrange
        this.setState(MarkupTag.SINGLE_QUOTE + DEFAULT_REMAINING_DATA + MarkupTag.DOUBLE_QUOTE + DEFAULT_REMAINING_DATA);
        
        // Apply + Assert
        this.parser.parse(input);
    }
    
    @Test
    public void testParse_DoubleQuoteOpeningSingleQuoteClosing_RemainingDataIsEmpty() throws IOException {
        // Arrange
        String expData = "";
        String data = "value to prevent false positive";
        
        this.setState(MarkupTag.SINGLE_QUOTE + DEFAULT_REMAINING_DATA + MarkupTag.DOUBLE_QUOTE + DEFAULT_REMAINING_DATA);
        
        // Apply
        try {
            this.parser.parse(input);
        } catch (EOFException e) {
            data = this.input.getRemainingData();
        }
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=InvalidStateException.class)
    public void testParse_NullInputGivenViaParse_ThrowsExpectedException() throws IOException {
        this.parser.parse(null);
    }
    
    private void setState(String data) {
        this.inputData = new LinkedList<Character>();
        
        for (int i = 0; i < data.length(); i++)
            this.inputData.add(data.charAt(i));
        
        this.input = new PushbackAndPositionReaderMock(this.inputData);
    }
    
    @After
    public void clearState() {
        this.inputData = null;
        this.input = null;
        this.parser.clearState();
    }
}