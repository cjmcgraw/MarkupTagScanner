package com.mycompany.htmlvalidator;

import com.mycompany.htmlvalidator.MarkupTagScanners.Tag;


public interface MarkupValidator {
    
    public void addTag(Tag tag);
    
    public void removeAll(String element);
    
    public boolean validate();
}
