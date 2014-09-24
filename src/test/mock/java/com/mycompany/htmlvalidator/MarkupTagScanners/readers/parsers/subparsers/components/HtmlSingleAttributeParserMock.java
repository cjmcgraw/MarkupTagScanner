package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components;

import java.io.IOException;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlAttribute;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components.exceptions.AttributeException;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public class HtmlSingleAttributeParserMock extends HtmlSingleAttributeParser {
    private HtmlAttribute result;
    private AttributeException err;
    
    public HtmlSingleAttributeParserMock(String result) {
        this.result = new HtmlAttribute(result);
    }
    
    public HtmlSingleAttributeParserMock(String result, AttributeException err) {
        this(result);
        this.err = err;
    }
    
    public void setError(AttributeException err) {
        this.err = err;
    }
    
    public AttributeException getError() {
        return this.err;
    }
    
    @Override
    public HtmlAttribute parse(PushbackAndPositionReader input) throws IOException {
        input.read();
        if (err != null)
            throw this.err;
        return this.result;
    }
}