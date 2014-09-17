package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import java.io.*;

import com.mycompany.htmlvalidator.exceptions.MarkupError;
import com.mycompany.htmlvalidator.scanners.MarkupTag;
import com.mycompany.htmlvalidator.scanners.readers.parsers.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.InvalidStateException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions.EndOfInputAttributeException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public abstract class HtmlComponentAttributeParser extends MarkupParser<HtmlAttribute> {
    private static final String CLASS_NAME = "HtmlComponentAttributeParser";
    private static final String FIELD_NAME = "attribute";
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
    
    protected char read() throws IOException {
        try {
            return super.read();
        } catch (EOFException e) {
            throw this.generateEndOfInputAttributeException();
        }
    }
    
    protected EndOfInputAttributeException generateEndOfInputAttributeException() {
        this.attribute.setName(this.getAttributeName());
        this.attribute.setValue(this.getAttributeValue());
        return new EndOfInputAttributeException(this.getAttribute());
    }
    
    protected abstract String getAttributeName();
    
    protected abstract String getAttributeValue();
    
    protected boolean isValueSeparator(char c) {
        return MarkupTag.ATTRIBUTE_VALUE_SEPARATOR.equals(c);
    }
    
    protected void addError(MarkupError err) {
        this.validateState();
        this.getAttribute().addError(err);
    }
    
    protected HtmlAttribute getAttribute() {
        this.validateState();
        return this.attribute;
    }
    
    private void validateState() {
        if(this.isMissingState())
            throw new InvalidStateException(CLASS_NAME, FIELD_NAME);
    }
    
    private boolean isMissingState() {
        return this.attribute == null;
    }
}