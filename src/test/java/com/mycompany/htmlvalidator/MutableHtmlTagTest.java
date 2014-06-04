package com.mycompany.htmlvalidator;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import com.mycompany.htmlvalidator.interfaces.HtmlTag;
import com.mycompany.htmlvalidator.MutableHtmlTagImpl;
import com.mycompany.htmlvalidator.interfaces.MutableHtmlTag;
import com.mycompany.htmlvalidator.parsers.utilities.HtmlData;

public class MutableHtmlTagTest {
    private final String elementName = "someElement";
    private final String otherElementName = "otherElement";
    private final String selfClosingElementName = "br";
    private final boolean openFlag = true;
    private final boolean closeFlag = !openFlag;
    
    private HtmlTag tag;
    
    @Before
    public void setUp() {
        this.newState();
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
        
        this.newState(this.elementName, this.closeFlag);
        
        // Apply
        data = this.tag.isOpenTag();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsOpenTag_WithSelfClosingTagAndOpenTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        
        this.newState(this.selfClosingElementName, this.openFlag);
        
        // Apply
        data = this.tag.isOpenTag();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsOpenTag_WithSelfClosingTagAndCloseTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        
        this.newState(this.selfClosingElementName, this.closeFlag);
        
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
        
        this.newState(selfClosingElementName);
        
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
    public void testMatches_MainTagOpenNonSelfClosing_WithSameElementSameTag() {
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
    public void testMatches_MainTagOpenNonSelfClosing_WithSameElementDiffTag() {
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
    public void testMatches_MainTagOpenNonSelfClosing_WithDiffElementSameTag() {
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
    public void testMatches_MainTagOpenNonSelfClosing_WithDiffElementDiffTag() {
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
    public void testMatches_MainTagCloseNonSelfClosing_WithSameElementSameTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        this.newState(this.elementName, this.closeFlag);
        
        HtmlTag other = this.createHtmlTag(this.elementName, this.closeFlag);
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_MainTagCloseNonSelfClosing_WithSameElementDiffTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        this.newState(this.elementName, this.closeFlag);
        
        HtmlTag other = this.createHtmlTag(this.elementName, this.openFlag);
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_MainTagCloseNonSelfClosing_WithDiffElementSameTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        this.newState(this.elementName, this.closeFlag);
        
        HtmlTag other = this.createHtmlTag(this.otherElementName, this.closeFlag);
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_MainTagCloseNonSelfClosing_WithDiffElementDiffTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        this.newState(this.elementName, this.closeFlag);
        
        HtmlTag other = this.createHtmlTag(this.otherElementName, this.openFlag);
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_MainTagSelfClosing_WithSameElementDiffTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        this.newState(this.selfClosingElementName, this.openFlag);
        
        HtmlTag other = this.createHtmlTag(this.selfClosingElementName, this.closeFlag);
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_MainTagSelfClosing_WithSameElementSameTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        this.newState(this.selfClosingElementName, this.openFlag);
        
        HtmlTag other = this.createHtmlTag(this.selfClosingElementName, this.openFlag);
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_MainTagSelfClosing_WithDiffElementSameTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        this.newState(this.selfClosingElementName, this.openFlag);
        
        HtmlTag other = this.createHtmlTag(this.otherElementName, this.openFlag);
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_MainTagSelfClosing_WithDiffElementDiffTag() {
        // Arrange
        boolean data;
        boolean expData = false;
        this.newState(this.selfClosingElementName, this.openFlag);
        
        HtmlTag other = this.createHtmlTag(this.otherElementName, this.closeFlag);
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetData_MainTag_SetStandardElement() {
        // Arrange
        String data;
        String expData = "someElement that has been changed";
        
        // Apply
        this.setState(expData);
        data = this.tag.getElement();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetData_MainTag_SetElementWithTrailingWhiteSpace() {
        // Arrange
        String data;
        String expData = "someElement with no trailing whitespace";
        String trailingWhiteSpace = "  \t \n";
        
        // Apply
        this.setState(trailingWhiteSpace + expData + trailingWhiteSpace);
        data = this.tag.getElement();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testSetData_MainTag_SetInvalidElement_Null() {
        // Arrange
        String expData = null;
        
        // Apply + Assert
        this.setState(expData);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testSetData_MainTag_SetInvalidElement_EmptyString() {
        // Arrange
        String expData = "";
        
        // Apply + Assert
        this.setState(expData);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testSetData_MainTag_SetInvalidElement_WhiteSpaceOnly() {
        // Arrange
        String expData = "    \t \n";
        
        // Apply + Assert
        this.setState(expData);
    }
    
    @Test
    public void testSetData_MainTag_SetSelfClosing_SelfClosingElement() {
        // Arrange
       boolean data;
       boolean expData = true;
       String selfClosingElement = "br";
       
       // Apply
       this.setState(selfClosingElement);
       data = this.tag.isSelfClosing();
       
       // Assert
       assertEquals(data, expData);
    }
    
    @Test
    public void testSetData_MainTag_SetSelfClosing_NonSelfClosingElement() {
        // Arrange
        boolean data;
        boolean expData = false;
        String nonSelfClosingElement = "someNonSelfClosingElement";
        
        // Apply
        this.setState(nonSelfClosingElement);
        data = this.tag.isSelfClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetData_MainTag_SetSelfClosing_UnaffectedByBooleanArgumentTrue () {
        // Arrange
        boolean data;
        boolean expData = false;
        boolean isOpenTag = true;
        
        // Apply
        this.setState(isOpenTag);
        data = this.tag.isSelfClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetData_MainTag_SetSelfClosing_UnaffectedByBooleanArgumentFalse() {
        // Arrange
        boolean data;
        boolean expData = false;
        boolean isOpenTag = false;
        
        // Apply
        this.setState(isOpenTag);
        data = this.tag.isSelfClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetData_MainTag_SetIsOpen_ToTrue() {
        //Arrange
        boolean data;
        boolean expData = true;
        
        // Apply
        this.setState(expData);
        data = this.tag.isOpenTag();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetData_MainTag_SetIsOpen_ToFalse() {
        // Arrange
        boolean data;
        boolean expData = false;
        
        // Apply
        this.setState(expData);
        data = this.tag.isOpenTag();
        
        // Assert
        assertEquals(expData, data);
    }
    
    private void setState(String element) {
        this.setState(element, this.tag.isOpenTag());
    }
    
    private void setState(boolean openFlag) {
        this.setState(this.tag.getElement(), openFlag);
    }
    
    private void setState(String element, boolean openFlag) {
        HtmlData data = new HtmlData(element, openFlag);
        MutableHtmlTag tag = (MutableHtmlTag) this.tag;
        tag.setData(data);
    }
    
    private void newState() {
        this.newState(this.elementName);
    }
    
    private void newState(String element) {
        this.newState(element, this.openFlag);
    }
    
    private void newState(String element, boolean openFlag) {
        this.tag = this.createHtmlTag(element, openFlag);
    }
    
    private HtmlTag createHtmlTag(String element, boolean openFlag) {
        HtmlData data = new HtmlData(element, openFlag);
        
        MutableHtmlTag tag = new MutableHtmlTagImpl(data);
        
        return tag;
    }
    
    private void clearState() {
        this.tag = null;
    }
}
