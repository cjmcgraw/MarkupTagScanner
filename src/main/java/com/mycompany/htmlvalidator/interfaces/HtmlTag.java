package com.mycompany.htmlvalidator.interfaces;

public interface HtmlTag {
    
    public String getElement();
    
    public boolean isOpenTag();
    
    public boolean isSelfClosing();
    
    public boolean equals(HtmlTag other);
    
    public boolean matches(HtmlTag other);
}
