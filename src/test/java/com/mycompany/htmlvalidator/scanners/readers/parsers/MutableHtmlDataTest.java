package com.mycompany.htmlvalidator.scanners.readers.parsers;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlAttribute;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.MutableHtmlData;

public class MutableHtmlDataTest {
    public final String DEFAULT_NAME = "someElement";
    public final HtmlAttribute[] DEFAULT_ATTR = {new HtmlAttribute("someData", "SomeValue"), 
                                                 new HtmlAttribute("otherData", "\"OtherValue\"")};
    public final boolean DEFAULT_OPENING = true;
    public final boolean DEFAULT_CLOSING = true;
    public final boolean DEFAULT_CLOSING_TAG = false;
    
    private String defaultName = DEFAULT_NAME;
    private List<HtmlAttribute> defaultAttr = Arrays.asList(DEFAULT_ATTR);
    private boolean defaultOpening = DEFAULT_OPENING;
    private boolean defaultClosing = DEFAULT_CLOSING;
    private boolean closingTag = DEFAULT_CLOSING_TAG;
    private MutableHtmlData data = new MutableHtmlData();
    
    
    @Test
    public void testToString() {
        // Arrange
        String expData = "<" + this.defaultName + " " + this.parseAttributesToString() + ">";
        String data;
        
        // Apply
        this.updateData();
        data = this.data.toString();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testToString_MissingClosingTag() {
        // Arrange
        String expData = "<" + this.defaultName + " " + this.parseAttributesToString() + "[>]";
        String data; 
        
        this.defaultClosing = false;
        
        // Apply
        this.updateData();
        data = this.data.toString();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testToString_MissingOpeningTag() {
        // Arrange
        String expData = "[<]" + this.defaultName + " " + this.parseAttributesToString() + ">";
        String data;
        
        this.defaultOpening = false;
        
        // Apply
        this.updateData();
        data = this.data.toString();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testToString_MissingBothOpeningAndClosingTag() {
        // Arrange
        String expData = "[<]" + this.defaultName + " " + this.parseAttributesToString() + "[>]";
        String data;
        
        this.defaultOpening = false;
        this.defaultClosing = false;
        
        // Apply
        this.updateData();
        data = this.data.toString();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testToString_AlteredElementName() {
        // Arrange
        this.defaultName = "newElementName";
        
        String expData = "<" + this.defaultName + " " + this.parseAttributesToString() + ">"; 
        String data;
        
        // Apply
        this.updateData();
        data = this.data.toString();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testToString_AlteredClosingValue_True() {
        // Arrange
        String expData = "</" + this.defaultName + " " + this.parseAttributesToString() + ">";
        String data;
        
        this.closingTag = true;
        
        // Apply
        this.updateData();
        data = this.data.toString();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testToString_AlteredAttributeData_WithSingleAttribute() {
        // Arrange
        this.defaultAttr = Arrays.asList(new HtmlAttribute("newAttribute", ""));
        
        String expData = "<" + this.defaultName + " newAttribute" + ">";
        String data;
        
        // Apply
        this.updateData();
        data = this.data.toString();
        
        //Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testToString_AlteredAttributeData_MorethanTwoAttributes() {
        // Arrange
        this.defaultAttr = Arrays.asList(new HtmlAttribute("firstAttr", "\"firstValue\""),
                                         new HtmlAttribute("secondAttr", "secondValue"),
                                         new HtmlAttribute("thirdAttr", ""));
        
        String expData = "<" + this.defaultName + " ..." + ">";
        String data;
        
        // Apply
        this.updateData();
        data = this.data.toString();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsHtmlDataAndMutableHtmlData_SameData_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.updateData();
        MutableHtmlData other = this.setData(new MutableHtmlData());
        
        // Apply
        data = this.getSymmetricPositiveEquals(this.data, other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEquals_DiffHasOpening_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.updateData();
        
        this.defaultOpening = !this.defaultOpening;
        MutableHtmlData other = this.setData();
        
        // Apply
        data = this.getSymmetricNegativeEquals(this.data, other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEquals_DiffIsClosing_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.updateData();
        
        this.closingTag = !this.closingTag;
        MutableHtmlData other = this.setData();
        
        // Apply
        data = this.getSymmetricNegativeEquals(this.data, other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEquals_DiffElementName_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.updateData();
        
        this.defaultName = this.defaultName + "otherName";
        MutableHtmlData other = this.setData();
        
        // Apply
        data = this.getSymmetricNegativeEquals(this.data, other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEquals_DiffAttrValues_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.updateData();
        
        this.defaultAttr = Arrays.asList(new HtmlAttribute("otherAttr", "otherName"));
        MutableHtmlData other = this.setData();
        
        // Apply
        data = this.getSymmetricNegativeEquals(this.data, other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEquals_DiffHasClosing_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.updateData();
        
        this.defaultClosing = !this.defaultClosing;
        MutableHtmlData other = this.setData();
        
        // Apply
        data = this.getSymmetricNegativeEquals(this.data, other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    private void updateData() {
        this.data = setData(this.data);
    }
    
    private boolean getSymmetricPositiveEquals(MutableHtmlData current, MutableHtmlData other) {
        boolean result = true;
        
        HtmlData currentHtmlData = current;
        HtmlData otherHtmlData = other;
        
        Object obj1 = current;
        Object obj2 = other;
        
        // Test Symmetric with regards to MutableHtmlData
        result = result && current.equals(other) && other.equals(current);
        
        // Test Symmetric with regards to HtmlData and MutableHtmlData
        result = result && currentHtmlData.equals(other) && other.equals(currentHtmlData);
        
        // Test Symmetric with regards to MutableHtmlData and HtmlData
        result = result && current.equals(otherHtmlData) && otherHtmlData.equals(current);
        
        // Test Symmetric with regards to HtmlData and HtmlData
        result = result && currentHtmlData.equals(otherHtmlData) && otherHtmlData.equals(currentHtmlData);
        
        // Test Symmetric with regards to objects
        result = result && obj1.equals(obj2) && obj2.equals(obj1);
        
        return result;
    }
    
    private boolean getSymmetricNegativeEquals(MutableHtmlData current, MutableHtmlData other) {
        boolean result = false;
        
        HtmlData currentHtmlData = current;
        HtmlData otherHtmlData = other;
        
        Object obj1 = current;
        Object obj2 = other;
        
        // Test Symmetric with regards to MutableHtmlData
        result = result || current.equals(other) || other.equals(current);
        
        // Test Symmetric with regards to HtmlData and MutableHtmlData
        result = result || currentHtmlData.equals(other) || other.equals(currentHtmlData);
        
        // Test Symmetric with regards to MutableHtmlData and HtmlData
        result = result || current.equals(otherHtmlData) || otherHtmlData.equals(current);
        
        // Test Symmetric with regards to HtmlData
        result = result || currentHtmlData.equals(otherHtmlData) || otherHtmlData.equals(currentHtmlData);
        
        // Test Symmetric with regards to objects
        result = result || obj1.equals(obj2) || obj2.equals(obj1);
        
        return result;
    }
    
    private MutableHtmlData setData() {
        return this.setData(new MutableHtmlData());
    }
    
    private MutableHtmlData setData(MutableHtmlData data) {
        if (this.defaultOpening)
            data.confirmOpeningTag();
        
        data.setIsClosing(this.closingTag);
        data.setName(this.defaultName);
        data.setAttributes(this.defaultAttr);
        
        if (this.defaultClosing)
            data.confirmClosingTag();
        
        return data;
    }
    
    private String parseAttributesToString() {
        String result = "";
        
        for (HtmlAttribute data : this.defaultAttr)
            result += data.toString() + " ";
        
        return result.trim();
    }
    
    @After
    public void resetState() {
        this.defaultOpening = DEFAULT_OPENING;
        this.closingTag = DEFAULT_CLOSING_TAG;
        this.defaultName = DEFAULT_NAME;
        this.defaultAttr = Arrays.asList(DEFAULT_ATTR);
        this.defaultClosing = DEFAULT_CLOSING;
        this.data = new MutableHtmlData();
    }
}
