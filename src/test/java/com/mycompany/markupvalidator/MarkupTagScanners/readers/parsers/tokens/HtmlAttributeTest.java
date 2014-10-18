/*  This file is part of MarkupValidator.
 *
 *  MarkupValidator is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MarkupValidator is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MarkupValidator. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.*;

import com.mycompany.markupvalidator.errors.*;
import org.junit.*;

import com.mycompany.markupvalidator.MarkupTagScanners.enums.MarkupTag;

public class HtmlAttributeTest {
    public static final String DEFAULT_NAME = "some name";
    public static final String DEFAULT_VALUE = "some value";
    public static final String EMPTY_VALUE = HtmlAttribute.DEFAULT_EMPTY_VALUES;
    public static final String WHITESPACE = String.format("    \t\t    \t\t %n%n  \t \t ");
    
    public static final ComponentError FIRST_ERROR = new MissingCharacterComponentError('a', new Point(0, 0), "some data");
    public static final ComponentError SECOND_ERROR = new UnexpectedCharacterComponentError('a', new Point(1, 1), "other data");
    public static final ComponentError THIRD_ERROR = new MissingCharacterComponentError('z', new Point(2, 2), "last data");
    
    public static final List<ComponentError> ERRORS = Arrays.asList(FIRST_ERROR, SECOND_ERROR, THIRD_ERROR);
    
    private HtmlAttribute attribute;
    
    @Before
    public void initializeState() {
        this.setState(DEFAULT_NAME, DEFAULT_VALUE);
    }
    
    @Test
    public void testNameConstructor_ReturnsCorrectName_DependsOnGetName() {
        // Arrange
        String expData = "constructed name";
        
        // Apply
        this.setState(expData);
        
        // Assert
        assertEquals(expData, this.attribute.getName());
    }
    
    @Test
    public void testNameConstructor_ReturnsEmptyValue_DependsOnGetValue() {
        // Apply
        this.setState("constructed name");
        
        // Assert
        assertEquals(EMPTY_VALUE, this.attribute.getValue());
    }
    
    @Test
    public void testGetName_DefaultName() {
        // Assert
        assertEquals(DEFAULT_NAME, this.attribute.getName());
    }
    
    @Test
    public void testGetName_OtherName() {
        // Arrange
        String expData = "other name";
        
        // Apply
        this.setState(expData);
        
        // Assert
        assertEquals(expData, this.attribute.getName());
    }
    
    @Test
    public void testSetName_DependsOnGetName() {
        // Arrange
        String expData = "other name";
        
        // Apply
        this.attribute.setName(expData);
        
        // Assert
        assertEquals(expData, this.attribute.getName());
    }
    
    @Test
    public void testGetValue_DefaultValue() {
        // Assert
        assertEquals(DEFAULT_VALUE, this.attribute.getValue());
    }
    
    @Test
    public void testGetValue_OtherValue() {
        // Arrange
        String expData = "other value";
        
        // Apply
        this.setState(DEFAULT_NAME, expData);
        
        // Assert
        assertEquals(expData, this.attribute.getValue());
    }
    
    @Test
    public void testSetName_LeadingAndTrailingWhitespace_DependsOnGetName() {
        // Arrange
        String expData = "some name with whitespace";
        
        // Apply
        this.attribute.setName(WHITESPACE + expData + WHITESPACE);
        
        // Assert
        assertEquals(expData, this.attribute.getName());
    }
    
    @Test
    public void testSetValue_ReturnsCorrectValue_DependsOnGetValue() {
        // Arrange
        String expData = "other value";
        
        // Apply
        this.attribute.setValue(expData);
        
        // Assert
        assertEquals(expData, this.attribute.getValue());
    }
    
    @Test
    public void testSetValue_LeadingAndTrailingWhitespace_DependsOnGetValue() {
        // Arrange
        String expData = "some value with whitespace";
        
        // Apply
        this.attribute.setValue(WHITESPACE + expData + WHITESPACE);
        
        // Assert
        assertEquals(expData, this.attribute.getValue());
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
    public void testIsEmpty_WithEmptyAttribute_FromEmptyConstructor() {
        // Arrange
        HtmlAttribute attr = new HtmlAttribute();
        
        // Assert
        assertEquals(true, attr.isEmpty());
    }
    
    @Test
    public void testIsEmpty_WithNonEmptyName_FromConstructor() {
        // Arrange
        this.setState("some name");
        
        // Assert
        assertEquals(false, this.attribute.isEmpty());
    }
    
    @Test
    public void testIsEmpty_WithNonEmptyValue_FromConstructor() {
        // Arrange
        this.setState(EMPTY_VALUE, "some value");
        
        // Assert
        assertEquals(false, this.attribute.isEmpty());
    }
    
    @Test
    public void testIsEmpty_WithEmptyAttribute_FromSet() {
        // Arrange
        this.attribute.setName(EMPTY_VALUE);
        this.attribute.setValue(EMPTY_VALUE);
        
        // Assert
        assertEquals(true, this.attribute.isEmpty());
    }
    
    @Test
    public void testIsEmpty_WithNonEmptyName_FromSet() {
        // Arrange
        this.attribute.setValue(EMPTY_VALUE);
        
        // Assert
        assertEquals(false, this.attribute.isEmpty());
    }
    
    @Test
    public void testIsEmpty_WithNonEmptyValue_FromSet() {
        // Arrange
        this.attribute.setName(EMPTY_VALUE);
        
        // Assert
        assertEquals(false, this.attribute.isEmpty());
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
