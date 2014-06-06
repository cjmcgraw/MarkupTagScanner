package com.mycompany.htmlvalidator.parsers;

import java.io.Closeable;

import com.mycompany.htmlvalidator.exceptions.IllegalHtmlTagException;
import com.mycompany.htmlvalidator.interfaces.HtmlTag;

public interface HtmlScanner extends Closeable {
    
    public HtmlTag next() throws IllegalHtmlTagException;
    
    public boolean hasNext();
}
