/*  This file is part of MarkupTagScanner.
 *
 *  MarkupTagScanner is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MarkupTagScanner is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MarkupTagScanner. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycompany.markuptagscanner.readers.parsers.tokens;

import static org.junit.Assert.*;

import java.awt.*;
import java.util.*;
import java.util.List;

import com.mycompany.markuptagscanner.Attribute;
import org.junit.*;

import com.mycompany.markuptagscanner.enums.*;

public class HtmlDataTest {
    public static final String DEFAULT_NAME = "someElement";
    public static final Attribute[] DEFAULT_ATTR = {new HtmlAttribute("someData", "SomeValue"),
                                                    new HtmlAttribute("otherData", "\"OtherValue\""),
                                                    new HtmlAttribute("otherData", "\'other value\'"),
                                                    new HtmlAttribute("lastData", "last Value")};
    public static final boolean DEFAULT_OPENING = true;
    public static final boolean DEFAULT_CLOSING = true;
    public static final boolean DEFAULT_IS_CLOSING = false;
    public static final boolean DEFAULT_IS_SELF_CLOSING = false;
    
    public static final HtmlAttribute DEFAULT_SELF_CLOSING_ATTR = new HtmlAttribute(MarkupTag.CLOSING_ATTRIBUTE.toString());
    public static final HtmlAttribute DEFAULT_EMPTY_ATTR = new HtmlAttribute();
    
    private String defaultName;
    private List<Attribute> defaultAttr;
    private boolean defaultOpening;
    private boolean defaultClosing;
    private boolean isClosing;
    private boolean isSelfClosing;
    private HtmlData data;
    
    @Before
    public void defaultState() {
        this.defaultOpening = DEFAULT_OPENING;
        this.isClosing = DEFAULT_IS_CLOSING;
        this.defaultClosing = DEFAULT_CLOSING;
        
        this.defaultName = DEFAULT_NAME;
        this.defaultAttr = this.generateAttributeListWithDefaultValues();
        
        this.setDataFromState();
    }
    
    @Test
    public void testGetName() {
        // Arrange
        String expData = "some other name";
        String data;
        
        this.defaultName = expData;
        this.setDataFromState();
        
        // Apply
        data = this.data.getName();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testSetName_DefaultData_ImplicitlyTestGetName() {
        testSetName(DEFAULT_NAME);
    }
    
    @Test
    public void testSetName_OverwriteAlreadySetName_implicitlyTestGetName() {
        // Set up
        this.defaultName = "some other name";
        this.setDataFromState();
        
        // Test
        testSetName(DEFAULT_NAME);
    }
    
    private void testSetName(String name) {
        // Apply
        this.data.setName(name);
        
        // Assert
        assertEquals(name, this.data.getName());
    }
    
    @Test
    public void testSetName_NameWithLeadingAndTrailingWhitespace_ImplicitlyTestGetName() {
        // Apply
        this.data.setName(String.format("  \t\t %n some name     \t\t\t %n%n"));
        
        // Assert
        assertEquals("some name", this.data.getName());
    }
    
    @Test
    public void testUpdateName_WithEntireString_ImplicitlyTestGetName() {
        // Arrange
        String added = "some added name";
        String expData = DEFAULT_NAME + added;
        String data;
        
        this.data.setName(DEFAULT_NAME);
        
        // Apply
        this.data.updateName(added);
        data = this.data.getName();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testUpdataName_WithSingleChar_ImplicitlyTestGetName() {
        // Arrange
        char added = 'q';
        String expData = DEFAULT_NAME + added;
        String data;
        
        this.data.setName(DEFAULT_NAME);
        
        // Apply
        this.data.updateName(added);
        data = this.data.getName();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testHasOpening_IsTrue() {
        // Test
        this.testHasOpeningTag(true);
    }
    
    @Test
    public void testHasOpeningTag_IsFalse() {
        // Test
        this.testHasOpeningTag(false);
    }
    
    private void testHasOpeningTag(boolean expValue) {
        // Apply
        this.defaultOpening = expValue;
        this.setDataFromState();
        
        // Assert
        assertEquals(expValue, this.data.hasOpeningBracket());
    }
    
    @Test
    public void testConfirmOpeningTag_ImplicitlyTestHasOpeningTag() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        this.data.confirmOpeningTag();
        data = this.data.hasOpeningBracket();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testHasClosingTag_IsTrue() {
        // Test
        this.testHasClosingTag(true);
    }
    
    @Test
    public void testHasClosingTag_IsFalse() {
        // Test
        this.testHasClosingTag(false);
    }
    
    private void testHasClosingTag(boolean expValue) {
        // Arrange
        this.defaultClosing = expValue;
        this.setDataFromState();
        
        // Assert
        assertEquals(expValue, this.data.hasClosingBracket());
    }
    
    @Test
    public void testConfirmClosingTag_ImplicitlyTestHasClosingTag() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        this.data.confirmClosingTag();
        data = this.data.hasClosingBracket();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testGetAttributes_EmptyList() {
        this.testGetAttribute(new ArrayList<Attribute>());
    }
    
    @Test
    public void testGetAttributes_DefaultList() {
        this.testGetAttribute(Arrays.asList(DEFAULT_ATTR));
    }
    
    @Test
    public void testGetAttributes_OtherAttrs() {
        // Set up
        List<Attribute> expData = new ArrayList<>();
        expData.add(new HtmlAttribute("attr1", "val1"));
        expData.add(new HtmlAttribute("attr2", "val2"));
        expData.add(new HtmlAttribute("attr3", "val3"));
        
        // Test
        this.testGetAttribute(expData);
    }
    
    private void testGetAttribute(List<Attribute> expValue) {
        // Arrange
        this.defaultAttr = expValue;
        this.setDataFromState();
        
        // Assert
        assertEquals(expValue, this.data.getAttributes());
    }
    
    @Test
    public void testSetAttributes_EmptyList_ImplicitlyTestGetAttribute() {
        // Test
        this.testSetAttributes(new ArrayList<Attribute>());
    }
    
    @Test
    public void testSetAttributes_NullList_ResultIsEmptyList_ImplicitlyTestGetAttribute() {
        // Arrange + Apply
        this.data.setAttributes(this.generateAttributeListWithNullValues());
        
        // Assert
        assertEquals(0, this.data.getAttributes().size());
    }
    
    @Test
    public void testSetAttributes_EmptyAttrValues_ResultIsEmptyList_ImplicitlyTestGetAttribute() {
        // Arrange + Apply
        this.data.setAttributes(this.generateAttributeListWithEmptyAttrs());
        
        // Assert
        assertEquals(0, this.data.getAttributes().size());
    }
    
    @Test
    public void testSetAttributes_OtherAttrValues_ImplicitlyTestGetAttribute() {
        // Arrange
        List<Attribute> expData = new ArrayList<>();
        expData.add(new HtmlAttribute("attr1", "val1"));
        expData.add(new HtmlAttribute("attr2", "val2"));
        expData.add(new HtmlAttribute("attr3", "val3"));
        
        // Test
        this.testSetAttributes(expData);
    }
    
    private void testSetAttributes(List<Attribute> expValue) {
        // Apply
        this.data.setAttributes(expValue);
        
        // Assert
        assertEquals(expValue, this.data.getAttributes());
    }
    
    @Test
    public void testUpdateAttributes_SingleAttribute_SingleTime() {
        // Test
        this.testUpdateAttributes_SingleAttr(new HtmlAttribute("new attr"));
    }
    
    @Test
    public void testUpdateAttributes_SingleAttribute_MultipleTimes() {
        // Test
        this.testUpdateAttributes_SingleAttr(new HtmlAttribute("new attr 1"),
                                             new HtmlAttribute("new attr 2"),
                                             new HtmlAttribute("new attr 3"));
    }
    
    private void testUpdateAttributes_SingleAttr(HtmlAttribute... attrs) {
        // Arrange + Apply
        List<Attribute> expData = new ArrayList<>(defaultAttr);
        
        
        for (HtmlAttribute attr : attrs) {
            this.data.updateAttributes(attr);
            expData.add(attr);
        }
        
        // Test
        assertEquals(expData, this.defaultAttr);
    }
    
    @Test
    public void testUpdateAttributes_SingleNullAttribute_ListShouldBeUnchanged() {
        // Set up
        HtmlAttribute attr = null;
        
        // Test
        this.testUpdateAttribute_UnchangedList(attr);
    }
    
    @Test
    public void testUpdateAttributes_MultipleNullAttributes_ListShouldBeUnchanged() {
        // set up
        HtmlAttribute attr = null;
        
        // Test
        this.testUpdateAttribute_UnchangedList(attr, attr, attr, attr, attr);
    }
    
    @Test
    public void testUpdateAttributes_SingleEmptyAttribute_ListShouldBeUnchanged() {
        // Set up
        HtmlAttribute attr = DEFAULT_EMPTY_ATTR;
        
        // Test
        this.testUpdateAttribute_UnchangedList(attr);
    }
    
    @Test
    public void testUpdateAttributes_MultipleEmptyAttributes_ListShouldBeUnchanged() {
        // Set up
        HtmlAttribute attr = DEFAULT_EMPTY_ATTR;
        
        // Test
        this.testUpdateAttribute_UnchangedList(attr, attr, attr, attr, attr);
    }
    
    private void testUpdateAttribute_UnchangedList(HtmlAttribute...attrs) {
        // Arrange
        int expData = this.defaultAttr.size();
        
        // Apply
        for (HtmlAttribute attr: attrs)
            this.data.updateAttributes(attr);
        
        // Assert
        assertEquals(expData, this.defaultAttr.size());
    }
    
    @Test
    public void testUpdateAttributes_ListOfAttributes_SingleAttribute() {
        // Set up
        List<Attribute> expData = new ArrayList<>();
        expData.add(new HtmlAttribute("some new attr"));
        
        // Test
        this.testUpdateAttributes_ListOfAttrs(expData);
    }
    
    @Test
    public void testUpdateAttributes_ListOfAttributes_MultipleAttributes() {
        // Set up
        List<Attribute> expData = new ArrayList<>();
        expData.add(new HtmlAttribute("attr1"));
        expData.add(new HtmlAttribute("attr2"));
        expData.add(new HtmlAttribute("attr3"));
        
        // Test
        this.testUpdateAttributes_ListOfAttrs(expData);
    }
    
    private void testUpdateAttributes_ListOfAttrs(List<Attribute> attrs) {
        // Arrange
        List<Attribute> expData = new ArrayList<>(defaultAttr);
        expData.addAll(attrs);
        
        // Apply
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(expData, this.defaultAttr);
    }
    
    @Test
    public void testUpdateAttributes_ListOfAttributes_SingleNullAttribute_ListShouldBeUnchanged() {
        // Set up
        List<Attribute> attrs = new ArrayList<>();
        attrs.add(null);
        
        // Test
        this.testUpdateAttributes_UnchangedList(attrs);
    }
    
    @Test
    public void testUpdateAttributes_ListOfAttributes_MultipleNullAttributes_ListShouldBeUnchanged() {
        // Set up
        List<Attribute> attrs = this.generateAttributeListWithNullValues();
        
        // Test
        this.testUpdateAttributes_UnchangedList(attrs);
    }
    
    @Test
    public void testUpdateAttributes_ListOfAttributes_ContainsNormalAttributesAndNullAttributes_ListShouldContainValidAttributes() {
        // Set up
        List<Attribute> allAttrs = this.generateAttributeListWithNullValues();
        List<Attribute> validAttrs = this.generateAttributeListWithDefaultValues();
        
        allAttrs.addAll(validAttrs);
        
        // Test
        this.testUpdateAttributes_MixedValidAndInvalid(allAttrs, validAttrs);
    }

    @Test
    public void testUpdateAttributes_ListOfAttributes_SingleEmptyAttribute_ListShouldBeUnchanged() {
        // Set up
        List<Attribute> attrs = new ArrayList<>();
        attrs.add(DEFAULT_EMPTY_ATTR);
        
        // Test
        this.testUpdateAttributes_UnchangedList(attrs);
    }
    
    @Test
    public void testUpdateAttributes_ListOfAttributes_MultipleEmptyAttributes_ListShouldBeUnchanged() {
        // Set up
        List<Attribute> attrs = this.generateAttributeListWithEmptyAttrs();
        
        // Test
        this.testUpdateAttributes_UnchangedList(attrs);
    }
    
    @Test
    public void testUpdateAttributes_ListOfAttributes_ContainsNormalAttributesAndEmptyAttributes_ListShouldContainValidAttributes() {
        // Set up
        List<Attribute> allAttrs = this.generateAttributeListWithEmptyAttrs();
        List<Attribute> validAttrs = this.generateAttributeListWithDefaultValues();
        
        allAttrs.addAll(validAttrs);
        
        // Test
        this.testUpdateAttributes_MixedValidAndInvalid(allAttrs, validAttrs);
    }
    
    private void testUpdateAttributes_UnchangedList(List<Attribute> attrs) {
        // Arrange
        int expData = this.defaultAttr.size();
        
        // Apply
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(expData, this.defaultAttr.size());
    }
    
    private void testUpdateAttributes_MixedValidAndInvalid(List<Attribute> allAttrs, 
                                                           List<Attribute> validAttrs) {
        // Apply
        this.defaultAttr = new ArrayList<>();
        this.setDataFromState();
        
        this.data.updateAttributes(allAttrs);
        
        // Assert
        assertEquals(validAttrs, this.defaultAttr);
    }
    
    @Test
    public void testIsClosing_IsTrue() {
        // Test
        testIsClosing(true);
    }
    
    @Test
    public void testIsClosing_IsFalse() {
        // Test
        testIsClosing(false);
    }
    
    private void testIsClosing(boolean expValue) {
        // Arrange
        this.isClosing = expValue;
        this.setDataFromState();
        
        // Assert
        assertEquals(expValue, this.data.isClosing());
    }

    @Test
    public void testSetIsClosing_SetToTrue_ImplicitlyTestIsClosing() {
        // Test
        this.testSetIsClosing(true);
    }

    @Test
    public void testSetIsClosing_SetToFalse_ImplicitlyTestIsClosing() {
        // Test
        this.testSetIsClosing(false);
    }
    
    private void testSetIsClosing(boolean expValue) {
        // Arrange
        this.data.setIsClosing(expValue);
        
        // Assert
        assertEquals(expValue, this.data.isClosing());
    }
    
    @Test
    public void testIsSelfClosing_IsTrue() {
        // Test
        this.testIsSelfClosing(true);
    }
    
    @Test
    public void testIsSelfClosing_IsFalse() {
        // Test
        this.testIsSelfClosing(false);
    }
    
    private void testIsSelfClosing(boolean expValue) {
        // Arrange
        this.isSelfClosing = expValue;
        this.setDataFromState();
        
        // Assert
        assertEquals(expValue, this.data.isSelfClosing());
    }
    
    @Test
    public void testIsSelfClosing_DefaultData_IsFalse() {
        // Test
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_SingleAttr_NonSelfClosingAttr_ImplicitlyTestIsSelfClosing() {
        // Apply
        this.data.updateAttributes(DEFAULT_ATTR[0]);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_SingleAttr_NullAttr_ImplicitlyTestIsSelfClosing() {
        // Apply
        HtmlAttribute attr = null;
        this.data.updateAttributes(attr);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_SingleAttr_EmptyAttr_ImplicitlyTestIsSelfClosing() {
        // Apply
        this.data.updateAttributes(DEFAULT_EMPTY_ATTR);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_SingleAttr_SelfClosingAttr_ImplicitlyTestIsSelfClosing() {
        // Apply
        this.data.updateAttributes(DEFAULT_SELF_CLOSING_ATTR);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_SingleAttr_MultipleUpdates_FirstNot_SecondNot_ImplicitlyTestIsSelfClosing() {
        // Apply
        this.data.updateAttributes(DEFAULT_ATTR[0]);
        this.data.updateAttributes(DEFAULT_ATTR[0]);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_SingleAttr_MultipleUpdates_FirstSelfClosing_SecondNot_ImplicitlyTestIsSelfClosing() {
        // Apply
        this.data.updateAttributes(DEFAULT_SELF_CLOSING_ATTR);
        this.data.updateAttributes(DEFAULT_ATTR[0]);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_SingleAttr_MultipleUpdates_FirstNot_SecondIsSelfClosing_ImplicitlyTestIsSelfClosing() {
        // Apply
        this.data.updateAttributes(DEFAULT_ATTR[0]);
        this.data.updateAttributes(DEFAULT_SELF_CLOSING_ATTR);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_SingleAttr_MultipleUpdates_FirstIs_SecondIs_ImplicitlyTestIsSelfClosing() {
        // Apply
        this.data.updateAttributes(DEFAULT_SELF_CLOSING_ATTR);
        this.data.updateAttributes(DEFAULT_SELF_CLOSING_ATTR);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_AttrList_NonSelfClosingAttrs_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> attrs = this.generateAttributeListWithDefaultValues();
        
        // Apply
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_AttrList_NullAttrs_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> attrs = this.generateAttributeListWithNullValues();
        
        // Apply
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_AttrList_EmptyAttrs_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> attrs = this.generateAttributeListWithEmptyAttrs();
        
        // Apply
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_AttrList_FirstValueIsSelfClosingAttr_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> attrs = this.generateAttributeListWithDefaultValues();
        attrs.add(0, DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_AttrList_LastValueIsSelfClosingAttr_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> attrs = this.generateAttributeListWithDefaultValues();
        attrs.add(DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_AttrList_MiddleValueIsSelfClosingAttr_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> attrs = this.generateAttributeListWithDefaultValues();
        attrs.set(attrs.size() / 2, DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_AttrList_MultipleUpdates_FirstNot_SecondNot_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> attrs = this.generateAttributeListWithDefaultValues();
        // Apply
        this.data.updateAttributes(attrs);
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_AttrList_MultipleUpdates_FirstIs_SecondNot_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> attrs = this.generateAttributeListWithDefaultValues();
        attrs.add(DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.updateAttributes(attrs);
        
        attrs.remove(attrs.size() - 1);
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_AttrList_MultipleUpdates_FirstNot_SecondIs_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> attrs = this.generateAttributeListWithDefaultValues();
        // Apply
        this.data.updateAttributes(attrs);
        
        attrs.add(DEFAULT_SELF_CLOSING_ATTR);
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_AttrList_MultipleUpdates_FirstIs_SecondIs_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> attrs = this.generateAttributeListWithDefaultValues();
        attrs.add(DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.updateAttributes(attrs);
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_SetAttributes_NonSelfClosingAttrs_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> attrs = this.generateAttributeListWithDefaultValues();
        
        // Apply
        this.data.setAttributes(attrs);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_SetAttributes_NullAttrs_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> attrs = this.generateAttributeListWithNullValues();
        
        // Apply
        this.data.setAttributes(attrs);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_SetAttributes_EmptyAttrs_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> attrs = this.generateAttributeListWithEmptyAttrs();
        
        // Apply
        this.data.setAttributes(attrs);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_SetAttributes_FirstIndexIsSelfClosing_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> attrs = this.generateAttributeListWithDefaultValues();
        attrs.set(0, DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.setAttributes(attrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_SetAttributes_LastIndexIsSelfClosing_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> attrs = this.generateAttributeListWithDefaultValues();
        attrs.add(DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.setAttributes(attrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_SetAttributes_MiddleIndexIsSelfClosing_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> attrs = this.generateAttributeListWithDefaultValues();
        attrs.set(attrs.size() / 2, DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.setAttributes(attrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_SetAttributes_MultipleSets_FirstNot_SecondNot_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> firstAttrs = this.generateAttributeListWithDefaultValues();
        List<Attribute> scndAttrs = this.generateAttributeListWithDefaultValues();
        
        // Apply
        this.data.setAttributes(firstAttrs);
        this.data.setAttributes(scndAttrs);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_SetAttributes_MultipleSets_FirstIs_SecondNot_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> firstAttrs = this.generateAttributeListWithDefaultValues();
        firstAttrs.add(DEFAULT_SELF_CLOSING_ATTR);
        List<Attribute> scndAttrs = this.generateAttributeListWithDefaultValues();
        
        
        // Apply
        this.data.setAttributes(firstAttrs);
        this.data.setAttributes(scndAttrs);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_SetAttributes_MultipleSets_FirstNot_SecondIs_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> firstAttrs = this.generateAttributeListWithDefaultValues();
        List<Attribute> scndAttrs = this.generateAttributeListWithDefaultValues();
        scndAttrs.add(DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.setAttributes(firstAttrs);
        this.data.setAttributes(scndAttrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_SetAttributes_MultipleSets_FirstIs_SecondIs_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<Attribute> firstAttrs = this.generateAttributeListWithDefaultValues();
        firstAttrs.add(DEFAULT_SELF_CLOSING_ATTR);
        List<Attribute> scndAttrs = this.generateAttributeListWithDefaultValues();
        scndAttrs.add(DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.setAttributes(firstAttrs);
        this.data.setAttributes(scndAttrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }

    @Test
    public void testLocation_LocationMatchesExpectedResult() {
        // Arrange
        Point exp = new Point(-1, 1);
        Point result;

        this.data.setLocation(exp);

        // Apply + Assert
        assertEquals(exp, this.data.location());
    }

    @Test
    public void testToString() {
        // Test
        this.testToString("<" + this.defaultName + " " + this.parseAttributesToString() + ">");
    }
    
    @Test
    public void testToString_MissingClosingTag() {
        // Set up
        this.defaultClosing = false;
        
        // Test
        this.testToString("<" + this.defaultName + " " + this.parseAttributesToString() + "[>]");
    }
    
    @Test
    public void testToString_MissingOpeningTag() {
        // Set up
        this.defaultOpening = false;
        
        // Test
        this.testToString("[<]" + this.defaultName + " " + this.parseAttributesToString() + ">");
    }
    
    @Test
    public void testToString_MissingBothOpeningAndClosingTag() {
        // Set up
        this.defaultOpening = false;
        this.defaultClosing = false;
        
        // Test
        this.testToString("[<]" + this.defaultName + " " + this.parseAttributesToString() + "[>]");
    }
    
    @Test
    public void testToString_AlteredElementName() {
        // Setup
        this.defaultName = "newElementName";
        
        // Test
        this.testToString("<" + this.defaultName + " " + this.parseAttributesToString() + ">"); 
    }
    
    @Test
    public void testToString_AlteredClosingValue_True() {
        // Set up
        this.isClosing = true;
        
        // Test
        this.testToString("</" + this.defaultName + " " + this.parseAttributesToString() + ">");
    }
    
    @Test
    public void testToString_AlteredAttributeData_WithNoAttributes() {
        // Set up
        this.defaultAttr = new ArrayList<>();
        
        // Test
        this.testToString("<" + this.defaultName + " >");
    }
    
    @Test
    public void testToString_AlteredAttributeData_WithSingleAttribute() {
        // Set up
        this.defaultAttr = new ArrayList<>();
        this.defaultAttr.add(new HtmlAttribute("newAttribute"));
        
        // Test
        this.testToString("<" + this.defaultName + " newAttribute" + ">");
    }
    
    @Test
    public void testToString_AlteredAttributeData_MorethanMaxAttributes() {
        // Set up
        this.defaultAttr = new ArrayList<Attribute>();
        
        for (int i = 0; i <= HtmlData.MAX_NUM_ATTR_IN_STRING; i++) {
            HtmlAttribute attr = new HtmlAttribute("attr" + i, "value" + i);
            this.defaultAttr.add(attr);
        }
        
        // Test
        this.testToString("<" + this.defaultName + " " + HtmlData.ELIPSIS + ">");
    }
    
    @Test
    public void testToString_CommentTagAsName() {
        // Set up
        this.defaultName = MarkupTagNames.COMMENT_TAG.getBeginName();
        String endName = MarkupTagNames.COMMENT_TAG.getEndName();
        
        // Test
        this.testToString("<" + this.defaultName + " " + this.parseAttributesToString() + endName + ">");
    }
    

    private void testToString(String expValue) {
        // Arrange
        this.setDataFromState();
        
        // Assert
        assertEquals(expValue, this.data.toString());
    }
    
    @Test
    public void testEqualsHtmlDataAndMutableHtmlData_SameData_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.setDataFromState();
        HtmlData other = this.generateData();
        
        // Apply
        data = this.getSymmetricPositiveEquals(this.data, other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEquals_DiffHasOpening_ResultIsFalse() {
        // Set up
        this.defaultOpening = !this.defaultOpening;
        
        // Test
        this.testNegativeEquals();
    }
    
    @Test
    public void testEquals_DiffIsClosing_ResultIsFalse() {
        // Set up
        this.isClosing = !this.isClosing;
        
        // Test
        this.testNegativeEquals();
    }
    
    @Test
    public void testEquals_DiffElementName_ResultIsFalse() {
        // Set up
        this.defaultName = this.defaultName + "otherName";
        
        // Test
        this.testNegativeEquals();
    }
    
    @Test
    public void testEquals_DiffAttrValues_ResultIsFalse() {
        // Set up
        this.defaultAttr = new ArrayList<>();
        this.defaultAttr.add(new HtmlAttribute("otherAttr", "otherName"));
        
        // Test
        this.testNegativeEquals();
    }
    
    @Test
    public void testEquals_DiffHasClosing_ResultIsFalse() {
        // Set up
        this.defaultClosing = !this.defaultClosing;
        
        // Test
        this.testNegativeEquals();
    }
    
    private void testNegativeEquals() {
        // Arrange
        HtmlData other = this.generateData();
        
        // Assert
        assertEquals(false, this.getSymmetricNegativeEquals(this.data, other));
    }
    
    private boolean getSymmetricPositiveEquals(HtmlData current, HtmlData other) {
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
    
    private boolean getSymmetricNegativeEquals(HtmlData current, HtmlData other) {
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
    
    private void setDataFromState() {
        this.data = generateData();
    }
    
    private HtmlData generateData() {
        return new HtmlData(defaultName,
                                   isClosing,
                                   defaultOpening,
                                   defaultClosing,
                                   isSelfClosing,
                                   defaultAttr);
    }
    
    private String parseAttributesToString() {
        String result = "";
        
        for (Attribute data : this.defaultAttr)
            result += data.toString() + " ";
        
        return result.trim();
    }
    
    private List<Attribute> generateAttributeListWithDefaultValues() {
        List<Attribute> result = new ArrayList<>();
        
        for (int i = 0; i < DEFAULT_ATTR.length; i++)
            result.add(DEFAULT_ATTR[i]);
        
        return result;
    }
    
    private List<Attribute> generateAttributeListWithNullValues() {
        List<Attribute> result = new ArrayList<>();
        
        for (int i = 0; i < DEFAULT_ATTR.length; i++)
            result.add(null);
        
        return result;
    }
    
    private List<Attribute> generateAttributeListWithEmptyAttrs() {
        List<Attribute> result = new ArrayList<>();
        
        for (int i = 0; i < DEFAULT_ATTR.length; i++)
            result.add(DEFAULT_EMPTY_ATTR);
        
        return result;
    }
    
    @After
    public void clearState() {
        this.defaultOpening = false;
        this.isClosing = false;
        this.defaultName = null;
        this.defaultAttr = null;
        this.defaultClosing = false;
        this.data = null;
    }
}
