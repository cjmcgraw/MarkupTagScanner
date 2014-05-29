package com.mycompany.htmlvalidator.interfaces;

import com.mycompany.htmlvalidator.interfaces.HtmlTag;

public interface HtmlValidator {
    
    public void addTag(HtmlTag tag);
    
    public void removeAll(String element);
    
    public boolean validate();
}
