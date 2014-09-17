package com.mycompany.htmlvalidator.scanners.tokens;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;

public interface HtmlTag extends HtmlData {
    
    public HtmlData getData();
    
    public boolean validate();
    
    public boolean isSelfClosing();
    
    public boolean equals(HtmlTag other);
    
    public boolean matches(HtmlTag other);
}
