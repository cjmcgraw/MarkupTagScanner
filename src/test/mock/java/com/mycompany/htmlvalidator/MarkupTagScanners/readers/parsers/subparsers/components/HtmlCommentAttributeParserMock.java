package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components;

import java.io.IOException;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlAttribute;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components.exceptions.AttributeException;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public class HtmlCommentAttributeParserMock extends HtmlCommentAttributeParser {
    private HtmlAttribute result;
    private AttributeException err;
    
    public HtmlCommentAttributeParserMock(String result) {
        this.result = new HtmlAttribute(result);
    }
    
    public void setError(AttributeException err) {
        this.err = err;
    }
    
    public AttributeException getError() {
        return this.err;
    }
    
    @Override
    public HtmlAttribute parse(PushbackAndPositionReader input) throws IOException {
        if (err != null)
            throw err;
        input.read();
        return result;
    }
}