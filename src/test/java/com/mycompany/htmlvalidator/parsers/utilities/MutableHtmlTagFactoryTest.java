package com.mycompany.htmlvalidator.parsers.utilities;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.BeforeClass;

import java.util.Map;
import java.util.HashMap;

import com.mycompany.htmlvalidator.interfaces.MutableHtmlTag;
import com.mycompany.htmlvalidator.parsers.utilities.MutableHtmlTagFactory;

public class MutableHtmlTagFactoryTest {
    private static MutableHtmlTagFactory factory;
    private static Map<String, String> nameData;
    
    @BeforeClass
    public static void setUp() {
        factory = new MutableHtmlTagFactory();
        nameData = new HashMap<String, String>();
        
        nameData.put("MutableHtmlTag", "MutableHtmlTag");
    }
    
    @Test
    public void testMakeTag() {
        // Arrange
        boolean data;
        boolean expData = true;
        
        String name = "MutableHtmlTag";
        
        // Apply
        MutableHtmlTag tag = factory.makeTag(nameData.get(name));
        data = tag instanceof MutableHtmlTag;
        
        // Assert
        assertEquals(expData, data);
    }
    
}
