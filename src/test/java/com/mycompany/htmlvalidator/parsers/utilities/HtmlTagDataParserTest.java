package com.mycompany.htmlvalidator.parsers.utilities;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.BeforeClass;

import com.mycompany.htmlvalidator.parsers.utilities.HtmlData;
import com.mycompany.htmlvalidator.parsers.utilities.HtmlTagDataParser;
import com.mycompany.htmlvalidator.parsers.utilities.HtmlTagDataParserImpl;
import com.mycompany.htmlvalidator.exceptions.IllegalHtmlTagException;

public class HtmlTagDataParserTest {
    public static HtmlTagDataParser parser;
    
    @BeforeClass
    public static void setUpClass() {
        HtmlTagDataParserTest.parser = new HtmlTagDataParserImpl();
    }
    
    @Test
    public void testParse_IsOpenTag_ValidTag_NonSelfClosingOpen() throws IllegalHtmlTagException {
        // Arrange
        boolean data;
        boolean expData = true;
        
        String tag = "<p>";
        
        // Apply
        HtmlData htmlData = parser.parse(tag);
        data = htmlData.isOpenTag();
        
        // Assert
        assertEquals(expData, data);
        
    }
    
    @Test
    public void testParse_IsOpenTag_ValidTag_NonSelfClosingClosed() throws IllegalHtmlTagException {
        // Arrange
        boolean data;
        boolean expData = false;
        
        String tag = "</p>";
        
        // Apply
        HtmlData htmlData = parser.parse(tag);
        data = htmlData.isOpenTag();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ElementData_ValidTag_SimpleElementData() throws IllegalHtmlTagException {
        // Arrange
        String data;
        String expData = "simpleElementData";
        
        String tag = "<" + expData + ">";
        
        // Apply
        HtmlData htmlData = parser.parse(tag);
        data = htmlData.getElementData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ElementData_ValidTag_ComplexElementDataNoBrackets() throws IllegalHtmlTagException {
        // Arrange
        String data;
        String expData = "complexElementData";
        String someExtraTagData = " someValue=1234567890 otherRandomData=!@#$%^&*() some other stuff randomSlashes=\\//\\\\\\//\\/\\";
        String tag = "<" + expData + someExtraTagData + ">";
        
        // Apply
        HtmlData htmlData = parser.parse(tag);
        data = htmlData.getElementData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ElementData_ValidTag_ComplexElementDataWithBrackets() throws IllegalHtmlTagException {
        // Arrange
        String data;
        String expData = "complexElementData";
        String someExtraPostTagData = "/ other D a t aIncluding Brackets=<<<<><>>><<>>> /";
        
        String tag = "</" + expData + someExtraPostTagData + ">";
        // Apply
        HtmlData htmlData = parser.parse(tag);
        data = htmlData.getElementData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testParse_ElementData_ValidTag_WithExtrenousWhiteSpace() throws IllegalHtmlTagException {
        // Arrange
        String data;
        String expData = "someElementData";
        
        String tag = "    \t  <" + expData + "   \t> \t\t    ";
        
        // Apply
        HtmlData htmlData = parser.parse(tag);
        data = htmlData.getElementData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=IllegalHtmlTagException.class)
    public void testParse_InvalidTag_EmptyElementName() throws IllegalHtmlTagException {
        // Arrange
        String tag = "<>";
        
        // Apply + Assert
        parser.parse(tag);
    }
    
    @Test(expected=IllegalHtmlTagException.class)
    public void testParse_InvalidTag_EmptyElementNameWithClosingTag() throws IllegalHtmlTagException {
        // Arrange
        String tag = "</>";
        
        // Apply + Assert
        parser.parse(tag);
    }
    
    @Test(expected=IllegalHtmlTagException.class)
    public void testParse_InvalidTag_WithWhiteSpaceElementName() throws IllegalHtmlTagException {
        // Arrange
        String tag = "<    \t>";
        
        // apply + assert
        parser.parse(tag);
    }
    
    @Test(expected=IllegalHtmlTagException.class)
    public void testParse_InvalidTag_EmptyString() throws IllegalHtmlTagException {
        // Arrange
        String tag = "";
        
        // Apply + Assert
        parser.parse(tag);
    }
    
    @Test(expected=IllegalHtmlTagException.class)
    public void testParse_InvalidTag_MissingClosingBracket() throws IllegalHtmlTagException {
        // Arrange
        String tag = "<sometag";
        
        // Apply + Assert
        parser.parse(tag);
    }
    
    @Test(expected=IllegalHtmlTagException.class)
    public void testParse_InvalidTag_MissingOpeningBracket() throws IllegalHtmlTagException {
        // Arrange
        String tag = "sometag>";
        
        // Apply + Assert
        parser.parse(tag);
    }
    
    @Test(expected=IllegalHtmlTagException.class)
    public void testParse_InvalidTag_StandardString() throws IllegalHtmlTagException {
        // Arrange
        String tag = "someString";
        
        // Apply + Assert
        parser.parse(tag);
    }
}
