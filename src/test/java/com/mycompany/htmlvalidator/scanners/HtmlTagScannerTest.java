package com.mycompany.htmlvalidator.scanners;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

import org.junit.*;

import com.mycompany.htmlvalidator.scanners.readers.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.*;

public class HtmlTagScannerTest {
    public static ArrayList<HtmlData> DEFAULT_DATA = generateDefaultData();
    public static boolean DEFAULT_EXCEPTION_STATE = false;
    
    private static ArrayList<HtmlData> generateDefaultData() {
        ArrayList<HtmlData> result = new ArrayList<>();
        
        MutableHtmlData firstValue = new MutableHtmlData();
        firstValue.setName("firstName");
        result.add(firstValue);
        
        MutableHtmlData midValue = new MutableHtmlData();
        midValue.setName("midName");
        result.add(midValue);
        
        MutableHtmlData lastValue = new MutableHtmlData();
        lastValue.setName("lastName");
        result.add(lastValue);
        
        return result;
    }
    
    private HtmlTagScanner scanner;
    private boolean exceptionState = DEFAULT_EXCEPTION_STATE;
    private HtmlReaderMock reader;
    
    @Before
    public void setStateFromDefaults() {
        this.setState(DEFAULT_DATA, DEFAULT_EXCEPTION_STATE);
    }
    
    @Test
    public void testClose_ClosesReader() throws IOException {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        this.scanner.close();
        data = this.reader.getClosed();
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_FirstElement_ResultMatchesFirstHtmlData() {
        // Arrange
        HtmlData expData = DEFAULT_DATA.get(0);
        HtmlData data;
        
        // Apply
        data = this.scanner.next();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNext_SecondElement_ResultMatchesSecondHtmlData() {
        // Arrange
        HtmlData expData = DEFAULT_DATA.get(1);
        HtmlData data;
        
        // Apply
        this.scanner.next();
        data = this.scanner.next();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void textNext_ThirdElement_ResultMatchesThirdHtmlData() {
        // Arrange
        HtmlData expData = DEFAULT_DATA.get(2);
        HtmlData data;
        
        // Apply
        this.scanner.next();
        this.scanner.next();
        data = this.scanner.next();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testHasNext_ByCyclingData_PreFirstElement_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        data = this.scanner.hasNext();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testHasNext_ByCyclingData_PreSecondElement_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.scanner.next();
        
        // Apply
        data = this.scanner.hasNext();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testHasNext_ByCyclingData_PreThirdElement_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.scanner.next();
        this.scanner.next();
        
        // Apply
        data = this.scanner.hasNext();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testHasNext_ByCyclingData_PostThirdElement_ResultIsTrue() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.scanner.next();
        this.scanner.next();
        this.scanner.next();
        
        // Apply
        data = this.scanner.hasNext();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testHasNext_EmptyStartData_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.setState(new ArrayList<HtmlData>(), this.exceptionState);
        
        // Apply
        data = this.scanner.hasNext();
        
        // Assert
        assertEquals(expData, data);
    }
    
    private void setState(List<HtmlData> data, boolean exceptionState) {
        this.reader = new HtmlReaderMock(data, exceptionState);
        this.scanner = new HtmlTagScanner(this.reader);
    }
    
    @After
    public void clearState() {
        this.setDefaultState();
        this.scanner = null;
        this.reader = null;
    }
    
    private void setDefaultState() {
        this.exceptionState = DEFAULT_EXCEPTION_STATE;
    }
}
