package com.mycompany.htmlvalidator.scanners.tokens;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.*;

import org.junit.*;

import com.mycompany.htmlvalidator.scanners.readers.parsers.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.EndOfInputParsingException;

public class MutableHtmlTagTest {
    public static final String DEFAULT_NAME = "defaultName";
    public static final String SELF_CLOSING_NAME = MutableHtmlTag.SELF_CLOSING_TAGS.iterator().next();
    
    public static final List<HtmlAttribute> DEFAULT_ATTRIBUTES = 
            Arrays.asList(new HtmlAttribute("firstAttr", "firstValue"), 
                          new HtmlAttribute ("secondAttr"));
    public static final boolean DEFAULT_CLOSING = false;
    public static final boolean DEFAULT_ENCLOSURES = true;
    
    private String defaultName = DEFAULT_NAME;
    private List<HtmlAttribute> defaultAttr = DEFAULT_ATTRIBUTES;
    private boolean defaultIsClosing = DEFAULT_CLOSING;
    private boolean defaultHasOpeningTag = DEFAULT_ENCLOSURES;
    private boolean defaultHasClosingTag = DEFAULT_ENCLOSURES;
    
    private MutableHtmlTag tag;
    
    @Test
    public void testIsClosing_NonSelfClosingNonClosing_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.tag.isClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsClosing_NonSelfClosingIsClosing_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.defaultIsClosing = true;
        this.setStateFromFields();
        
        // Apply
        data = this.tag.isClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsClosing_SelfClosingNonClosing_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.defaultName = SELF_CLOSING_NAME;
        this.setStateFromFields();
        
        // Apply
        data = this.tag.isClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsClosing_SelfClosingIsClosing_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.defaultIsClosing = true;
        this.defaultName = SELF_CLOSING_NAME;
        
        this.setStateFromFields();
        
        // Arrange
        data = this.tag.isClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsSelfClosing_GivenNonSelfClosingName_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.tag.isSelfClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsSelfClosing_GivenSelfClosingName_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.defaultName = SELF_CLOSING_NAME;
        this.setStateFromFields();
        
