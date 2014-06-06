package com.mycompany.htmlvalidator.parsers;

import java.io.IOException;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.mycompany.htmlvalidator.MutableHtmlTagImpl;
import com.mycompany.htmlvalidator.exceptions.IllegalHtmlTagException;
import com.mycompany.htmlvalidator.interfaces.HtmlTag;
import com.mycompany.htmlvalidator.parsers.utilities.HtmlData;
import com.mycompany.htmlvalidator.parsers.utilities.mock.HtmlTagDataParserMock;
import com.mycompany.htmlvalidator.parsers.utilities.mock.HtmlBufferedReaderMock;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HtmlTagScannerTest {
    private final List<String> defaultData = Arrays.asList("tag1", "tag2", "tag3");
    
    private HtmlBufferedReaderMock reader;
    private HtmlTagDataParserMock parser;
    private HtmlScanner scanner;
    
    @Before
    public void setDefaultState() {
        this.setState(defaultData, true);
    }
    
    @After
    public void clearDefaultState() {
        try {
            this.clearState();
        } catch (IOException e) {
            throw new RuntimeException("Error in closing scanner: " + e.getLocalizedMessage());
        }
    }
    
    @Test
    public void testHasNext_DefaultState_firstValue() {
        // Arrange
        boolean data;
        boolean expData = true;
        
        // Apply
        data = this.scanner.hasNext();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testHasNext_DefaultState_secondValue() {
        // Arrange
        boolean data;
        boolean expData = true;
        
        // Apply
        this.cycleScanner(1);
        data = this.scanner.hasNext();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testHasNext_DefaultState_secondToLastValue() {
        // Arrange
        boolean data;
        boolean expData = true;
        
        // Apply
        this.cycleScanner(this.defaultData.size() - 1);
        data = this.scanner.hasNext();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testHasNext_DefaultState_lastValue() {
        // Arrange
        boolean data;
        boolean expData = false;
        
        // Apply
        this.cycleScanner(this.defaultData.size());
        data = this.scanner.hasNext();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_DefaultState_firstValue() {
        // Arrange
        HtmlTag data;
        HtmlTag expData;
        
        // Apply
        this.cycleScanner(0);
        
        expData = this.getDefaultValueAt(0);
        data = this.getNextValue();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_DefaultState_secondValue() {
        // Arrange
        HtmlTag data;
        HtmlTag expData;
        
        // Apply
        this.cycleScanner(1);
        
        expData = this.getDefaultValueAt(1);
        data = this.getNextValue();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_DefaultState_lastValue() {
        // Arrange
        HtmlTag data;
        HtmlTag expData;
        
        // Apply
        this.cycleScanner(this.defaultData.size() - 1);
        
        expData = this.getDefaultValueAt(this.defaultData.size() - 1);
        data = this.getNextValue();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=IllegalHtmlTagException.class)
    public void testNext_ExceptionState_InvalidParserValue() throws IllegalHtmlTagException {
        // Arrange
        this.parser.setOutputException(true);
        
        // Apply + Assert
        this.scanner.next();
    }
    
    @Test(expected=NoSuchElementException.class)
    public void testNext_ExceptionState_NoMoreValidValuesToIterateOver() throws IllegalHtmlTagException {
        // Arrange
        this.reader.setOutputException(true);
        
        // Apply + Assert;
        this.scanner.next();
    }
    
    @Test
    public void testClose() throws IOException{
        // Arrange
        boolean data;
        boolean expData = true;
        
        // Apply
        this.scanner.close();
        data = this.reader.isClosed();
        
        // Assert
        assertEquals(expData, data);
    }
    
    private void cycleScanner(int numOfCycles) {
        for(; numOfCycles > 0; numOfCycles--) {
            this.getNextValue();
        }
    }
    
    private HtmlTag getNextValue() {
        try {
            return this.scanner.next();
        } catch (IllegalHtmlTagException e) {
            throw new IllegalArgumentException();
        }
    }
    
    private HtmlTag getDefaultValueAt(int index) {
        try {
            HtmlData data = this.parser.parse(this.defaultData.get(index));
            return new MutableHtmlTagImpl(data);
        } catch (IllegalHtmlTagException e) {
            throw new RuntimeException("Error involving mock parser!");
        }
    }
    
    private void setState(Collection<String> readerData, boolean closingTag) {
        Map<String, Boolean> parserData = new HashMap<String, Boolean>();
        
        for (String data : readerData) {
            parserData.put(data, closingTag);
        }
        
        this.reader = new HtmlBufferedReaderMock(readerData);
        this.parser = new HtmlTagDataParserMock(parserData);
        
        this.scanner = new HtmlTagScanner(this.reader, this.parser);
    }
    
    private void clearState() throws IOException {
        this.scanner.close();
        this.scanner = null;
        this.parser = null;
        this.reader = null;
    }
}
