package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlElementParser extends HtmlUtilityParser {
    private static final String COMMENT_NAME = MarkupTagNames.COMMENT_TAG.getBeginName();
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input, HtmlData result) throws IOException {
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
        
        if (this.isComment())
            this.unread();
    }
    
    protected boolean validateChar(char c) throws IOException {
        return super.validateChar(c) && 
                this.NonClosingAttribute() &&
                !this.isWhitespace(c) && 
                !this.isComment();
    }
    
    private boolean isComment() throws IOException {
        String name = this.result.getName();
        
        return name.length() == COMMENT_NAME.length() && 
               name.startsWith(COMMENT_NAME);
    }
    
    protected boolean NonClosingAttribute() throws IOException {
        if (super.isClosingAttribute()) {
            this.unread();
            return false;
        }
        return true;
    }
}