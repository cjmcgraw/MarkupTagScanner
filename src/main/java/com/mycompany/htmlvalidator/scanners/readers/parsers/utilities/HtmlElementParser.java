package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.MutableHtmlData;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlElementParser extends HtmlUtilityParser {
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input, MutableHtmlData result) throws IOException {
        this.setState(input, result);
        this.parseData();
        this.clearState();
        return result;
    }
    
    private void parseData() throws IOException {
        this.parseElementName();
    }
    
    private void parseElementName() throws IOException {
        char c;
        while(this.validateChar(c = read()))
            this.getResult().updateName(c);
    }
    
    protected boolean validateChar(char c) throws IOException {
        return super.validateChar(c) && !this.isWhitespace(c);
    }
}