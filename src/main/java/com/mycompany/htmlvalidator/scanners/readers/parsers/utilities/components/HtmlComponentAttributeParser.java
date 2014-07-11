package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.MarkupTag;
import com.mycompany.htmlvalidator.scanners.readers.parsers.*;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public abstract class HtmlComponentAttributeParser extends MarkupParser<HtmlAttribute> {
    protected HtmlAttribute attribute;
    
    public abstract HtmlAttribute parse(PushbackAndPositionReader input) throws IOException;
    
    protected void setState(PushbackAndPositionReader input) {
        super.setState(input);
        this.attribute = new HtmlAttribute();
    }
    
    protected void clearState() {
        super.clearState();
        this.attribute = null;
    }
    
    protected boolean isValueSeparator(char c) {
        return MarkupTag.ATTRIBUTE_VALUE_SEPARATOR.equals(c);
    }
}