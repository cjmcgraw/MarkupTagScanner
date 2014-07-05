package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.io.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.*;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public abstract class DataParser extends HtmlParser {
    protected MutableHtmlData result;
    
    protected char readNext() throws IOException {
        try {
            return this.read();
        } catch (EOFException e) {
            throw new EndOfInputParsingException(this.currentPosition(), this.result);
        }
    }
    
    public abstract HtmlData parse(PushbackAndPositionReader input) throws IOException;
    
    protected boolean validateChar(char c) throws IOException {
        if(isCloseTagEnclosure(c))
            this.closeTagRead(c);
        else if (isOpenTagEnclosure(c))
            this.openTagRead(c);
        
        return true;
    }
    
    protected void closeTagRead(char c) throws IOException {
        this.unread(c);
        throw new UnexpectedCloseTagParsingException(this.currentPosition(), this.result);
    }
    
    protected void openTagRead(char c) throws IOException {
        this.unread(c);
        throw new UnclosedTagParsingException(this.currentPosition(), this.result);
    }
    
    @Override
    protected void setState(PushbackAndPositionReader input) {
        this.setState(input, new MutableHtmlData());
    }
    
    protected void setState(PushbackAndPositionReader input, MutableHtmlData result) {
        super.setState(input);
        this.result = result;
    }
    
    protected void clearState() {
        super.clearState();
        this.result = null;
    }
}
