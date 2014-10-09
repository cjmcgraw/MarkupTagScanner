package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components;

import java.io.IOException;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlAttribute;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components.errors.AttributeError;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public class HtmlSingleAttributeParserMock extends HtmlSingleAttributeParser {
    private HtmlAttribute result;
    private AttributeError err;
    
    public HtmlSingleAttributeParserMock(String result) {
        this.result = new HtmlAttribute(result);
    }
    
    public HtmlSingleAttributeParserMock(String result, AttributeError err) {
        this(result);
        this.err = err;
    }
    
    public void setError(AttributeError err) {
        this.err = err;
    }
    
    public AttributeError getError() {
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