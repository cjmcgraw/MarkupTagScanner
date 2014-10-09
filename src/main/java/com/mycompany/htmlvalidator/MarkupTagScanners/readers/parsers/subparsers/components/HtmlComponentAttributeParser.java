package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components;

import java.io.*;

import com.mycompany.htmlvalidator.MarkupTagScanners.enums.MarkupTag;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.*;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.errors.InvalidStateException;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlAttribute;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components.errors.EndOfInputAttributeError;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;
import com.mycompany.htmlvalidator.errors.MarkupError;

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
    
    protected EndOfInputAttributeError generateEndOfInputAttributeException() {
        this.attribute.setName(this.getAttributeName());
        this.attribute.setValue(this.getAttributeValue());
        return new EndOfInputAttributeError(this.getAttribute());
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