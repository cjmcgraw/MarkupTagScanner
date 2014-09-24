package com.mycompany.htmlvalidator.MarkupTagScanners;

import java.io.Serializable;

import com.mycompany.htmlvalidator.exceptions.ErrorReporter;

public interface Attribute extends Serializable{
    public static final String EMPTY_VALUE = "";
    
    public String getName();
    
    public String getValue();
    
    public boolean isFlag();
    
    public boolean isEmpty();
    
    public ErrorReporter getErrorReporter();
}
