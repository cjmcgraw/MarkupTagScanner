package com.mycompany.htmlvalidator.parsers;

import com.mycompany.htmlvalidator.exceptions.IllegalHtmlTagException;
import com.mycompany.htmlvalidator.parsers.utilities.HtmlData;

public interface HtmlTagDataParser {
    
    public HtmlData parse(String data) throws IllegalHtmlTagException;
}
