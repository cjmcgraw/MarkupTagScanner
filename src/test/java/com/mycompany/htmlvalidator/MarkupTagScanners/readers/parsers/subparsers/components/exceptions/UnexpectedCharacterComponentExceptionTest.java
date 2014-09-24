package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components.exceptions;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.*;

public class UnexpectedCharacterComponentExceptionTest {
    private static final char DEFAULT_MISSING = 'a';
    private static final Point DEFAULT_POSITION = new Point(123, 456);
    private static final String DEFAULT_DATA = "some data";
    
    private static final char OTHER_MISSING = 'z';
    private static final Point OTHER_POSITION = new Point(654, 321);
    private static final String OTHER_DATA = "other data";
    
    private UnexpectedCharacterComponentException error;
    
    @Before
    public void setState() {
        this.error = new UnexpectedCharacterComponentException(DEFAULT_MISSING,
                                                               DEFAULT_POSITION,
                                                               DEFAULT_DATA);
    }
    
    @Test
    public void testGetData() {
        // Arrange
        String expData = DEFAULT_DATA;
        String data;
        
        // Apply
        data = this.error.getData();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEquals_sameObject_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        UnexpectedCharacterComponentException other = this.error;
        
        // Apply
        data = this.symmetricEquals_Positive(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEquals_diffObject_sameFields_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        UnexpectedCharacterComponentException other = this.generateException(DEFAULT_MISSING,
                                                                             DEFAULT_POSITION,
                                                                             DEFAULT_DATA);
        
        // Apply
        data = this.symmetricEquals_Positive(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEquals_diffObject_diffMissing_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        UnexpectedCharacterComponentException other = this.generateException(OTHER_MISSING,
                                                                          DEFAULT_POSITION,
                                                                          DEFAULT_DATA);
        
        // Apply
        data = this.symmetricEquals_Negative(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEquals_diffObject_diffPosition_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        UnexpectedCharacterComponentException other = this.generateException(DEFAULT_MISSING,
                                                                          OTHER_POSITION,
                                                                          DEFAULT_DATA);
        
        // Apply
        data = this.symmetricEquals_Negative(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEquals_diffObject_diffData_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        UnexpectedCharacterComponentException other = this.generateException(DEFAULT_MISSING,
                                                                             DEFAULT_POSITION,
                                                                             OTHER_DATA);
        
        // Apply
        data = this.symmetricEquals_Negative(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    private boolean symmetricEquals_Positive(UnexpectedCharacterComponentException other) {
        return this.error.equals(other) &&
               other.equals(this.error) &&
               this.error.equals((ComponentException) other) &&
               other.equals((ComponentException) this.error) &&
               this.error.equals((Object) other) && 
               other.equals((Object) this.error);
    }
    
    private boolean symmetricEquals_Negative(UnexpectedCharacterComponentException other) {
        return this.error.equals(other) ||
                other.equals(this.error) ||
                this.error.equals((ComponentException) other) ||
                other.equals((ComponentException) this.error) ||
                this.error.equals((Object) other) || 
                other.equals((Object) this.error);
    }
    
    private UnexpectedCharacterComponentException generateException(char missing, Point position, String data) {
        return new UnexpectedCharacterComponentException(missing, position, data);
    }
    
    @After
    public void clearState() {
        this.error = null;
    }
    
}