package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlParser;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class WhitespaceConsumer extends HtmlParser{
    
    public void consume(PushbackAndPositionReader input) throws IOException {
        this.setState(input);
        this.consumeAnyWhitespace();
        this.clearState();
    }
    
    private void consumeAnyWhitespace() throws IOException {
        char c = this.read();
        
        while(this.isWhiteSpace(c))
            c = this.read();
        
        this.input.unread(c);
    }
    
    private boolean isWhiteSpace(char ch) {
        return Character.isWhitespace(ch);
    }
}
