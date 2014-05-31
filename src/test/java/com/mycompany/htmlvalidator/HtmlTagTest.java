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
    private final boolean openFlag = false;
    private final boolean closeFlag = !openFlag;
    
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
    public void testGetElement() {
        // Arrange
        String data;
        String expData = this.elementName;
        
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
        
        this.setState(this.elementName, !this.openFlag);
        
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
        
        this.setState("br");
        
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
        
        HtmlTag other = this.createHtmlTag(this.elementName, this.openFlag);
        
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
        
        HtmlTag other = this.createHtmlTag(this.elementName, this.closeFlag);
        
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
        
        HtmlTag other = this.createHtmlTag(this.otherElementName, this.openFlag);
        
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
        
        HtmlTag other = this.createHtmlTag(this.otherElementName, this.closeFlag);
        
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
        
        HtmlTag other = this.createHtmlTag(this.elementName, this.openFlag);
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_WithSameElementDiffTag() {
        // Arrange
        boolean data;
        boolean expData = true;
        
        HtmlTag other = this.createHtmlTag(this.elementName, this.closeFlag);
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_WithDiffElementSameTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        
        HtmlTag other = this.createHtmlTag(this.otherElementName, this.openFlag);
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_WithDiffElementDiffTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        
        HtmlTag other = this.createHtmlTag(this.otherElementName, this.closeFlag);
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    private void setState() {
        this.setState(this.elementName);
    }
    
    private void setState(String element) {
        this.setState(element, this.openFlag);
    }
    
    private void setState(String element, boolean openFlag) {
        this.tag = this.createHtmlTag(element, openFlag);
    }
    
    private HtmlTag createHtmlTag(String element, boolean openFlag) {
        return new HtmlTagImpl(element, openFlag);
    }
    
    private void clearState() {
        this.tag = null;
    }
}
