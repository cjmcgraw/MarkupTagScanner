package com.mycompany.htmlvalidator.scanners;

import java.io.Serializable;

import com.mycompany.htmlvalidator.exceptions.ErrorReporter;

public interface Attribute extends Serializable{
    public static final String EMPY_VALUE = "";
    
    public String getName();
    
    public String getValue();
    
    public boolean isFlag();
    
    public boolean isEmpty();
    
    public ErrorReporter getErrorReporter();
}
