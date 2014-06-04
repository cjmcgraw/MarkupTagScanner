package com.mycompany.htmlvalidator.parsers.utilities;

import com.mycompany.htmlvalidator.exceptions.IllegalHtmlTagException;

public interface HtmlTagDataParser {
    
    public HtmlData parse(String data) throws IllegalHtmlTagException;
}
