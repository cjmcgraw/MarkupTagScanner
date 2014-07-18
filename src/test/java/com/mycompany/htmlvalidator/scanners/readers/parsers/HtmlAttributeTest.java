package com.mycompany.htmlvalidator.scanners.readers.parsers;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.*;

import org.junit.*;

import com.mycompany.htmlvalidator.scanners.MarkupTag;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions.*;

public class HtmlAttributeTest {
    public static final String DEFAULT_NAME = "some name";
    public static final String DEFAULT_VALUE = "some value";
    public static final String EMPTY_VALUE = HtmlAttribute.DEFAULT_EMPTY_VALUES;
    
    public static final ComponentException FIRST_ERROR = new MissingCharacterComponentException('a', new Point(0, 0), "some data");
    public static final ComponentException SECOND_ERROR = new UnexpectedCharacterComponentException('a', new Point(1, 1), "other data");
    public static final ComponentException THIRD_ERROR = new MissingCharacterComponentException('z', new Point(2, 2), "last data");
    
    public static final List<ComponentException> ERRORS = Arrays.asList(FIRST_ERROR, SECOND_ERROR, THIRD_ERROR);
    
    private HtmlAttribute attribute;
    
    @Before
    public void initializeState() {
        this.setState(DEFAULT_NAME, DEFAULT_VALUE);
    }
    
    @Test
    public void testNameConstructor_ReturnsCorrectName() {
        // Arrange
        String expData = "constructed name";
        String data;
        
        this.setState(expData);
        
        // Apply
        data = this.attribute.getName();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testNameConstructor_ReturnsEmptyValue() {
        // Arrange
        String expData = "";
        String data;
        
        this.setState("constructed name");
        
        // Apply
        data = this.attribute.getValue();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetName_ReturnsCorrectName() {
        // Arrange
        String expData = "other name";
        String data;
        
        this.attribute = new HtmlAttribute(DEFAULT_NAME);
        
        // Apply
        this.attribute.setName(expData);
        data = this.attribute.getName();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetValue_ReturnsCorrectValue() {
        // Arrange
        String expData = DEFAULT_VALUE;
        String data;
        
        this.setState(DEFAULT_NAME);
        
        // Apply
        this.attribute.setValue(expData);
        data = this.attribute.getValue();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsFlag_WithNoValueSet_ExpectedTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.setState(DEFAULT_NAME);
        
        // Apply
        data = this.attribute.isFlag();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsFlag_WithValueSet_ExpectedFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.attribute.isFlag();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsClosingFlag_NonClosingTag_NamedFromConstructor_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        // Apply
        data = this.attribute.isClosingFlag();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsClosingFlag_ClosingTag_NamedFromConstructor_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.setState(MarkupTag.CLOSING_ATTRIBUTE.toString());
        
        // Apply
        data = this.attribute.isClosingFlag();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsClosingFlag_NonClosingTag_Set_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.attribute.setName("some other name");
        
        // Apply
        data = this.attribute.isClosingFlag();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsClosingFlag_ClosingTag_Set_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.attribute.setName(MarkupTag.CLOSING_ATTRIBUTE.toString());
        
        // Apply
        data = this.attribute.isClosingFlag();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testIsClosingFlag_StartWithClosingTag_SetNonClosing_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.setState(MarkupTag.CLOSING_ATTRIBUTE.toString());
        
        this.attribute.setName(DEFAULT_NAME);
        
        // Apply
        data = this.attribute.isClosingFlag();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEquals_SameAttributes_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        HtmlAttribute other = new HtmlAttribute(DEFAULT_NAME, DEFAULT_VALUE);
        
        // Apply
        data = this.getSymmetricEqualsPositive(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEquals_DiffName_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        HtmlAttribute other = new HtmlAttribute(DEFAULT_NAME + "diff", DEFAULT_VALUE);
        
        // Apply
        data = this.getSymmetricEqualsNegative(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEquals_DiffValue_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        HtmlAttribute other = new HtmlAttribute(DEFAULT_NAME, DEFAULT_VALUE + "diff");
        
        // Apply
        data = this.getSymmetricEqualsNegative(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testToString_StandardName_StandardValue() {
        // Arrange
        String expData = DEFAULT_NAME + MarkupTag.ATTRIBUTE_VALUE_SEPARATOR.toString() + DEFAULT_VALUE;
        String data;
        
        // Apply
        data = this.attribute.toString();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testToString_StandardName_NoValue() {
        // Arrange
        String expData = DEFAULT_NAME;
        String data;
        
        this.setState(expData);
        
        // Apply
        data = this.attribute.toString();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testToString_NoName_StandardValue() {
        // Arrange
        String expData = MarkupTag.ATTRIBUTE_VALUE_SEPARATOR.toString() + DEFAULT_VALUE;
        String data;
        
        HtmlAttribute attr = new HtmlAttribute();
        attr.setValue(DEFAULT_VALUE);
        
        // Apply
        data = attr.toString();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testToString_NoName_NoValue() {
        // Arrange
        String expData = "";
        String data;
        
        HtmlAttribute attr = new HtmlAttribute();
        
        // Apply
        data = attr.toString();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testToString_ClosingName() {
        // Arrange
        String expData = MarkupTag.CLOSING_ATTRIBUTE.toString();
        String data;
        
        this.attribute.setName(expData);
        
        // Apply
        data = this.attribute.toString();
        
        // Assert
        assertEquals(expData, data);
    }
    
    private boolean getSymmetricEqualsPositive(HtmlAttribute other) {
        return this.attribute.equals(other) &&
               other.equals(this.attribute) &&
               this.attribute.equals((Object) other) &&
               other.equals((Object) this.attribute);
    }
    
    private boolean getSymmetricEqualsNegative(HtmlAttribute other) {
        return this.attribute.equals(other) ||
               other.equals(this.attribute) ||
               this.attribute.equals((Object) other) ||
               other.equals((Object) this.attribute);
    }
    
    private void setState(String name) {
        this.setState(name, EMPTY_VALUE);
    }
    
    private void setState(String name, String value) {
        this.attribute = new HtmlAttribute(name, value);
    }
    
    @After
    public void clearState() {
        this.attribute = null;
    }
}