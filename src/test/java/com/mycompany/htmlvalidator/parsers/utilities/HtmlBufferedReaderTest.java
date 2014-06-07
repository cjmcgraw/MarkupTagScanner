package com.mycompany.htmlvalidator.parsers.utilities;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mycompany.htmlvalidator.parsers.utilities.mock.ReaderMock;

public class HtmlBufferedReaderTest {
    private final String[] tags = {"<tag1>", "<tag2>", "<tag3>", "<tag with data>",
                                   "<missingclosing", "<tag4>", "missingopening>", "<illformedtag<",
                                   "<<", ">>", "<tag5>"};
    private final String newLine = String.format("%n");
    private final String defaultData = tags[0] + newLine +
                                       tags[1] + " " + newLine + "\t" +
                                       tags[2] + newLine + "     " + 
                                       tags[3] + newLine + newLine +
                                       tags[4] + " some random text that shouldn't be given back" + 
                                       tags[5] + "some other " + newLine + "text that should be \t\tignored" +
                                       tags[6] + 
                                       tags[7] + newLine +
                                       tags[8] + 
                                       tags[9] + 
                                       tags[10];
    private HtmlBufferedReader reader;
    private ReaderMock mockReader;
    
    @Before
    public void setUp() {
        this.setState(this.defaultData);
    }
    
    @After
    public void tearDown() {
        this.clearState();
    }
    
    @Test
    public void testGetCurrentLineNumber_AfterFirstNext() {
        // Arrange: expected number of new lines determined from defaultData
        int data;
        int expData = 1;
        
        // Apply
        this.cycleReaderToTagAt(0);
        data = this.reader.getCurrentLineNumber();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testGetCurrentLineNumber_AfterMiddleNext() {
        // Arrange: expected number of new lines determined from defaultData
        int data;
        int expData = 5;
        
        // Apply
        this.cycleReaderToTagAt(3);
        data = this.reader.getCurrentLineNumber();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testGetCurrentLineNumber_AfterFinalNext() {
        // Arrange: expected number of new lines determined from defaultData
        int data;
        int expData = 7;
        
        // Apply
        this.cycleReaderToTagAt(9);
        data = this.reader.getCurrentLineNumber();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testClose() throws IOException {
        // Arrange
        boolean data;
        boolean expData = true;
        
        // Apply
        this.reader.close();
        data = this.mockReader.isClosed();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testHasNext_StartOfData() {
        // Arrange
        boolean data;
        boolean expData = true;
        
        // Apply
        data = this.reader.hasNext();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testHasNext_SecondStartOfData() {
        // Arrange
        boolean data;
        boolean expData = true;
        
        //Apply
        this.cycleReaderToTagAt(1);
        data = this.reader.hasNext();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testHasNext_SecondToEndOfData() {
        // Arrange
        boolean data;
        boolean expData = true;
        
        // Apply
        this.cycleReaderToTagAt(this.tags.length - 1);
        data = this.reader.hasNext();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testHasNext_EndOfData() {
        // Arrange
        boolean data;
        boolean expData = false;
        
        // Apply
        this.cycleReaderToTagAt(this.tags.length - 1);
        data = this.reader.hasNext();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_StandardTag_StartOfData() {
        // Arrange
        String data;
        String expData = this.tags[0];
        
        // Apply
        data = this.reader.next();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_StandardTag_SecondStartOfData() {
        // Arrange
        String data;
        String expData = this.tags[1];
        
        // Apply
        this.cycleReader(1);
        data = this.reader.next();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_StandardTag_EndOfData() {
        // Arrange
        String data;
        String expData = this.tags[this.tags.length - 1];
        
        // Apply
        this.cycleReader(this.tags.length - 1);
        data = this.reader.next();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_InvalidTag_MissingClosing() {
        // Arrange
        String data;
        String expData = this.tags[4];
        
        // Apply
        this.cycleReader(4);
        data = this.reader.next();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_InvalidTag_MissingOpening() {
        // Arrange
        String data;
        String expData = this.tags[6];
        
        // Apply
        this.cycleReader(6);
        data = this.reader.next();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_InvalidTag_DoubleOpening() {
        // Arrange
        String data;
        String expData = this.tags[8];
        
        // Apply
        this.cycleReader(8);
        data = this.reader.next();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_InvalidTag_DoubleClosing() {
        // Arrange
        String data;
        String expData = this.tags[9];
        
        // Apply
        this.cycleReader(9);
        data = this.reader.next();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=UnsupportedOperationException.class)
    public void testRemove() {
        // Arrange
        this.cycleReader(1);
        
        // Apply + Assert
        this.reader.remove();
    }
    
    public void cycleReader(int numOfCycles) {
        if(numOfCycles > 0) {
            this.reader.next();
            this.cycleReader(numOfCycles - 1);
        }
    }
    
    public void cycleReaderToTagAt(int index) {
        if(index > 0) {
            this.mockReader.pointToTag(index);
        }
    }
    
    public void setState(String data) {
        this.mockReader = new ReaderMock(data, tags);
        this.reader = new HtmlBufferedReader();
        this.reader.setReader(mockReader);
    }
    
    public void clearState() {
        this.reader = null;
    }
}
