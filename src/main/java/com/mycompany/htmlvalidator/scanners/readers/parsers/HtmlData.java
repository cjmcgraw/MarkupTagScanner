package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.io.Serializable;
import java.util.List;

public interface HtmlData extends Serializable{
    
    public String getName();
    
    public List<HtmlAttribute> getAttributes();
    
    public boolean isClosing();
    
    public boolean isSelfClosing();
    
    public boolean hasOpeningTag();
    
    public boolean hasClosingTag();
    
    @Override
    public String toString();
}
