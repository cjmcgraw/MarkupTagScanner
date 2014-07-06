package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import com.mycompany.htmlvalidator.scanners.MarkupTag;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlAttribute;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlParser;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public abstract class HtmlComponentAttributeParser extends HtmlParser<HtmlAttribute> {
    protected HtmlAttribute attribute;
    
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
