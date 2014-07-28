package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlAttribute;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlSingleAttributeParserMock extends HtmlSingleAttributeParser {
    private HtmlAttribute result;
    
    public HtmlSingleAttributeParserMock(String result) {
        this.result = new HtmlAttribute(result);
    }
    
    @Override
    public HtmlAttribute parse(PushbackAndPositionReader input) throws IOException {
        input.read();
        return this.result;
    }
}