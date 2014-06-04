package com.mycompany.htmlvalidator.interfaces;

import com.mycompany.htmlvalidator.parsers.utilities.HtmlData;

public interface MutableHtmlTag extends HtmlTag{
    
    public void setData(HtmlData data);
    
}
