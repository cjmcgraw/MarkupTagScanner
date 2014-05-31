package com.mycompany.htmlvalidator;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import com.mycompany.htmlvalidator.interfaces.HtmlTag;
import com.mycompany.htmlvalidator.HtmlTagImpl;

public class HtmlTagTest {
    private final String elementName = "someElement";
    private final String otherElementName = "otherElement";
    
    private final String openTag = "<" + elementName + ">";
    private final String otherOpenTag = "<" + otherElementName + ">";
    private final String closeTag = "</" + elementName + ">";
    private final String otherCloseTag = "</" + otherElementName + ">";
    
    private HtmlTag tag;
    
    @Before
    public void setUp() {
        this.setState();
    }
    
    @After
    public void tearDown() {
        this.clearState();
    }
    
    @Test
    public void testGetElement_WithNonEmptyElement() {
        // Arrange
        String data;
        String expData = this.elementName;
        
        // Apply
        data = this.tag.getElement();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testGetElement_WithEmptyElement() {
        // Arrange
        String data;
        String expData = "";
        this.setState("<>");
        
        // Apply
        data = this.tag.getElement();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsOpenTag_WithOpenTag() {
        // Arrange
        boolean data;
        boolean expData = true;
        
        // Apply
        data = this.tag.isOpenTag();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsOpenTag_WithCloseTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        
        // Apply
        data = this.tag.isOpenTag();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsSelfClosing_WithSelfClosingTag() {
        // Arrange
        boolean data;
        boolean expData = true;
        
        this.setState("<br />");
        
        // Apply
        data = this.tag.isSelfClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsSelfClosing_WithNonSelfClosingTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        
        // Apply
        data = this.tag.isSelfClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsHtmlTag_WithSameElementSameTag() {
        // Arrange
        boolean data;
        boolean expData = true;
        
        HtmlTag other = this.createTag(this.openTag);
        
        // Apply
        data = this.tag.equals(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsHtmlTag_WithSameElementDiffTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        
        HtmlTag other = this.createTag(this.closeTag);
        
        // Apply
        data = this.tag.equals(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsHtmlTag_WithDiffElementSameTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        
        HtmlTag other = this.createTag(this.otherOpenTag);
        
        // Apply
        data = this.tag.equals(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsHtmlTag_WithDiffElementDiffTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        
        HtmlTag other = this.createTag(this.otherCloseTag);
        
        // Apply
        data = this.tag.equals(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_WithSameElementSameTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        
        HtmlTag other = this.createTag(this.openTag);
        
        // Apply
        data = this.tag.equals(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_WithSameElementDiffTag() {
        // Arrange
        boolean data;
        boolean expData = true;
        
        HtmlTag other = this.createTag(this.closeTag);
        
        // Apply
        data = this.tag.equals(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_WithDiffElementSameTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        
        HtmlTag other = this.createTag(this.otherOpenTag);
        
        // Apply
        data = this.tag.equals(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_WithDiffElementDiffTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        
        HtmlTag other = this.createTag(this.otherCloseTag);
        
        // Apply
        data = this.tag.equals(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    private void setState() {
        this.setState(openTag);
    }
    
    private void setState(String tagData) {
        this.tag = this.createTag(tagData);
    }
    
    private HtmlTag createTag(String tagData) {
        return new HtmlTagImpl(tagData);
    }
    
    private void clearState() {
        this.tag = null;
    }
}
