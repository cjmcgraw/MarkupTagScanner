package com.mycompany.htmlvalidator.parsers.utilities;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.BeforeClass;

import com.mycompany.htmlvalidator.interfaces.MutableHtmlTag;
import com.mycompany.htmlvalidator.parsers.utilities.MutableHtmlTagFactory;

public class MutableHtmlTagFactoryTest {
    private static MutableHtmlTagFactory factory;
    
    @BeforeClass
    public static void setUp() {
        factory = new MutableHtmlTagFactory();
    }
    
    @Test
    public void testMakeTag_StandardMutableHtmlTag_LowerCase() {
        // Arrange
        boolean data;
        boolean expData = true;
        
        String name = "standard";
        
        // Apply
        MutableHtmlTag tag = factory.makeTag(name);
        data = tag instanceof MutableHtmlTag;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMakeTag_StandardMutableHtmlTag_UpperCase() {
        // Arrange
        boolean data;
        boolean expData = true;
        
        String name = "STANDARD";
        
        // Apply
        MutableHtmlTag tag = factory.makeTag(name);
        data = tag instanceof MutableHtmlTag;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testMakeTag_StandardMutableHtmlTag_MixedCase() {
        // Arrange
        boolean data;
        boolean expData = true;
        
        String name = "sTaNdArD";
        
        // Apply
        MutableHtmlTag tag = factory.makeTag(name);
        data = tag instanceof MutableHtmlTag;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testMakeTag_UnknownArgument() {
        // Arrange
        String name = "Some unknown name. Should cause exception";
        
        // Apply + Assert
        factory.makeTag(name);
    }
    
}
