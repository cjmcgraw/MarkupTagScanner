package com.mycompany.htmlvalidator.scanners.readers.parsers;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import com.mycompany.htmlvalidator.scanners.*;

public class MutableHtmlDataTest {
    public final String DEFAULT_NAME = "someElement";
    public final HtmlAttribute[] DEFAULT_ATTR = {new HtmlAttribute("someData", "SomeValue"), 
                                                 new HtmlAttribute("otherData", "\"OtherValue\""),
                                                 new HtmlAttribute("otherData", "\'other value\'"),
                                                 new HtmlAttribute("lastData", "last Value")};
    public final boolean DEFAULT_OPENING = true;
    public final boolean DEFAULT_CLOSING = true;
    public final boolean DEFAULT_IS_CLOSING = false;
    public final boolean DEFAULT_IS_SELF_CLOSING = false;
    
    public final HtmlAttribute DEFAULT_SELF_CLOSING_ATTR = new HtmlAttribute(MarkupTag.CLOSING_ATTRIBUTE.toString());
    
    private String defaultName;
    private List<HtmlAttribute> defaultAttr;
    private boolean defaultOpening;
    private boolean defaultClosing;
    private boolean isClosing;
    private boolean isSelfClosing;
    private MutableHtmlData data;
    
    @Before
    public void defaultState() {
        this.defaultOpening = DEFAULT_OPENING;
        this.isClosing = DEFAULT_IS_CLOSING;
        this.defaultClosing = DEFAULT_CLOSING;
        
        this.defaultName = DEFAULT_NAME;
        this.defaultAttr = this.getDefaultAttrData();
        
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
        assertEquals(expValue, this.data.hasOpeningTag());
    }
    
