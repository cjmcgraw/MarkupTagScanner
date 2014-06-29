package com.mycompany.htmlvalidator.scanners.tokens;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;

public interface HtmlTag {
    
    public HtmlData getData();
    
    public boolean isOpenTag();
    
    public boolean isSelfClosing();
    
    public boolean equals(HtmlTag other);
    
    public boolean matches(HtmlTag other);
}
