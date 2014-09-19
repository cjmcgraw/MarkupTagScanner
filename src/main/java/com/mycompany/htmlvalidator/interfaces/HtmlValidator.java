package com.mycompany.htmlvalidator.interfaces;

import com.mycompany.htmlvalidator.scanners.tokens.*;


public interface HtmlValidator {
    
    public void addTag(Tag tag);
    
    public void removeAll(String element);
    
    public boolean validate();
}
