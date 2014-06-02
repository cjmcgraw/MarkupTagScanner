package com.mycompany.htmlvalidator.interfaces;

public interface MutableHtmlTag extends HtmlTag{
    
    public void setElement(String element);
    
    public void setIsOpenTag(boolean openTag);
    
}
