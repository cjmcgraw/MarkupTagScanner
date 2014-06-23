package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.util.List;

public interface HtmlData {
    
    public String getName();
    
    public List<HtmlAttribute> getAttributes();
    
    public boolean isClosing();
    
    public boolean hasOpeningTag();
    
    public boolean hasClosingTag();
    
    @Override
    public String toString();
}
