package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.*;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlCommentAttributeParserMock extends HtmlCommentAttributeParser {
    private HtmlAttribute result;
    
    public HtmlCommentAttributeParserMock(String result) {
        this.result = new HtmlAttribute(result);
    }
    
    @Override
    public HtmlAttribute parse(PushbackAndPositionReader input) throws IOException {
        input.read();
        return result;
    }
}
