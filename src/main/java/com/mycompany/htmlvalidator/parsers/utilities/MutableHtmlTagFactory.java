package com.mycompany.htmlvalidator.parsers.utilities;

import com.mycompany.htmlvalidator.interfaces.MutableHtmlTag;
import com.mycompany.htmlvalidator.MutableHtmlTagImpl;

public class MutableHtmlTagFactory {
    
    public MutableHtmlTag makeTag(String name) {
        switch (name.toLowerCase()) {
        case "standard": return new MutableHtmlTagImpl();
        }
        
        throw new IllegalArgumentException("The given MutableHtmlTag name is unrecognized");
    }
}