    @Test
    public void testConfirmOpeningTag_ImplicitlyTestHasOpeningTag() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        this.data.confirmOpeningTag();
        data = this.data.hasOpeningTag();
        
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
        assertEquals(expValue, this.data.hasClosingTag());
    }
    
    @Test
    public void testConfirmClosingTag_ImplicitlyTestHasClosingTag() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        // Apply
        this.data.confirmClosingTag();
        data = this.data.hasClosingTag();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testGetAttributes_EmptyList() {
        this.testGetAttribute(new ArrayList<HtmlAttribute>());
    }
    
    @Test
    public void testGetAttributes_DefaultList() {
        this.testGetAttribute(Arrays.asList(DEFAULT_ATTR));
    }
    
    @Test
    public void testGetAttributes_OtherAttrs() {
        // Set up
        List<HtmlAttribute> expData = new ArrayList<>();
        expData.add(new HtmlAttribute("attr1", "val1"));
        expData.add(new HtmlAttribute("attr2", "val2"));
        expData.add(new HtmlAttribute("attr3", "val3"));
        
        // Test
        this.testGetAttribute(expData);
    }
    
    private void testGetAttribute(List<HtmlAttribute> expValue) {
        // Arrange
        this.defaultAttr = expValue;
        this.setDataFromState();
        
        // Assert
        assertEquals(expValue, this.data.getAttributes());
    }
    
    @Test
    public void testSetAttributes_EmptyList_ImplicitlyTestGetAttribute() {
        // Test
        this.testSetAttributes(new ArrayList<HtmlAttribute>());
    }
    
    @Test
    public void testSetAttributes_OtherAttrValues_ImplicitlyTestGetAttribute() {
        // Arrange
        List<HtmlAttribute> expData = new ArrayList<>();
        expData.add(new HtmlAttribute("attr1", "val1"));
        expData.add(new HtmlAttribute("attr2", "val2"));
        expData.add(new HtmlAttribute("attr3", "val3"));
        
        // Test
        this.testSetAttributes(expData);
    }
    
    private void testSetAttributes(List<HtmlAttribute> expValue) {
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
        List<HtmlAttribute> expData = new ArrayList<>(defaultAttr);
        
        
        for (HtmlAttribute attr : attrs) {
            this.data.updateAttributes(attr);
            expData.add(attr);
        }
        
        // Test
        assertEquals(expData, this.defaultAttr);
    }
    
    @Test
    public void testUpdateAttributes_ListOfAttributes_SingleAttribute() {
        // Set up
        List<HtmlAttribute> expData = new ArrayList<>();
        expData.add(new HtmlAttribute("some new attr"));
        
        // Test
        this.testUpdateAttributes_ListOfAttrs(expData);
    }
    
    @Test
    public void testUpdateAttributes_ListOfAttributes_MultipleAttributes() {
        // Set up
        List<HtmlAttribute> expData = new ArrayList<>();
        expData.add(new HtmlAttribute("attr1"));
        expData.add(new HtmlAttribute("attr2"));
        expData.add(new HtmlAttribute("attr3"));
        
        // Test
        this.testUpdateAttributes_ListOfAttrs(expData);
    }
    
    private void testUpdateAttributes_ListOfAttrs(List<HtmlAttribute> attrs) {
        // Arrange
        List<HtmlAttribute> expData = new ArrayList<>(defaultAttr);
        expData.addAll(attrs);
        
        // Apply
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(expData, this.defaultAttr);
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
    
    public void testSetIsClosing_SetToTrue_ImplicitlyTestIsClosing() {
        // Test
        this.testSetIsClosing(true);
    }
    
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
        List<HtmlAttribute> attrs = this.getDefaultAttrData();
        
        // Apply
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_AttrList_FirstValueIsSelfClosingAttr_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<HtmlAttribute> attrs = this.getDefaultAttrData();
        attrs.add(0, DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_AttrList_LastValueIsSelfClosingAttr_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<HtmlAttribute> attrs = this.getDefaultAttrData();
        attrs.add(DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_AttrList_MiddleValueIsSelfClosingAttr_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<HtmlAttribute> attrs = this.getDefaultAttrData();
        attrs.set(attrs.size() / 2, DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_AttrList_MultipleUpdates_FirstNot_SecondNot_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<HtmlAttribute> attrs = this.getDefaultAttrData();
        // Apply
        this.data.updateAttributes(attrs);
        this.data.updateAttributes(attrs);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_FromUpdateAttributes_AttrList_MultipleUpdates_FirstIs_SecondNot_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<HtmlAttribute> attrs = this.getDefaultAttrData();
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
        List<HtmlAttribute> attrs = this.getDefaultAttrData();
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
        List<HtmlAttribute> attrs = this.getDefaultAttrData();
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
        List<HtmlAttribute> attrs = this.getDefaultAttrData();
        
        // Apply
        this.data.setAttributes(attrs);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_SetAttributes_FirstIndexIsSelfClosing_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<HtmlAttribute> attrs = this.getDefaultAttrData();
        attrs.set(0, DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.setAttributes(attrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_SetAttributes_LastIndexIsSelfClosing_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<HtmlAttribute> attrs = this.getDefaultAttrData();
        attrs.add(DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.setAttributes(attrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_SetAttributes_MiddleIndexIsSelfClosing_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<HtmlAttribute> attrs = this.getDefaultAttrData();
        attrs.set(attrs.size() / 2, DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.setAttributes(attrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_SetAttributes_MultipleSets_FirstNot_SecondNot_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<HtmlAttribute> firstAttrs = this.getDefaultAttrData();
        List<HtmlAttribute> scndAttrs = this.getDefaultAttrData();
        
        // Apply
        this.data.setAttributes(firstAttrs);
        this.data.setAttributes(scndAttrs);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_SetAttributes_MultipleSets_FirstIs_SecondNot_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<HtmlAttribute> firstAttrs = this.getDefaultAttrData();
        firstAttrs.add(DEFAULT_SELF_CLOSING_ATTR);
        List<HtmlAttribute> scndAttrs = this.getDefaultAttrData();
        
        
        // Apply
        this.data.setAttributes(firstAttrs);
        this.data.setAttributes(scndAttrs);
        
        // Assert
        assertEquals(false, this.data.isSelfClosing());
    }
    
    @Test
    public void testSetSelfClosing_SetAttributes_MultipleSets_FirstNot_SecondIs_ImplicitlyTestIsSelfClosing() {
        // Arrange
        List<HtmlAttribute> firstAttrs = this.getDefaultAttrData();
        List<HtmlAttribute> scndAttrs = this.getDefaultAttrData();
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
        List<HtmlAttribute> firstAttrs = this.getDefaultAttrData();
        firstAttrs.add(DEFAULT_SELF_CLOSING_ATTR);
        List<HtmlAttribute> scndAttrs = this.getDefaultAttrData();
        scndAttrs.add(DEFAULT_SELF_CLOSING_ATTR);
        
        // Apply
        this.data.setAttributes(firstAttrs);
        this.data.setAttributes(scndAttrs);
        
        // Assert
        assertEquals(true, this.data.isSelfClosing());
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
        this.defaultAttr = Arrays.asList(new HtmlAttribute("newAttribute"));
        
        // Test
        this.testToString("<" + this.defaultName + " newAttribute" + ">");
    }
    
    @Test
    public void testToString_AlteredAttributeData_MorethanMaxAttributes() {
        // Set up
        this.defaultAttr = new ArrayList<HtmlAttribute>();
        
        for (int i = 0; i <= MutableHtmlData.MAX_NUM_ATTR_IN_STRING; i++) {
            HtmlAttribute attr = new HtmlAttribute("attr" + i, "value" + i);
            this.defaultAttr.add(attr);
        }
        
        // Test
        this.testToString("<" + this.defaultName + " " + MutableHtmlData.ELIPSIS + ">");
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
        MutableHtmlData other = this.generateData();
        
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
        this.defaultAttr = Arrays.asList(new HtmlAttribute("otherAttr", "otherName"));
        
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
        MutableHtmlData other = this.generateData();
        
        // Assert
        assertEquals(false, this.getSymmetricNegativeEquals(this.data, other));
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
    
    private void setDataFromState() {
        this.data = generateData();
    }
    
    private MutableHtmlData generateData() {
        return new MutableHtmlData(defaultName,
                                   isClosing,
                                   defaultOpening,
                                   defaultClosing,
                                   isSelfClosing,
                                   defaultAttr);
    }
    
    private String parseAttributesToString() {
        String result = "";
        
        for (HtmlAttribute data : this.defaultAttr)
            result += data.toString() + " ";
        
        return result.trim();
    }
    
    private List<HtmlAttribute> getDefaultAttrData() {
        List<HtmlAttribute> result = new ArrayList<>();
        
        for (int i = 0; i < DEFAULT_ATTR.length; i++) {
            result.add(DEFAULT_ATTR[i]);
        }
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