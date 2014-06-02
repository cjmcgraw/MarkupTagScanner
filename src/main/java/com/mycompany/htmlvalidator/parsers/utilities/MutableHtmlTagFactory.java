package com.mycompany.htmlvalidator.parsers.utilities;

import com.mycompany.htmlvalidator.interfaces.MutableHtmlTag;
import com.mycompany.htmlvalidator.MutableHtmlTagImpl;

public class MutableHtmlTagFactory {
    
    public MutableHtmlTag makeTag(String name) {
        return new MutableHtmlTagImpl();
    }
}