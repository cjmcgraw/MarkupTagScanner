package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.util.List;

public interface HtmlData {
    
    public String getName();
    
    public List<String> getData();
    
    public boolean isClosing();
    
    @Override
    public String toString();
}