        // Apply
        data = this.tag.isSelfClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsSymmetric_SameHtmlData_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.symmetricEqualsPositive(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsSymmetric_DiffName_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultName = "otherName";
        
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.symmetricEqualsNegative(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsSymmetric_DiffAttr_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultAttr = Arrays.asList(new HtmlAttribute("otherAttr", "otherValue"));
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.symmetricEqualsNegative(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsSymmetric_DiffIsClosing_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultIsClosing = !this.defaultIsClosing;
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.symmetricEqualsNegative(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsSymmetric_DiffHasOpeningTag_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultHasOpeningTag = !this.defaultHasOpeningTag;
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.symmetricEqualsNegative(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsSymmetric_DiffHasClosingTag_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultHasClosingTag = !this.defaultHasClosingTag;
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.symmetricEqualsNegative(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsSymmetric_SameTag_SingleTagIsInvalid_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        MutableHtmlTag other = this.createHtmlTagWithParsingExceptionFromFields();
        
        // Apply
        data = this.symmetricEqualsNegative(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsSymmetric_SameTag_BothTagsInvalid_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.tag = this.createHtmlTagWithParsingExceptionFromFields();
        MutableHtmlTag other = this.createHtmlTagWithParsingExceptionFromFields();
        
        // Apply
        data = this.symmetricEqualsPositive(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_SameTagWithIsOpening_DefaultIsOpening_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_SameTagWithIsOpening_DefaultIsClosing_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultIsClosing = true;
        this.setStateFromFields();
        
        this.returnFieldsToDefault();
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_SameTagWithIsClosing_DefaultIsOpening_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.defaultIsClosing = true;
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_SameTagWithIsClosing_DefaultIsClosing_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultIsClosing = true;
        this.setStateFromFields();
        
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_DiffTagElementNameWithIsOpening_DefaultIsOpening_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultName += "_different";
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_DiffTagElementNameWithIsOpening_DefaultIsClosing_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultIsClosing = true;
        this.setStateFromFields();
        
        this.returnFieldsToDefault();
        this.defaultName += "_different";
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_DiffTagElementNameWithIsClosing_DefaultIsOpening_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultName += "_different";
        this.defaultIsClosing = true;
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_DiffTagElementNameWithIsClosing_DefaultIsClosing_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultIsClosing = true;
        this.setStateFromFields();
        
        this.defaultName += "_different";
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_DiffTagAttributeWithIsOpening_DefaultIsOpening_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultAttr = Arrays.asList(new HtmlAttribute("different"));
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_DiffTagAttributeWithIsOpening_DefaultIsClosing_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultIsClosing = true;
        this.setStateFromFields();
        
        this.returnFieldsToDefault();
        this.defaultAttr = Arrays.asList(new HtmlAttribute("different"));
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_DiffTagAttributeWithIsClosing_DefaultIsOpening_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.defaultIsClosing = true;
        this.defaultAttr = Arrays.asList(new HtmlAttribute("different"));
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_DiffTagAttributeWithIsClosing_DefaultIsClosing_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultIsClosing = true;
        this.setStateFromFields();
        
        this.defaultAttr = Arrays.asList(new HtmlAttribute("different"));
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_InvalidTag_SameTagWithIsClosingMissingOpeningTag_DefaultIsOpening_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultIsClosing = true;
        this.defaultHasOpeningTag = false;
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_InvalidTag_SameTagWithIsClosingMissingClosingTag_DefaultIsOpening_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultIsClosing = true;
        this.defaultHasClosingTag = false;
        MutableHtmlTag other = this.createHtmlTagFromFields();
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMatches_InvalidTag_SameTagWithIsClosingParsingException_DefaultIsOpening_ReusltIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        MutableHtmlTag other = this.createHtmlTagWithParsingExceptionFromFields();
        
        // Apply
        data = this.tag.matches(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetData_DiffElementName_ResultIsChanged() {
        // Arrange
        String expData = "differentName";
        String data;
        
        this.defaultName = expData;
        MutableHtmlData diffData = this.createHtmlDataFromFields();
        
        // Apply
        this.tag.setData(diffData);
        data = this.tag.getName();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetData_DiffIsClosing_ResultIsChanged() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.defaultIsClosing = expData;
        MutableHtmlData diffData = this.createHtmlDataFromFields();
        
        // Apply
        this.tag.setData(diffData);
        data = this.tag.isClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetData_DiffIsClosing_SelfClosingIsUnchanged() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultIsClosing = !expData;
        MutableHtmlData diffData = this.createHtmlDataFromFields();
        
        // Apply
        this.tag.setData(diffData);
        data = this.tag.isSelfClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetData_DiffSelfClosing_ResultIsChanged() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.defaultName = SELF_CLOSING_NAME;
        MutableHtmlData diffData = this.createHtmlDataFromFields();
        
        // Apply
        this.tag.setData(diffData);
        data = this.tag.isSelfClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetData_DiffSelfClosing_IsClosingIsChanged() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.defaultName = SELF_CLOSING_NAME;
        MutableHtmlData diffData = this.createHtmlDataFromFields();
        
        // Apply
        this.tag.setData(diffData);
        data = this.tag.isClosing();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetData_DiffAttr_ResultIsChanged() {
        // Arrange
        List<HtmlAttribute> expData = Arrays.asList(new HtmlAttribute("different"));
        List<HtmlAttribute> data;
        
        this.defaultAttr = expData;
        MutableHtmlData diffData = this.createHtmlDataFromFields();
        
        // Apply
        this.tag.setData(diffData);
        data = this.tag.getAttributes();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testValidate_ValidTag_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        data = this.tag.validate();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test 
    public void testValidate_InvalidTag_MissingOpeningTag_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultHasOpeningTag = false;
        this.setStateFromFields();
        
        // Apply
        data = this.tag.validate();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testValidate_InvalidTag_MissingClosingTag_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.defaultHasClosingTag = false;
        this.setStateFromFields();
        
        // Apply
        data = this.tag.validate();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testValidate_InvalidTag_ParsingExceptionTag_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        MutableHtmlTag tag = this.createHtmlTagWithParsingExceptionFromFields();
        
        // Apply
        data = tag.validate();
        
        // Assert
        assertEquals(expData, data);
    }
    
    private boolean symmetricEqualsPositive(MutableHtmlTag other) {
        return (this.tag.equals(other) && 
                other.equals(this.tag) && 
                this.tag.equals((Object) other) && 
                other.equals((Object) this.tag));
    }
    
    private boolean symmetricEqualsNegative(MutableHtmlTag other) {
        return (this.tag.equals(other) || 
                other.equals(this.tag) || 
                this.tag.equals((Object) other) || 
                other.equals((Object) this.tag));
    }
    
    @Before
    public void setStateFromFields() {
        this.tag = this.createHtmlTagFromFields();
    }
    
    private MutableHtmlData createHtmlDataFromFields() {
        MutableHtmlData data = new MutableHtmlData();
        
        data.setName(this.defaultName);
        data.setAttributes(this.defaultAttr);
        data.setIsClosing(this.defaultIsClosing);
        
        if (this.defaultHasOpeningTag)
            data.confirmOpeningTag();
        if (this.defaultHasClosingTag)
            data.confirmClosingTag();
        
        return data;
    }
    
    private MutableHtmlTag createHtmlTagFromFields() {
        return new MutableHtmlTag(this.createHtmlDataFromFields());
    }
    
    private MutableHtmlTag createHtmlTagWithParsingExceptionFromFields() {
        HtmlData data = new EndOfInputParsingException(new Point(0, 0), this.createHtmlDataFromFields());
        return new MutableHtmlTag(data);
    }
    
    @After
    public void clearState() {
        this.tag = null;
        this.returnFieldsToDefault();
    }
    
    private void returnFieldsToDefault() {
        this.defaultName = DEFAULT_NAME;
        this.defaultAttr = DEFAULT_ATTRIBUTES;
        this.defaultIsClosing = DEFAULT_CLOSING;
        this.defaultHasOpeningTag = DEFAULT_ENCLOSURES;
        this.defaultHasClosingTag = DEFAULT_ENCLOSURES;
    }
}